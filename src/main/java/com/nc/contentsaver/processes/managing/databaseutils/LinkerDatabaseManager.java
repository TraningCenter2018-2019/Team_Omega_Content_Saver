package com.nc.contentsaver.processes.managing.databaseutils;

import com.google.gson.Gson;
import com.nc.contentsaver.exceptions.ContentNotFoundException;
import com.nc.contentsaver.processes.linking.DataLinkObject;
import com.nc.contentsaver.utils.ResourceManager;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.nc.contentsaver.processes.managing.model.Tables.DATA_COPY_LINK;
import static com.nc.contentsaver.processes.managing.model.Tables.DATA_LINK;

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
            LOG.log(Level.SEVERE, "Could not create database service.", e);
            System.exit(1);
        }

        LOG.info("The database service has been created.");
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
            saveWithForeignKeyReferringToHash(object, record);
            object.setSaved(true);
        } else {
            Result<Record2<String, String>> fetch1 = create.select(DATA_LINK.LINK, DATA_LINK.HASH)
                    .from(DATA_LINK)
                    .where(DATA_LINK.HASH.eq(object.getSha256())).fetch();
            if (fetch1.isNotEmpty()) {
                saveWithHashTransferToAnotherTable(object);
                object.setSaved(true);
            } else {
                saveWithUniqueHash(object);
                object.setSaved(false);
            }
        }
        LOG.info("Object saved to database.");
    }

    /**
     * Indicates in the database that the object was successfully written to disk.
     *
     * @param object object to update
     */
    public void markAsSaved(DataLinkObject object) {
        create.update(DATA_LINK)
                .set(DATA_LINK.SAVED, true)
                .where(DATA_LINK.LINK.eq(object.getPublicLink())).execute();
    }

    /**
     * Returns an object from the database.
     *
     * @param link attribute link
     * @return data link bject
     * @throws ContentNotFoundException if the information about the content is not found or it has not yet been saved
     */
    public DataLinkObject getLinkObject(String link) throws ContentNotFoundException {
        Result<Record3<String, Boolean, Integer>> fetch = create
                .select(DATA_LINK.HASH, DATA_LINK.SAVED, DATA_LINK.COPY_ID)
                .from(DATA_LINK)
                .where(DATA_LINK.LINK.eq(link)).fetch();
        DataLinkObject result = new DataLinkObject(link);
        if (fetch.isEmpty()) {
            throw new ContentNotFoundException(false);
        }
        boolean saved = (Boolean) fetch.get(0).get(1);
        if (!saved) {
            throw new ContentNotFoundException(true);
        }
        String hash = (String) fetch.get(0).get(0);
        if (hash == null) {
            Result<Record1<String>> fetch1 = create.select(DATA_COPY_LINK.HASH)
                    .from(DATA_COPY_LINK)
                    .where(DATA_COPY_LINK.ID.eq((Integer) fetch.get(0).get(2))).fetch();
            result.setSha256((String) fetch1.get(0).get(0));
        } else {
            result.setSha256(hash);
        }
        result.setSaved(true);
        return result;
    }

    /**
     * This method is called when there is a separate entry in the database with the same hash.
     * In this case, this entry is stored with reference to that entry.
     *
     * @param object object to save
     * @param record separate entry with the same hash
     */
    private void saveWithForeignKeyReferringToHash(DataLinkObject object, Record record) {
        create.insertInto(DATA_LINK)
                .columns(DATA_LINK.LINK,
                        DATA_LINK.HASH,
                        DATA_LINK.SAVED,
                        DATA_LINK.COPY_ID)
                .values(object.getPublicLink(),
                        null,
                        false,
                        (Integer) record.get(0)).execute();
    }

    /**
     * This method is called when there are no records in the database with such a hash.
     *
     * @param object object to save
     */
    private void saveWithUniqueHash(DataLinkObject object) {
        create.insertInto(DATA_LINK)
                .columns(DATA_LINK.LINK, DATA_LINK.HASH, DATA_LINK.SAVED, DATA_LINK.COPY_ID)
                .values(object.getPublicLink(), object.getSha256(), false, null).execute();
    }

    /**
     * This method is called when there are already records in the database that have this hash.
     * In this case, a separate hash entry is created,
     * and all entries that have this hash (including this entry) receive a link to a separate entry.
     *
     * @param object object to save
     */
    private void saveWithHashTransferToAnotherTable(DataLinkObject object) {
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
    }

    /**
     * Loads class information into a database. Creates values of static fields.
     */
    public static void load() {
        LOG.info("The class providing the connection to the database is loaded.");
    }
}
