package com.nc.contentsaver.verticles.put;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nc.contentsaver.processes.linking.DataLinkManager;
import com.nc.contentsaver.processes.linking.DataLinkObject;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerRequest;

import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

/**
 * The class describes an object that can return an answer to the client - a link to download the file.
 */
public class PutFileLinkGetter {
    /**
     * Logger. Displays a message before returning a response to the client.
     */
    private static final Logger LOG = Logger.getLogger(PutFileLinkGetter.class.getSimpleName());
    /**
     * Request object.
     * It is necessary to receive from it response object and to write there the answer.
     */
    private HttpServerRequest request;

    /**
     * Creates an object that returns a link to the file to the client.
     *
     * @param request request from the client to upload file
     */
    public PutFileLinkGetter(HttpServerRequest request) {
        this.request = request;
    }

    /**
     * Returns a link to download the file to the client.
     *
     * @return The object to be saved
     */
    public DataLinkObject returnFileLinkToClient() {
        DataLinkObject linkToData = DataLinkManager.generateLink();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(linkToData);
        request.response().headers()
                .add(HttpHeaders.CONTENT_TYPE, "application/json")
                .add(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8.name())
                .add(HttpHeaders.CONTENT_LENGTH, json.length() + "");
        LOG.info("Возвращаем ссылку на файл клиенту");
        request.response().write(Buffer.buffer(json));
        return linkToData;
    }
}
