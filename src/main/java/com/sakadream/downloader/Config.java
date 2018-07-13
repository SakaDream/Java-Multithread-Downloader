package com.sakadream.downloader;

import java.util.Objects;

/**
 * Config
 */
public class Config {

    private Integer numberOfConnections;
    private String downloadsLocation;
    private Boolean useSystemProxy;

    private static Config instance = null;

    protected Config() {
    }

    public static Config getInstance() {
        if (Objects.isNull(instance)) {
            instance = new Config();
        }
        return instance;
    }

    /**
     * @return the numberOfConnections
     */
    public Integer getNumberOfConnections() {
        return numberOfConnections;
    }

    /**
     * @param numberOfConnections the numberOfConnections to set
     */
    public void setNumberOfConnections(Integer numberOfConnections) {
        this.numberOfConnections = numberOfConnections;
    }

    /**
     * @return the downloadsLocation
     */
    public String getDownloadsLocation() {
        return downloadsLocation;
    }

    /**
     * @param downloadsLocation the downloadsLocation to set
     */
    public void setDownloadsLocation(String downloadsLocation) {
        this.downloadsLocation = downloadsLocation;
    }

    /**
     * @return the useSystemProxy
     */
    public Boolean getUseSystemProxy() {
        return useSystemProxy;
    }

    /**
     * @param useSystemProxy the useSystemProxy to set
     */
    public void setUseSystemProxy(Boolean useSystemProxy) {
        this.useSystemProxy = useSystemProxy;
    }

}