package com.nc.contentsaver.processes.saving.databaseutils;

import com.google.gson.Gson;
import com.nc.contentsaver.processes.linking.DataLinkObject;
import com.nc.contentsaver.utils.ResourceManager;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.nc.contentsaver.processes.saving.model.Tables.DATA_COPY_LINK;
import static com.nc.contentsaver.processes.saving.model.Tables.DATA_LINK;

/**
 * The class describes an object for working with a database.
 */
public final class LinkerDatabaseManager {
    /**
     * Logger Displays information about the connection and
     * information about the operations of interaction with the database.
     */
    private static final Logger LOG = Logger.getLogger(LinkerDatabaseManager.class.getSimpleName());
    /**
     * The only created object to work with the database.
     */
    private static LinkerDatabaseManager instance = new LinkerDatabaseManager();

    static {
        createDSLContext();
    }

    /**
     * Tool for creating sql queries.
     */
    private static DSLContext create;

    /**
     * Creates an object to work with the database.
     */
    private LinkerDatabaseManager() {
    }

    /**
     * Creates DSL Context. It is necessary to build sql.
     * In case of failure, displays a message in the console and closes the program.
     * Receives connection data from file "database_custom.json".
     * If this file does not contain the necessary data, then the file with default data “database.json” is used.
     */
    private static void createDSLContext() {
        DatabaseCredentials credentials = new Gson().fromJson(
                ResourceManager.getStringDataFromFile("database_custom.json"), DatabaseCredentials.class);
        if (credentials == null) {
            credentials = new Gson().fromJson(
                    ResourceManager.getStringDataFromFile("database.json"), DatabaseCredentials.class);
        }
        try {
            Connection connection = DriverManager.getConnection(
                    credentials.getUrl(), credentials.getUserName(), credentials.getPassword());
            create = DSL.using(connection);
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Не удалось создать сервис работы с базой данных", e);
            System.exit(1);
        }

        LOG.info("Сервис работы с базой данных создан!");
    }

    /**
     * Returns a database access object.
     *
     * @return database access object
     */
    public static LinkerDatabaseManager getInstance() {
        return instance;
    }

    /**
     * Saves object to database.
     *
     * @param object object to save
     */
    public void saveObject(DataLinkObject object) {

        Result<Record2<Integer, String>> fetch = create.select(DATA_COPY_LINK.ID, DATA_COPY_LINK.HASH)
                .from(DATA_COPY_LINK)
                .where(DATA_COPY_LINK.HASH.eq(object.getSha256())).fetch();
        if (fetch.isNotEmpty()) {
            Record record = fetch.get(0);
            create.insertInto(DATA_LINK)
                    .columns(DATA_LINK.LINK,
                            DATA_LINK.HASH,
                            DATA_LINK.SAVED,
                            DATA_LINK.COPY_ID)
                    .values(object.getPublicLink(),
                            null,
                            false,
                            (Integer) record.get(0)).execute();
        } else {
            Result<Record2<String, String>> fetch1 = create.select(DATA_LINK.LINK, DATA_LINK.HASH)
                    .from(DATA_LINK)
                    .where(DATA_LINK.HASH.eq(object.getSha256())).fetch();
            if (fetch1.isNotEmpty()) {
                create.transaction(configuration -> {
                    create.insertInto(DATA_COPY_LINK)
                            .columns(DATA_COPY_LINK.HASH)
                            .values(object.getSha256()).execute();
                    Result<Record1<Integer>> fetch2 = create.select(DATA_COPY_LINK.ID)
                            .from(DATA_COPY_LINK)
                            .where(DATA_COPY_LINK.HASH.eq(object.getSha256())).fetch();
                    int copyId = (Integer) fetch2.get(0).get(0);
                    create.update(DATA_LINK)
                            .set(DATA_LINK.HASH, (String) null)
                            .set(DATA_LINK.COPY_ID, copyId)
                            .where(DATA_LINK.HASH.eq(object.getSha256())).execute();
                    create.insertInto(DATA_LINK)
                            .columns(DATA_LINK.LINK, DATA_LINK.HASH, DATA_LINK.SAVED, DATA_LINK.COPY_ID)
                            .values(object.getPublicLink(), null, false, copyId).execute();
                });
            } else {
                create.insertInto(DATA_LINK)
                        .columns(DATA_LINK.LINK, DATA_LINK.HASH, DATA_LINK.SAVED, DATA_LINK.COPY_ID)
                        .values(object.getPublicLink(), object.getSha256(), false, null).execute();
            }
        }
        LOG.info("Объект сохранён в базу данных");
    }

    /**
     * Loads class information into a database. Creates values of static fields.
     */
    public static void load() {
        LOG.info("Класс, обсепечивающий подключение к базе данных, загружен.");
    }
}
