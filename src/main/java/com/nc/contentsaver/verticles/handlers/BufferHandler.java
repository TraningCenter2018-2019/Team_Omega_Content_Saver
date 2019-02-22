package com.nc.contentsaver.verticles.handlers;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;

/**
 * Class describes an object that will read information from the input stream into the transferred buffer.
 */
public class BufferHandler implements Handler<Buffer> {
    /**
     * Buffer into which the data stream will be written.
     */
    private Buffer buffer;

    /**
     * Creates an object that will read information from the input stream into the transferred buffer.
     *
     * @param buffer buffer into which the data stream will be written.
     */
    public BufferHandler(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void handle(Buffer buffer) {
        this.buffer.appendBuffer(buffer);
    }
}
