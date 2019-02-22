package com.nc.contentsaver.processes.saving.saver.link;

import com.nc.contentsaver.processes.linking.DataLinkObject;

/**
 * Interface of content saving.
 */
public interface DataLinkSaver {
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
}
