package com.nc.contentsaver.verticles;

/**
 * The class describes the server settings.
 */
public class ServerSettings {
    /**
     * Server port.
     * The default is unavailable port.
     */
    private int port = -1;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
