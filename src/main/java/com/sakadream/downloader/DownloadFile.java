package com.sakadream.downloader;

import java.net.URL;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * DownloadFile
 */
public class DownloadFile {

    private String filename;
    private String randomString;
    private URL url;
    private List<DownloadPart> downloadParts;

    public DownloadFile() {
        super();
    }

    public DownloadFile(String filename, URL url, List<DownloadPart> downloadParts) {
        super();
        this.filename = filename;
        this.randomString = RandomStringUtils.randomAlphanumeric(16);
        this.url = url;
        this.downloadParts = downloadParts;
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