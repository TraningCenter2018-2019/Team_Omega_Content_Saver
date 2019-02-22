package com.nc.contentsaver.processes.saving.saver.link;

import com.nc.contentsaver.processes.linking.DataLinkObject;
import com.nc.contentsaver.processes.saving.databaseutils.LinkerDatabaseManager;

import java.util.logging.Logger;

/**
 * The class describes an object that can save information about the content in the database.
 */
public class DataBaseLinkSaver implements DataLinkSaver {
    /**
     * Logger Displays information about saving content information.
     */
    private static final Logger LOG = Logger.getLogger(DataBaseLinkSaver.class.getSimpleName());

    @Override
    public void saveLink(DataLinkObject link) {
        LOG.info("Ссылка сохранена в базу данных, помечена как не сохранённая");
        LinkerDatabaseManager.getInstance().saveObject(link);
    }

    @Override
    public void markAsSaved(DataLinkObject link) {
        LOG.info("Ссылка обновлена, помечена как сохранённая");
//        LinkerDatabaseManager.getInstance().saveObject(link);//fixme добавить обновление аттрибута saved
    }
}
