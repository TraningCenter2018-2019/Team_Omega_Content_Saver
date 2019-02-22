package com.nc.contentsaver.processes.managing.manager;

import com.nc.contentsaver.exceptions.ContentNotFoundException;
import com.nc.contentsaver.processes.linking.DataLinkBufferObject;

import java.util.Observer;

/**
 * Data manager interface.
 */
public interface DataManager extends Observer {
    /**
     * Saves data.
     *
     * @param data data to save
     */
    void saveData(DataLinkBufferObject data);

    /**
     * Returns content.
     *
     * @param link link to content
     * @return content object
     * @throws ContentNotFoundException if the information about the content is not found or it has not yet been saved
     */
    DataLinkBufferObject getData(String link) throws ContentNotFoundException;
}
