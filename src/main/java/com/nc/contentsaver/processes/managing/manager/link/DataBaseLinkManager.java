package com.nc.contentsaver.processes.managing.manager.link;

import com.nc.contentsaver.exceptions.ContentNotFoundException;
import com.nc.contentsaver.processes.linking.DataLinkObject;
import com.nc.contentsaver.processes.managing.databaseutils.LinkerDatabaseManager;

import java.util.logging.Logger;

/**
 * The class describes an object that can save information about the content in the database.
 */
public class DataBaseLinkManager implements DataLinkManager {
    /**
     * Logger. Displays information about managing content information.
     */
    private static final Logger LOG = Logger.getLogger(DataBaseLinkManager.class.getSimpleName());

    @Override
    public void saveLink(DataLinkObject link) {
        LOG.info("Link saved to database, marked as not saved.");
        LinkerDatabaseManager.getInstance().saveObject(link);
    }

    @Override
    public void markAsSaved(DataLinkObject link) {
        LOG.info("Link updated, marked as saved.");
        LinkerDatabaseManager.getInstance().markAsSaved(link);
    }

    @Override
    public DataLinkObject restoreFromLink(String link) throws ContentNotFoundException {
        return LinkerDatabaseManager.getInstance().getLinkObject(link);
    }
}
