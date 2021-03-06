/*
 * This file is generated by jOOQ.
 */
package com.nc.contentsaver.processes.managing.model;


import com.nc.contentsaver.processes.managing.model.tables.DataCopyLink;
import com.nc.contentsaver.processes.managing.model.tables.DataLink;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


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
public class Public extends SchemaImpl {

    private static final long serialVersionUID = -1380808119;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.data_copy_link</code>.
     */
    public final DataCopyLink DATA_COPY_LINK = com.nc.contentsaver.processes.managing.model.tables.DataCopyLink.DATA_COPY_LINK;

    /**
     * The table <code>public.data_link</code>.
     */
    public final DataLink DATA_LINK = com.nc.contentsaver.processes.managing.model.tables.DataLink.DATA_LINK;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        List result = new ArrayList();
        result.addAll(getSequences0());
        return result;
    }

    private final List<Sequence<?>> getSequences0() {
        return Arrays.<Sequence<?>>asList(
            Sequences.DATA_COPY_LINK_SEQ);
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            DataCopyLink.DATA_COPY_LINK,
            DataLink.DATA_LINK);
    }
}
