package com.nc.contentsaver.processes.saving.saver;

import com.nc.contentsaver.processes.linking.DataLinkBufferObject;

import java.util.Observer;

/**
 * Data storage interface.
 */
public interface DataSaver extends Observer {
    /**
     * Saves data.
     *
     * @param data data to save
     */
    void saveData(DataLinkBufferObject data);
}
