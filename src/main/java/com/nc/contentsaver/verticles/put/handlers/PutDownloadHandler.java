package com.nc.contentsaver.verticles.put.handlers;


import com.nc.contentsaver.verticles.handlers.BufferHandler;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;

public class PutDownloadHandler implements Handler<Buffer> {
    private BufferHandler bufferHandler;
    private HttpServerRequest request;

    public PutDownloadHandler(Buffer buffer, HttpServerRequest request){
        this.bufferHandler = new BufferHandler(buffer);
        this.request = request;
    }

    @Override
    public void handle(Buffer buffer) {
        bufferHandler.handle(buffer);
    }
}
