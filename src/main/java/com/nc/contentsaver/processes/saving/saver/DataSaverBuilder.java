package com.nc.contentsaver.processes.saving.saver;

import com.nc.contentsaver.processes.saving.saver.buffer.DataBufferSaver;
import com.nc.contentsaver.processes.saving.saver.buffer.DataBufferToFileSaver;
import com.nc.contentsaver.processes.saving.saver.link.DataBaseLinkSaver;
import com.nc.contentsaver.processes.saving.saver.link.DataLinkSaver;

/**
 * Builder. Allows you to create an object that can save content and information about it.
 */
public class DataSaverBuilder {
    /**
     * An object that can save content data.
     */
    private DataLinkSaver dataLinkSaver;
    /**
     * An object that can save content.
     */
    private DataBufferSaver dataBufferSaver;

    /**
     * Sets the implementation of the content data storage interface.
     *
     * @param dataLinkSaver implementation of the content data storage interface
     * @return data saver builder
     */
    public DataSaverBuilder setDataLinkSaver(DataLinkSaver dataLinkSaver) {
        this.dataLinkSaver = dataLinkSaver;
        return this;
    }

    /**
     * Sets the implementation of the save content interface.
     *
     * @param dataBufferSaver implementation of the save content interface
     * @return data saver builder
     */
    public DataSaverBuilder setDataBufferSaver(DataBufferSaver dataBufferSaver) {
        this.dataBufferSaver = dataBufferSaver;
        return this;
    }

    /**
     * Creates an object using the specified interface implementations.
     * Instead of unspecified implementations, default implementations are used.
     *
     * @return data saver object
     */
    public DataSaver build() {
        if (dataLinkSaver == null) {
            this.dataLinkSaver = getDefaultDataLinkSaver();
        }
        if (dataBufferSaver == null) {
            this.dataBufferSaver = getDefaultDataBufferSaver();
        }
        AbstractDataSaver dataSaver = new AbstractDataSaver();
        dataSaver.setDataLinkSaver(dataLinkSaver);
        dataSaver.setDataBufferSaver(dataBufferSaver);
        return dataSaver;
    }

    /**
     * Creates an object using default interface
     * implementations: the content will be saved to disk, the content data will be saved to the database.
     *
     * @return data saver object
     */
    public DataSaver buildDefault() {
        this.dataLinkSaver = getDefaultDataLinkSaver();
        this.dataBufferSaver = getDefaultDataBufferSaver();
        AbstractDataSaver dataSaver = new AbstractDataSaver();
        dataSaver.setDataLinkSaver(dataLinkSaver);
        dataSaver.setDataBufferSaver(dataBufferSaver);
        return dataSaver;
    }

    private DataLinkSaver getDefaultDataLinkSaver() {
        return new DataBaseLinkSaver();
    }

    private DataBufferSaver getDefaultDataBufferSaver() {
        return new DataBufferToFileSaver();
    }
}
