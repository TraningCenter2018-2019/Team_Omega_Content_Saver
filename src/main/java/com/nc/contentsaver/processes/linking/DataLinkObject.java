package com.nc.contentsaver.processes.linking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The class describes an object containing a link to a file and a file hash.
 */
public class DataLinkObject {
    /**
     * Link to file. Does not include the host, port, scheme, and so on.
     */
    @Expose
    @SerializedName("link")
    private String publicLink;

    /**
     * File hash. Needed to avoid file duplication.
     */
    @Expose(serialize = false, deserialize = false)
    private String sha256;

    /**
     * Creates an object that will contain a link to the file and the file hash.
     *
     * @param publicLink link to the file
     */
    public DataLinkObject(String publicLink) {
        this.publicLink = publicLink;
    }

    public String getPublicLink() {
        return publicLink;
    }

    public void setPublicLink(String publicLink) {
        this.publicLink = publicLink;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }
}
