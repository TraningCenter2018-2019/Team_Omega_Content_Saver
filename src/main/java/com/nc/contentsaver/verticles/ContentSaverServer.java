package com.nc.contentsaver.verticles;

import com.google.gson.Gson;
import com.nc.contentsaver.processes.linking.DataLinkObject;
import com.nc.contentsaver.utils.ResourceManager;
import com.nc.contentsaver.verticles.handlers.BufferHandler;
import com.nc.contentsaver.verticles.put.PutFileLinkGetter;
import com.nc.contentsaver.verticles.put.handlers.PutSaveDataHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;

import java.util.logging.Logger;

public class ContentSaverServer extends AbstractVerticle {
    private static final Logger log = Logger.getLogger(ContentSaverServer.class.getSimpleName());
    private HttpServer server;

    @Override
    public void start() throws Exception {
        server = vertx.createHttpServer();

        server.requestHandler(request -> {
            Buffer body = Buffer.buffer();
            switch (request.method()){
                case GET:
                    break;
                case PUT:
                    DataLinkObject entity = new PutFileLinkGetter(request).returnFileLinkToClient();
                    request.handler(new BufferHandler(body));
                    request.endHandler(new PutSaveDataHandler(body, entity, request));
                    break;
                case DELETE:
                    break;
            }
        });

        ServerProperties props = new Gson().fromJson(ResourceManager.getStringDataFromFile("server_custom.json"),
                ServerProperties.class);
        if (props == null) {
            props = new Gson().fromJson(ResourceManager.getStringDataFromFile("server.json"),
                ServerProperties.class);
        }
        int port = props.getPort();
        server.listen(port);
        log.info("Создан http-сервер [port:" + port + "]!");
    }
}
