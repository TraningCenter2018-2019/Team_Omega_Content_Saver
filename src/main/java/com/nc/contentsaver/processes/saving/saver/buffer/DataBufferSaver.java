package com.nc.contentsaver.processes.saving.saver.buffer;

import io.vertx.core.buffer.Buffer;

import java.io.IOException;

public interface DataBufferSaver {
    void saveBuffer(Buffer buffer, String hash) throws IOException;
}
