package com.sakadream.downloader;

import java.util.ArrayList;
import java.util.List;

/**
 * Downloader
 */
public class Downloader {
    private DownloadFile downloadFile;
    private List<DownloaderChildThread> childThreads;

    public Downloader() {
        super();
    }

    public Downloader(DownloadFile downloadFile) {
        super();
        this.downloadFile = downloadFile;
        this.childThreads = new ArrayList<>();
    }

    /**
     * @return the downloadFile
     */
    public DownloadFile getDownloadFile() {
        return downloadFile;
    }

    /**
     * @param downloadFile the downloadFile to set
     */
    public void setDownloadFile(DownloadFile downloadFile) {
        this.downloadFile = downloadFile;
    }

    public boolean startDownload() {
        Timer timer = Timer.getInstance();
        timer.setStartTime();

        List<DownloadPart> downloadParts = this.downloadFile.getDownloadParts();
        for (int i = 0; i < downloadParts.size(); i++) {
            DownloadPart downloadPart = downloadParts.get(i);
            DownloaderChildThread childThread = new DownloaderChildThread(downloadPart, i, downloadFile.getUrl(),
                    downloadFile.getRandomString());
            childThreads.add(childThread);
            childThread.start();
        }

        for (DownloaderChildThread childThread : childThreads) {
            try {
                childThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }
        timer.setDownloadCompletedTime();
        return true;
    }

}