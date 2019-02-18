package com.nc.contentsaver.processes.linking;

import io.vertx.core.buffer.Buffer;

public class DataLinkBufferObject {
    private DataLinkObject dataLinkObject;
    private Buffer buffer;

    public DataLinkBufferObject(DataLinkObject dataLinkObject, Buffer buffer) {
        this.dataLinkObject = dataLinkObject;
        this.buffer = buffer;
    }

    public DataLinkObject getDataLinkObject() {
        return dataLinkObject;
    }

    public void setDataLinkObject(DataLinkObject dataLinkObject) {
        this.dataLinkObject = dataLinkObject;
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public void setBuffer(Buffer buffer) {
        this.buffer = buffer;
    }
}
