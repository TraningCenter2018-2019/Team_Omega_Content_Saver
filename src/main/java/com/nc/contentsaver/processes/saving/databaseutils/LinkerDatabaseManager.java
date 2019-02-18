package com.nc.contentsaver.processes.saving.databaseutils;

import com.google.gson.Gson;
import com.nc.contentsaver.processes.linking.DataLinkObject;
import com.nc.contentsaver.utils.ResourceManager;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.nc.contentsaver.processes.saving.model.Tables.DATA_COPY_LINK;
import static com.nc.contentsaver.processes.saving.model.Tables.DATA_LINK;

public class LinkerDatabaseManager {
    private static LinkerDatabaseManager instance = new LinkerDatabaseManager();
    private static Connection connection;
    private static DSLContext create;

    private LinkerDatabaseManager() {
        DatabaseCredentials credentials = new Gson().fromJson(
                ResourceManager.getStringDataFromFile("database_custom.json"), DatabaseCredentials.class);
        if (credentials == null) {
            credentials = new Gson().fromJson(
                    ResourceManager.getStringDataFromFile("database.json"), DatabaseCredentials.class);
        }
        try {
            connection = DriverManager.getConnection(
                    credentials.getUrl(), credentials.getUserName(), credentials.getPassword());
            create = DSL.using(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось создать сервис работы с базой данных");
            System.exit(1);
        }

        System.out.println("Сервис работы с базой данных создан!");
    }

    public static LinkerDatabaseManager getInstance() {
        return instance;
    }

    public void saveObject(DataLinkObject object) {
        System.out.println("Объект сохранён в базу данных");

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
    }

    public static void load() {
        getInstance();
    }
}
