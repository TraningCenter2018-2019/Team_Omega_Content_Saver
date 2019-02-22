package com.nc.contentsaver;

import com.nc.contentsaver.processes.managing.databaseutils.DatabaseCredentials;
import com.nc.contentsaver.processes.managing.manager.DataManager;
import com.nc.contentsaver.processes.managing.manager.DataSaverBuilder;
import com.nc.contentsaver.verticles.ServerSettings;

import java.util.logging.Logger;

/**
 * The application class to run.
 */
public final class StartPoint {
    /**
     * StartPoint logger.
     */
    private static final Logger LOG = Logger.getLogger(StartPoint.class.getSimpleName());

    /**
     * Utility classes should not have a public constructor.
     */
    private StartPoint() {
    }

    /**
     * Start point of application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        DataManager saver = new DataSaverBuilder().buildDefault();
        ContentSaverConfig config = getContentSaverConfigFromArgs(args);
        new ContentSaver(saver, config);
    }

    /**
     * Get application settings from command line arguments.
     *
     * @param args command line arguments
     * @return application settings object
     */
    private static ContentSaverConfig getContentSaverConfigFromArgs(String[] args) {
        DatabaseCredentials credentials = getCredentialsDataFromArgs(args);
        ServerSettings server = getServerDataFromArgs(args);
        ContentSaverConfig config = new ContentSaverConfig();
        if (credentials.getUrl() != null
                && credentials.getUserName() != null
                && credentials.getPassword() != null) {
            config.setDatabaseCredentials(credentials);
        }
        if (server.getPort() != -1) {
            config.setServerSettings(server);
        }
        return config;
    }

    /**
     * Get database connection settings from command line arguments.
     *
     * @param args command line arguments
     * @return database connection settings object
     */
    private static DatabaseCredentials getCredentialsDataFromArgs(String[] args) {
        DatabaseCredentials credentials = new DatabaseCredentials();
        final String db = "db";
        final String un = ".un=";
        final String pw = ".pw=";
        final String ur = ".url=";
        for (String arg : args) {
            if (arg.startsWith(db)) {
                if (isItProperArg(arg, db, un)) {
                    String userName = arg.substring(db.length() + un.length());
                    credentials.setUserName(userName);
                } else if (isItProperArg(arg, db, pw)) {
                    String password = arg.substring(db.length() + pw.length());
                    credentials.setPassword(password);
                } else if (isItProperArg(arg, db, ur)) {
                    String url = arg.substring(db.length() + ur.length());
                    credentials.setUrl(url);
                }
            }
        }
        return credentials;
    }

    /**
     * Get server settings from command line arguments.
     *
     * @param args command line arguments
     * @return server settings object
     */
    private static ServerSettings getServerDataFromArgs(String[] args) {
        ServerSettings serverSettings = new ServerSettings();
        final String sv = "server";
        final String pt = ".port=";
        final int minPort = 0;
        final int maxPort = 65535;
        for (String arg : args) {
            if (arg.startsWith(sv) && isItProperArg(arg, sv, pt)) {
                String port = arg.substring(sv.length() + pt.length());
                try {
                    int iPort = Integer.parseInt(port);
                    if (iPort < minPort || iPort > maxPort) {
                        throw new NumberFormatException();
                    }
                    serverSettings.setPort(iPort);
                } catch (NumberFormatException e) {
                    LOG.warning("The server port is incorrect, the default port will be used.");
                }
            }
        }
        return serverSettings;
    }

    /**
     * Checks if the argument is a required one.
     *
     * @param arg    argument to check
     * @param params start with params
     * @return true if it is required, false otherwise
     */
    private static boolean isItProperArg(String arg, String... params) {
        int offset = 0;
        for (String param : params) {
            int res = arg.indexOf(param, offset);
            if (res != -1) {
                offset += param.length();
            } else {
                return false;
            }
        }
        return true;
    }
}
