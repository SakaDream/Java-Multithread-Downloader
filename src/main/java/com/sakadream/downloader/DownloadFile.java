package com.sakadream.downloader;

import java.net.URL;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * DownloadFile
 */
public class DownloadFile {

    private String filename;
    private Long fileSize;
    private String randomString;
    private URL url;
    private Boolean isPartialDownload;
    private List<DownloadPart> downloadParts;

    private static DownloadFile instance = null;

    protected DownloadFile() {
    }

    public static DownloadFile getInstance() {
        if (Objects.isNull(instance)) {
            instance = new DownloadFile();
        }
        return instance;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return the fileSize
     */
    public Long getFileSize() {
        return fileSize;
    }

    /**
     * @param fileSize the fileSize to set
     */
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * @return the randomString
     */
    public String getRandomString() {
        return randomString;
    }

    /**
     * @param randomString the randomString to set
     */
    public void setRandomString() {
        this.randomString = RandomStringUtils.randomAlphanumeric(16);
    }

    /**
     * @return the url
     */
    public URL getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(URL url) {
        this.url = url;
    }

    /**
     * @return the isPartialDownload
     */
    public Boolean getIsPartialDownload() {
        return isPartialDownload;
    }

    /**
     * @param isPartialDownload the isPartialDownload to set
     */
    public void setIsPartialDownload(Boolean isPartialDownload) {
        this.isPartialDownload = isPartialDownload;
    }

    /**
     * @return the downloadParts
     */
    public List<DownloadPart> getDownloadParts() {
        return downloadParts;
    }

    /**
     * @param downloadParts the downloadParts to set
     */
    public void setDownloadParts(List<DownloadPart> downloadParts) {
        this.downloadParts = downloadParts;
    }

}