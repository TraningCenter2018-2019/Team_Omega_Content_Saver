package com.nc.contentsaver;

import com.google.gson.Gson;
import com.nc.contentsaver.processes.saving.AMQP;
import com.nc.contentsaver.processes.saving.databaseutils.DatabaseCredentials;
import com.nc.contentsaver.processes.saving.databaseutils.LinkerDatabaseManager;
import com.nc.contentsaver.processes.saving.saver.DataSaver;
import com.nc.contentsaver.utils.ResourceManager;
import com.nc.contentsaver.verticles.ContentSaverServer;
import com.nc.contentsaver.verticles.ServerSettings;
import io.vertx.core.Vertx;

import java.util.logging.Logger;

/**
 * Class describes an object that will save the content.
 */
public class ContentSaver {
    /**
     * Logger. Displays object configuration information.
     */
    private static final Logger LOG = Logger.getLogger(ContentSaver.class.getSimpleName());

    /**
     * Creates an object that will save the content.
     *
     * @param dataSaver content saver
     */
    public ContentSaver(DataSaver dataSaver) {
        this(dataSaver, null);
    }

    /**
     * Creates an object that will save the content.
     *
     * @param dataSaver content saver
     * @param config    config object
     */
    public ContentSaver(DataSaver dataSaver, ContentSaverConfig config) {
        config(config);
        AMQP.getInstance().addObserver(dataSaver);

        LinkerDatabaseManager.load();
        Vertx.vertx().deployVerticle(new ContentSaverServer());
    }

    /**
     * Configures an object to save content.
     *
     * @param config config object
     */
    private void config(ContentSaverConfig config) {
        if (config == null) {
            return;
        }
        DatabaseCredentials credentials = config.getDatabaseCredentials();
        ServerSettings server = config.getServerSettings();
        if (credentials != null
                && credentials.getUrl() != null
                && credentials.getUserName() != null
                && credentials.getPassword() != null) {
            ResourceManager.writeToResource(new Gson().toJson(credentials), "database_custom.json");
            LOG.info("Установлены новые настройки подключения к базе данных");
        } else {
            ResourceManager.writeToResource("", "database_custom.json");
            LOG.info("Установлены настройки подключения к базе данных по умолчанию");
        }

        if (server != null
                && server.getPort() != -1) {
            ResourceManager.writeToResource(new Gson().toJson(server), "server_custom.json");
            LOG.info("Установлены новые настройки сервера");
        } else {
            ResourceManager.writeToResource("", "server_custom.json");
            LOG.info("Установлены настройки сервера по умолчанию");
        }
    }
}
