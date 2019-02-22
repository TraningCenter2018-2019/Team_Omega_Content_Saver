package com.nc.contentsaver.processes.managing.manager;

import com.nc.contentsaver.processes.managing.manager.buffer.DataBufferManager;
import com.nc.contentsaver.processes.managing.manager.buffer.DataBufferToFileManager;
import com.nc.contentsaver.processes.managing.manager.link.DataBaseLinkManager;
import com.nc.contentsaver.processes.managing.manager.link.DataLinkManager;

/**
 * Builder. Allows you to create an object that can save content and information about it.
 */
public class DataManagerBuilder {
    /**
     * An object that can save content data.
     */
    private DataLinkManager dataLinkManager;
    /**
     * An object that can save content.
     */
    private DataBufferManager dataBufferManager;

    /**
     * Sets the implementation of the content data storage interface.
     *
     * @param dataLinkManager implementation of the content data storage interface
     * @return data saver builder
     */
    public DataManagerBuilder setDataLinkManager(DataLinkManager dataLinkManager) {
        this.dataLinkManager = dataLinkManager;
        return this;
    }

    /**
     * Sets the implementation of the save content interface.
     *
     * @param dataBufferManager implementation of the save content interface
     * @return data saver builder
     */
    public DataManagerBuilder setDataBufferManager(DataBufferManager dataBufferManager) {
        this.dataBufferManager = dataBufferManager;
        return this;
    }

    /**
     * Creates an object using the specified interface implementations.
     * Instead of unspecified implementations, default implementations are used.
     *
     * @return data saver object
     */
    public DataManager build() {
        if (dataLinkManager == null) {
            this.dataLinkManager = getDefaultDataLinkSaver();
        }
        if (dataBufferManager == null) {
            this.dataBufferManager = getDefaultDataBufferSaver();
        }
        AbstractDataManager dataSaver = new AbstractDataManager();
        dataSaver.setDataLinkManager(dataLinkManager);
        dataSaver.setDataBufferManager(dataBufferManager);
        return dataSaver;
    }

    /**
     * Creates an object using default interface
     * implementations: the content will be saved to disk, the content data will be saved to the database.
     *
     * @return data saver object
     */
    public DataManager buildDefault() {
        this.dataLinkManager = getDefaultDataLinkSaver();
        this.dataBufferManager = getDefaultDataBufferSaver();
        AbstractDataManager dataSaver = new AbstractDataManager();
        dataSaver.setDataLinkManager(dataLinkManager);
        dataSaver.setDataBufferManager(dataBufferManager);
        return dataSaver;
    }

    private DataLinkManager getDefaultDataLinkSaver() {
        return new DataBaseLinkManager();
    }

    private DataBufferManager getDefaultDataBufferSaver() {
        return new DataBufferToFileManager();
    }
}
