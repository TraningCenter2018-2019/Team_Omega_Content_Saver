/*
 * This file is generated by jOOQ.
 */
package com.nc.contentsaver.processes.managing.model.tables;


import com.nc.contentsaver.processes.managing.model.Indexes;
import com.nc.contentsaver.processes.managing.model.Keys;
import com.nc.contentsaver.processes.managing.model.Public;
import com.nc.contentsaver.processes.managing.model.tables.records.DataLinkRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DataLink extends TableImpl<DataLinkRecord> {

    private static final long serialVersionUID = -1236743559;

    /**
     * The reference instance of <code>public.data_link</code>
     */
    public static final DataLink DATA_LINK = new DataLink();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DataLinkRecord> getRecordType() {
        return DataLinkRecord.class;
    }

    /**
     * The column <code>public.data_link.link</code>.
     */
    public final TableField<DataLinkRecord, String> LINK = createField("link", org.jooq.impl.SQLDataType.VARCHAR(20).nullable(false), this, "");

    /**
     * The column <code>public.data_link.hash</code>.
     */
    public final TableField<DataLinkRecord, String> HASH = createField("hash", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.data_link.saved</code>.
     */
    public final TableField<DataLinkRecord, Boolean> SAVED = createField("saved", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>public.data_link.copy_id</code>.
     */
    public final TableField<DataLinkRecord, Integer> COPY_ID = createField("copy_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * Create a <code>public.data_link</code> table reference
     */
    public DataLink() {
        this(DSL.name("data_link"), null);
    }

    /**
     * Create an aliased <code>public.data_link</code> table reference
     */
    public DataLink(String alias) {
        this(DSL.name(alias), DATA_LINK);
    }

    /**
     * Create an aliased <code>public.data_link</code> table reference
     */
    public DataLink(Name alias) {
        this(alias, DATA_LINK);
    }

    private DataLink(Name alias, Table<DataLinkRecord> aliased) {
        this(alias, aliased, null);
    }

    private DataLink(Name alias, Table<DataLinkRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> DataLink(Table<O> child, ForeignKey<O, DataLinkRecord> key) {
        super(child, key, DATA_LINK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.DATA_LINK_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<DataLinkRecord> getPrimaryKey() {
        return Keys.DATA_LINK_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<DataLinkRecord>> getKeys() {
        return Arrays.<UniqueKey<DataLinkRecord>>asList(Keys.DATA_LINK_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<DataLinkRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<DataLinkRecord, ?>>asList(Keys.DATA_LINK__DATA_LINK_COPY_ID_FKEY);
    }

    public DataCopyLink dataCopyLink() {
        return new DataCopyLink(this, Keys.DATA_LINK__DATA_LINK_COPY_ID_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataLink as(String alias) {
        return new DataLink(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataLink as(Name alias) {
        return new DataLink(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public DataLink rename(String name) {
        return new DataLink(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public DataLink rename(Name name) {
        return new DataLink(name, null);
    }
}
