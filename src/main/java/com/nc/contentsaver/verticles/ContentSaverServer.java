package com.nc.contentsaver.verticles;

import com.google.gson.Gson;
import com.nc.contentsaver.processes.linking.DataLinkObject;
import com.nc.contentsaver.processes.managing.AMQP;
import com.nc.contentsaver.processes.managing.manager.DataManager;
import com.nc.contentsaver.utils.ResourceManager;
import com.nc.contentsaver.verticles.get.ContentGetter;
import com.nc.contentsaver.verticles.handlers.BufferHandler;
import com.nc.contentsaver.verticles.put.PutFileLinkGetter;
import com.nc.contentsaver.verticles.put.handlers.PutSaveDataHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Application server.
 * <br>
 * Supports three requests:
 * <br>
 * <ol>
 * <li><b>PUT</b>: in the request body data about the file to be saved.
 * Returns a json containing a relative link to download the file.</li>
 * <li><b>GET</b>: executed by the host:port/link path, where link is the parameter that returned the PUT request.
 * Will return the file data. In case of an error, it will return a json file describing the error.</li>
 * <li><b>DELETE</b>: runs as GET. Will return json with the results of the file deletion.</li>
 * </ol>
 */
public class ContentSaverServer extends AbstractVerticle {
    /**
     * An object that allows you to save and retrieve content.
     */
    private DataManager dataManager;

    /**
     * Creates content saver.
     *
     * @param dataManager an object that allows you to save and retrieve content
     */
    public ContentSaverServer(DataManager dataManager) {
        this.dataManager = dataManager;
        AMQP.getInstance().addObserver(dataManager);
    }

    /**
     * Logger Displays service information about the status of the server.
     */
    private static final Logger LOG = Logger.getLogger(ContentSaverServer.class.getSimpleName());

    @Override
    public void start() throws Exception {
        HttpServer server = vertx.createHttpServer();

        server.requestHandler(request -> {
            Buffer body = Buffer.buffer();
            switch (request.method()) {
                case GET:
                    String link = request.path().substring(1);
                    new ContentGetter(link, dataManager).response(request.response());
                    request.connection().close();
                    break;
                case PUT:
                    DataLinkObject entity = new PutFileLinkGetter(request).returnFileLinkToClient();
                    request.handler(new BufferHandler(body));
                    request.endHandler(new PutSaveDataHandler(body, entity, request));
                    break;
                case DELETE:
                    break;
                default:
                    break;
            }
        });


        int port = getServerProperties().getPort();
        server.listen(port);
        LOG.log(Level.INFO, "Created http-server [port:{0}]!", Integer.toString(port));
    }

    /**
     * Returns server settings.
     *
     * @return server settings
     */
    private ServerSettings getServerProperties() {
        ServerSettings props = new Gson().fromJson(
                ResourceManager.getStringDataFromFile("server_custom.json"),
                ServerSettings.class);
        if (props == null) {
            props = new Gson().fromJson(ResourceManager.getStringDataFromFile("server.json"),
                    ServerSettings.class);
        }
        return props;
    }
}
