package com.nc;

import com.google.gson.Gson;
import com.nc.contentsaver.processes.saving.databaseutils.DatabaseCredentials;
import com.nc.contentsaver.processes.saving.databaseutils.LinkerDatabaseManager;
import com.nc.contentsaver.utils.ResourceManager;
import com.nc.contentsaver.verticles.ContentSaverServer;
import com.nc.contentsaver.verticles.ServerProperties;
import io.vertx.core.Vertx;

import java.util.logging.Logger;

public class StartPoint {
    private static final Logger log = Logger.getLogger(StartPoint.class.getSimpleName());

    public static void main(String[] args) throws Exception {
        registerParams(args);
        Vertx vertx = Vertx.vertx();

        LinkerDatabaseManager.load();

        vertx.deployVerticle(new ContentSaverServer());
    }

    private static void registerParams(String[] args) {
        DatabaseCredentials credentials = new DatabaseCredentials();
        ServerProperties server = new ServerProperties();
        for (String arg : args) {
            if (arg.startsWith("db")) {
                if (arg.substring(2).startsWith(".un=")) {
                    String userName = arg.substring(6);
                    credentials.setUserName(userName);
                } else if (arg.substring(2).startsWith(".pw=")) {
                    String password = arg.substring(6);
                    credentials.setPassword(password);
                } else if (arg.substring(2).startsWith(".url=")) {
                    String url = arg.substring(7);
                    credentials.setUrl(url);
                }
            } else if (arg.startsWith("server")) {
                if (arg.substring(6).startsWith(".port=")) {
                    String port = arg.substring(12);
                    try {
                        int iPort = Integer.parseInt(port);
                        if (iPort < 0 || iPort > 65535) {
                            throw new NumberFormatException();
                        }
                        server.setPort(iPort);
                    } catch (NumberFormatException e) {
                        log.warning("Порт сервера указан некорректно, будет использован порт по умолчанию");
                    }
                }
            }
        }
        if (credentials.getUrl() != null
                && credentials.getUserName() != null
                && credentials.getPassword() != null) {
            ResourceManager.writeToResource(new Gson().toJson(credentials), "database_custom.json");
            log.info("Установлены новые настройки подключения к базе данных");
        } else {
            ResourceManager.writeToResource("", "database_custom.json");
            log.info("Установлены настройки подключения к базе данных по умолчанию");
        }
        if (server.getPort() != -1) {
            ResourceManager.writeToResource(new Gson().toJson(server), "server_custom.json");
            log.info("Установлены новые настройки сервера");
        } else {
            ResourceManager.writeToResource("", "server_custom.json");
            log.info("Установлены настройки сервера по умолчанию");
        }
    }
}
