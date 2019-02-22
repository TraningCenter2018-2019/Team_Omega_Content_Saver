package com.nc.contentsaver.processes.managing.manager.link;

import com.nc.contentsaver.exceptions.ContentNotFoundException;
import com.nc.contentsaver.processes.linking.DataLinkObject;

/**
 * Interface of content managing.
 */
public interface DataLinkManager {
    /**
     * Saves content information.
     *
     * @param link object that contains information about the content
     */
    void saveLink(DataLinkObject link);

    /**
     * Indicates in the database that the object was successfully written to disk.
     *
     * @param link content Information
     */
    void markAsSaved(DataLinkObject link);

    /**
     * Returns content.
     *
     * @param link link to content
     * @return content object
     * @throws ContentNotFoundException if the information about the content is not found or it has not yet been saved
     */
    DataLinkObject restoreFromLink(String link) throws ContentNotFoundException;
}
