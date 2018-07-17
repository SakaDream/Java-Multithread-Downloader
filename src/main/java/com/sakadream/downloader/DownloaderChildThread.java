package com.sakadream.downloader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicLong;

/**
 * DownloaderChildThread
 */
public class DownloaderChildThread extends Thread {

    private DownloadPart downloadPart;
    private int partNumber;
    private URL url;
    private String filename;
    private String randomString;
    private AtomicLong downloadedFileSize = new AtomicLong(0);

    public DownloaderChildThread() {
        super();
    }

    public DownloaderChildThread(DownloadPart downloadPart, int partNumber, URL url, String randomString) {
        super();
        this.downloadPart = downloadPart;
        this.partNumber = partNumber;
        this.url = url;
        this.filename = downloadPart.getFilename();
        this.randomString = randomString;
    }

    /**
     * @return the downloadPart
     */
    public DownloadPart getDownloadPart() {
        return downloadPart;
    }

    /**
     * @param downloadPart the downloadPart to set
     */
    public void setDownloadPart(DownloadPart downloadPart) {
        this.downloadPart = downloadPart;
    }

    /**
     * @return the partNumber
     */
    public int getPartNumber() {
        return partNumber;
    }

    /**
     * @param partNumber the partNumber to set
     */
    public void setPartNumber(int partNumber) {
        this.partNumber = partNumber;
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
    public void setRandomString(String randomString) {
        this.randomString = randomString;
    }

    public Long getDownloadedFileSize() {
        return downloadedFileSize.get();
    }

    @Override
    public void run() {
        try {
            // System.out.format("Downloading part %d\n", partNumber + 1);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", Config.getInstance().getUserAgent());
            if (DownloadFile.getInstance().getIsPartialDownload()) {
                connection.setRequestProperty("Range",
                        "bytes=" + downloadPart.getStartByte() + '-' + downloadPart.getEndByte());
            }

            File file = new File(Constaints.TMP_FOLDER, downloadPart.getFilename());
            BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos, Constaints.BUFFER_SIZE);
            byte[] buffer = new byte[Constaints.BUFFER_SIZE];
            int data = 0;

            while ((data = bis.read(buffer, 0, Constaints.BUFFER_SIZE)) >= 0) {
                downloadedFileSize.set(downloadedFileSize.get() + data);
                bos.write(buffer, 0, data);
            }

            bos.flush();
            bos.close();
            bis.close();

            // System.out.format("Download part %s completed!\n", partNumber + 1);
        } catch (IOException ioe) {
            System.err.printf("Download failed in part %s\n", partNumber + 1);
            ioe.printStackTrace();
        }
    }

}