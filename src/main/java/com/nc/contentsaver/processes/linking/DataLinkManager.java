package com.nc.contentsaver.processes.linking;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Utility class that allows you to generate links.
 */
public final class DataLinkManager {
    /**
     * The length of the link to the file.
     */
    private static final int LINK_LENGTH = 20;

    /**
     * Utility classes should not have a public constructor.
     */
    private DataLinkManager() {
    }

    /**
     * Generates an object that will contain a link to the file.
     *
     * @return object containing a link to the file
     */
    public static DataLinkObject generateLink() {
        String generatedString = RandomStringUtils.randomAlphanumeric(LINK_LENGTH);
        return new DataLinkObject(generatedString);
    }
}
