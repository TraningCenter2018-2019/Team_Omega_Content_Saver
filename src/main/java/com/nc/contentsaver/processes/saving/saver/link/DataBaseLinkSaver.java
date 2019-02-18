package com.nc.contentsaver.processes.saving.saver.link;

import com.nc.contentsaver.processes.linking.DataLinkObject;
import com.nc.contentsaver.processes.saving.databaseutils.LinkerDatabaseManager;

public class DataBaseLinkSaver implements DataLinkSaver {
    @Override
    public void saveLink(DataLinkObject link) {
        System.out.println("Ссылка сохранена в базу данных, помечена как не сохранённая");
        LinkerDatabaseManager.getInstance().saveObject(link);//fixme
    }

    @Override
    public void markAsSaved(DataLinkObject link) {
        System.out.println("Ссылка обновлена, помечена как сохранённая");
//        LinkerDatabaseManager.getInstance().saveObject(link);//fixme
    }
}
