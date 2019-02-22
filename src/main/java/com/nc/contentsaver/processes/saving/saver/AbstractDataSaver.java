package com.nc.contentsaver.processes.saving.saver;

import com.nc.contentsaver.processes.linking.DataLinkBufferObject;
import com.nc.contentsaver.processes.saving.AMQP;
import com.nc.contentsaver.processes.saving.saver.buffer.DataBufferSaver;
import com.nc.contentsaver.processes.saving.saver.link.DataLinkSaver;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class describes an object that implements the "DataSaver" interface.
 */
class AbstractDataSaver implements DataSaver {
    /**
     * Logger. To log exceptions.
     */
    private static final Logger LOG = Logger.getLogger(AbstractDataSaver.class.getSimpleName());
    /**
     * An object that can save data of saved content.
     */
    private DataLinkSaver dataLinkSaver;
    /**
     * An object that can save content.
     */
    private DataBufferSaver dataBufferSaver;

    /**
     * Creates an object that implements the DataSaver interface.
     */
    AbstractDataSaver() {
        AMQP.getInstance().addObserver(this);
    }

    public DataLinkSaver getDataLinkSaver() {
        return dataLinkSaver;
    }

    public void setDataLinkSaver(DataLinkSaver dataLinkSaver) {
        this.dataLinkSaver = dataLinkSaver;
    }

    public DataBufferSaver getDataBufferSaver() {
        return dataBufferSaver;
    }

    public void setDataBufferSaver(DataBufferSaver dataBufferSaver) {
        this.dataBufferSaver = dataBufferSaver;
    }

    @Override
    public void saveData(DataLinkBufferObject data) {
        this.dataLinkSaver.saveLink(data.getDataLinkObject());
        try {
            this.dataBufferSaver.saveBuffer(data.getBuffer(), data.getDataLinkObject().getSha256());
        } catch (FileAlreadyExistsException ignored) {
            //ignored
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Ошибка сохранения контента", e);
        }
        this.dataLinkSaver.markAsSaved(data.getDataLinkObject());
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            saveData(AMQP.getInstance().getData());
        } catch (NoSuchElementException e) {
            LOG.log(Level.WARNING, "Элемент не был получен из очереди", e);
        }
    }
}
