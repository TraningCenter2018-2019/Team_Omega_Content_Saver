package com.nc.contentsaver.processes.managing.manager;

import com.nc.contentsaver.exceptions.ContentNotFoundException;
import com.nc.contentsaver.processes.linking.DataLinkBufferObject;
import com.nc.contentsaver.processes.linking.DataLinkObject;
import com.nc.contentsaver.processes.managing.AMQP;
import com.nc.contentsaver.processes.managing.manager.buffer.DataBufferManager;
import com.nc.contentsaver.processes.managing.manager.link.DataLinkManager;
import io.vertx.core.buffer.Buffer;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class describes an object that implements the "DataManager" interface.
 */
class AbstractDataManager implements DataManager {
    /**
     * Logger. To log exceptions.
     */
    private static final Logger LOG = Logger.getLogger(AbstractDataManager.class.getSimpleName());
    /**
     * An object that can save data of saved content.
     */
    private DataLinkManager dataLinkManager;
    /**
     * An object that can save content.
     */
    private DataBufferManager dataBufferManager;

    /**
     * Creates an object that implements the DataManager interface.
     */
    AbstractDataManager() {
        AMQP.getInstance().addObserver(this);
    }

    public DataLinkManager getDataLinkManager() {
        return dataLinkManager;
    }

    public void setDataLinkManager(DataLinkManager dataLinkManager) {
        this.dataLinkManager = dataLinkManager;
    }

    public DataBufferManager getDataBufferManager() {
        return dataBufferManager;
    }

    public void setDataBufferManager(DataBufferManager dataBufferManager) {
        this.dataBufferManager = dataBufferManager;
    }

    @Override
    public void saveData(DataLinkBufferObject data) {
        this.dataLinkManager.saveLink(data.getDataLinkObject());
        try {
            if (!data.getDataLinkObject().isSaved()) {
                this.dataBufferManager.saveBuffer(data.getBuffer(), data.getDataLinkObject().getSha256());
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Content save failed.", e);
        }
        this.dataLinkManager.markAsSaved(data.getDataLinkObject());
    }

    @Override
    public DataLinkBufferObject getData(String link) throws ContentNotFoundException {
        DataLinkObject dataLinkObject = this.dataLinkManager.restoreFromLink(link);
        Buffer buffer = Buffer.buffer();
        try {
            buffer = this.dataBufferManager.restoreBuffer(dataLinkObject.getSha256());
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Content reading error.", e);
        }
        return new DataLinkBufferObject(dataLinkObject, buffer);
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            saveData(AMQP.getInstance().getData());
        } catch (NoSuchElementException e) {
            LOG.log(Level.WARNING, "Item not received from queue.", e);
        }
    }
}
