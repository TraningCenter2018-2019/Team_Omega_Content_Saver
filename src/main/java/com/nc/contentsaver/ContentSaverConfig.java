package com.nc.contentsaver;

import com.nc.contentsaver.processes.managing.databaseutils.DatabaseCredentials;
import com.nc.contentsaver.verticles.ServerSettings;

/**
 * The class describes application configuration.
 */
public class ContentSaverConfig {
    /**
     * The object that contains data to connect to the database.
     */
    private DatabaseCredentials databaseCredentials;
    /**
     * The object that contains the server settings.
     */
    private ServerSettings serverSettings;

    public DatabaseCredentials getDatabaseCredentials() {
        return databaseCredentials;
    }

    public void setDatabaseCredentials(DatabaseCredentials databaseCredentials) {
        this.databaseCredentials = databaseCredentials;
    }

    public ServerSettings getServerSettings() {
        return serverSettings;
    }

    public void setServerSettings(ServerSettings serverSettings) {
        this.serverSettings = serverSettings;
    }
}
