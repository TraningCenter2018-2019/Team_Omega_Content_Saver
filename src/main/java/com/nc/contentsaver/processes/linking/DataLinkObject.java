package com.nc.contentsaver.processes.linking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataLinkObject {
    @Expose
    @SerializedName("link")
    private String publicLink;
    @Expose(serialize = false, deserialize = false)
    private String sha256;

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
