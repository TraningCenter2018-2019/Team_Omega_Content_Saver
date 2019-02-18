package com.nc.contentsaver.processes.saving.saver;

import com.nc.contentsaver.processes.saving.saver.buffer.DataBufferSaver;
import com.nc.contentsaver.processes.saving.saver.buffer.DataBufferToFileSaver;
import com.nc.contentsaver.processes.saving.saver.link.DataBaseLinkSaver;
import com.nc.contentsaver.processes.saving.saver.link.DataLinkSaver;

public class DataSaverBuilder {
    private DataLinkSaver dataLinkSaver;
    private DataBufferSaver dataBufferSaver;

    public DataSaverBuilder setDataLinkSaver(DataLinkSaver dataLinkSaver) {
        this.dataLinkSaver = dataLinkSaver;
        return this;
    }

    public DataSaverBuilder setDataBufferSaver(DataBufferSaver dataBufferSaver) {
        this.dataBufferSaver = dataBufferSaver;
        return this;
    }

    public DataSaver build() {
        if (dataLinkSaver == null) {
            this.dataLinkSaver = getDefaultDataLinkSaver();
        }
        if (dataBufferSaver == null) {
            this.dataBufferSaver = getDefaultDataBufferSaver();
        }
        AbstractDataSaver dataSaver = new AbstractDataSaver();
        dataSaver.setDataLinkSaver(dataLinkSaver);
        dataSaver.setDataBufferSaver(dataBufferSaver);
        return dataSaver;
    }

    public DataSaver buildDefault() {
        this.dataLinkSaver = getDefaultDataLinkSaver();
        this.dataBufferSaver = getDefaultDataBufferSaver();
        AbstractDataSaver dataSaver = new AbstractDataSaver();
        dataSaver.setDataLinkSaver(dataLinkSaver);
        dataSaver.setDataBufferSaver(dataBufferSaver);
        return dataSaver;
    }

    private DataLinkSaver getDefaultDataLinkSaver() {
        return new DataBaseLinkSaver();
    }

    private DataBufferSaver getDefaultDataBufferSaver() {
        return new DataBufferToFileSaver();
    }
}
