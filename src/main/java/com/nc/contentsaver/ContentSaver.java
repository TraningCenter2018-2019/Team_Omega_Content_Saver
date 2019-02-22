package com.nc.contentsaver;

import com.google.gson.Gson;
import com.nc.contentsaver.processes.managing.databaseutils.DatabaseCredentials;
import com.nc.contentsaver.processes.managing.databaseutils.LinkerDatabaseManager;
import com.nc.contentsaver.processes.managing.manager.DataManager;
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
     * @param dataManager content saver
     */
    public ContentSaver(DataManager dataManager) {
        this(dataManager, null);
    }

    /**
     * Creates an object that will save the content.
     *
     * @param dataManager content saver
     * @param config    config object
     */
    public ContentSaver(DataManager dataManager, ContentSaverConfig config) {
        config(config);

        LinkerDatabaseManager.load();
        Vertx.vertx().deployVerticle(new ContentSaverServer(dataManager));
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
            LOG.info("New database connection settings are set.");
        } else {
            ResourceManager.writeToResource("", "database_custom.json");
            LOG.info("Default database connection settings are set.");
        }

        if (server != null
                && server.getPort() != -1) {
            ResourceManager.writeToResource(new Gson().toJson(server), "server_custom.json");
            LOG.info("New server settings are set.");
        } else {
            ResourceManager.writeToResource("", "server_custom.json");
            LOG.info("Default server settings are set.");
        }
    }
}
