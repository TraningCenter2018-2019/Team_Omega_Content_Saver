package com.nc.contentsaver.verticles.get;

import com.nc.contentsaver.exceptions.ContentNotFoundException;
import com.nc.contentsaver.processes.linking.DataLinkBufferObject;
import com.nc.contentsaver.processes.managing.manager.DataManager;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;

/**
 * The class allows you to receive data using {@link DataManager} and return it to the client.
 */
public class ContentGetter {
    /**
     * Link to file to get.
     */
    private String link;
    /**
     * An object that allows you to save and retrieve content.
     */
    private DataManager dataManager;

    /**
     * Creates content getter object.
     *
     * @param link link to file to get
     * @param dataManager data manager
     */
    public ContentGetter(String link, DataManager dataManager) {
        this.link = link;
        this.dataManager = dataManager;
    }

    /**
     * Reads content data and responds to the client with content (or an error in case content was not found).
     *
     * @param response response object
     */
    public void response(HttpServerResponse response) {
        DataLinkBufferObject data;
        try {
            data = dataManager.getData(link);
            response.headers().set(HttpHeaders.CONTENT_LENGTH,
                    String.valueOf(data.getBuffer().length()))
                    .set(HttpHeaders.CONTENT_TYPE, "application/binary");
            response.end(data.getBuffer());
        } catch (ContentNotFoundException e) {
            String json = String.format("{\"error\":\"%s\"}", e.getMessage());
            final int notFound = 404;
            response.headers()
                    .set(HttpHeaders.CONTENT_LENGTH, String.valueOf(json.length()))
                    .set(HttpHeaders.CONTENT_TYPE, "application/json");
            response.setStatusCode(notFound).setStatusMessage("Not Found");
            response.end(Buffer.buffer(json));
        }
    }
}
