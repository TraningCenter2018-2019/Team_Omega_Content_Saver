package com.nc.contentsaver.processes.managing.databaseutils;

/**
 * The class describes the data to connect to the database.
 */
public class DatabaseCredentials {
    /**
     * Username to connect to the database.
     */
    private String userName;
    /**
     * Password to connect to the database.
     */
    private String password;
    /**
     * JDBC-url to connect to the database.
     */
    private String url;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
