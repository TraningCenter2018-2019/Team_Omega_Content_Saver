package com.nc.contentsaver.processes.saving.saver;

import com.nc.contentsaver.processes.linking.DataLinkBufferObject;

import java.util.Observer;

public interface DataSaver extends Observer {
    void saveData(DataLinkBufferObject data);
}
