package com.nc.contentsaver.processes.saving.saver.link;

import com.nc.contentsaver.processes.linking.DataLinkObject;

public interface DataLinkSaver {
    void saveLink(DataLinkObject link);

    void markAsSaved(DataLinkObject link);
}
