package com.nc.contentsaver.processes.linking;

import org.apache.commons.lang3.RandomStringUtils;

public class DataLinkManager {
    private DataLinkManager(){
    }

    public static DataLinkObject generateLink(){
        String generatedString = RandomStringUtils.randomAlphanumeric(20);
        return new DataLinkObject(generatedString);
    }
}
