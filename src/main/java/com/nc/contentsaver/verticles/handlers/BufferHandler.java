package com.nc.contentsaver.verticles.handlers;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;

public class BufferHandler implements Handler<Buffer> {
    private Buffer buffer;

    public BufferHandler(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void handle(Buffer buffer) {
        this.buffer.appendBuffer(buffer);
    }
}
