package com.nc.contentsaver.processes.saving.saver.buffer;

import io.vertx.core.buffer.Buffer;

import java.io.IOException;

/**
 * Interface of saving the content.
 */
public interface DataBufferSaver {
    /**
     * Saves content data.
     *
     * @param buffer content data
     * @param hash   hash of content
     * @throws IOException in case of data storage error
     */
    void saveBuffer(Buffer buffer, String hash) throws IOException;
}
