package com.sakadream.downloader;

/**
 * Config
 */
public class Config {

    private Integer numberOfConnections;
    private String downloadsLocation;

    public Config() {
        super();
    }

    public Config(Integer numberOfConnections, String downloadsLocation) {
        super();
        this.numberOfConnections = numberOfConnections;
        this.downloadsLocation = downloadsLocation;
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

}