package com.nc.contentsaver.verticles.put;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nc.contentsaver.processes.linking.DataLinkManager;
import com.nc.contentsaver.processes.linking.DataLinkObject;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerRequest;

public class PutFileLinkGetter {
    private HttpServerRequest request;

    public PutFileLinkGetter(HttpServerRequest request) {
        this.request = request;
    }

    public DataLinkObject returnFileLinkToClient() {
        DataLinkObject linkToData = DataLinkManager.generateLink();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(linkToData);
        request.response().headers()
                .add(HttpHeaders.CONTENT_TYPE, "application/json")
                .add(HttpHeaders.CONTENT_ENCODING, "UTF-8")
                .add(HttpHeaders.CONTENT_LENGTH, json.length() + "");
        System.out.println("Возвращаем ссылку на файл клиенту");
        request.response().write(Buffer.buffer(json));
        return linkToData;
    }
}
