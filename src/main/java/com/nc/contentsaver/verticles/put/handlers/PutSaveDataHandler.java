package com.nc.contentsaver.verticles.put.handlers;

import com.nc.contentsaver.processes.linking.DataLinkBufferObject;
import com.nc.contentsaver.processes.linking.DataLinkObject;
import com.nc.contentsaver.processes.saving.AMQP;
import com.nc.contentsaver.utils.Hasher;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;

/**
 * Handler for saving data that came to the server through a PUT request.
 */
public class PutSaveDataHandler implements Handler<Void> {
    /**
     * Content data.
     */
    private Buffer data;
    /**
     * Link to download content.
     */
    private DataLinkObject link;
    /**
     * The request object to break the connection after the data has been read.
     */
    private HttpServerRequest request;

    /**
     * Creates an object that will store the content data when it has been read.
     * Todo: Find out whether the client can always have time to read the link before breaking the connection
     *
     * @param data    content data
     * @param link    link to download content
     * @param request the request object to break the connection after the data has been read
     */
    public PutSaveDataHandler(Buffer data, DataLinkObject link, HttpServerRequest request) {
        this.data = data;
        this.link = link;
        this.request = request;
    }

    @Override
    public void handle(Void event) {
        request.response().end();
        request.connection().close();
        this.link.setSha256(Hasher.getSha256(data));
        DataLinkBufferObject toSave = new DataLinkBufferObject(link, data);
        AMQP.getInstance().saveData(toSave);
    }
}
