package com.nc.contentsaver.processes.managing.manager.buffer;

import io.vertx.core.buffer.Buffer;

import java.io.IOException;

/**
 * Interface of managing the content.
 */
public interface DataBufferManager {
    /**
     * Saves content data.
     *
     * @param buffer content data
     * @param hash   hash of content
     * @throws IOException in case of data storage error
     */
    void saveBuffer(Buffer buffer, String hash) throws IOException;

    /**
     * Returns data on their hash.
     *
     * @param hash hash of data
     * @return buffer of data
     * @throws IOException in case of data getting error
     */
    Buffer restoreBuffer(String hash) throws IOException;
}
