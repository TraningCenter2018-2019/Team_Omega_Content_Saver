package com.nc.contentsaver.processes.linking;

import io.vertx.core.buffer.Buffer;

/**
 * A class that describes an object that contains a link to the file and file data.
 */
public class DataLinkBufferObject {
    /**
     * An object containing a link to the file we need to save.
     */
    private DataLinkObject dataLinkObject;
    /**
     * File data we need to save.
     */
    private Buffer buffer;

    /**
     * Creates an object that contains a link to the file and file data.
     *
     * @param dataLinkObject an object containing a link to the file we need to save
     * @param buffer         file data we need to save
     */
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
