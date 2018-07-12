package com.sakadream.downloader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Downloader
 */
public class Downloader {
    private List<DownloaderChildThread> childThreads = new ArrayList<>();

    private static Downloader instance = null;

    protected Downloader() {
    }

    public static Downloader getInstance() {
        if (Objects.isNull(instance)) {
            instance = new Downloader();
        }
        return instance;
    }

    public List<DownloaderChildThread> getDownloaderChildThreads() {
        return childThreads;
    }

    public boolean startDownload() {
        Timer timer = Timer.getInstance();
        timer.setStartTime();

        List<DownloadPart> downloadParts = DownloadFile.getInstance().getDownloadParts();
        for (int i = 0; i < downloadParts.size(); i++) {
            DownloadPart downloadPart = downloadParts.get(i);
            DownloaderChildThread childThread = new DownloaderChildThread(downloadPart, i,
                    DownloadFile.getInstance().getUrl(), DownloadFile.getInstance().getRandomString());
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