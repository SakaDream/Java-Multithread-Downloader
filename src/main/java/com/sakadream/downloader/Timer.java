package com.sakadream.downloader;

import java.util.Objects;

/**
 * Timer
 */
public class Timer {

    private Long startTime;
    private Long downloadCompletedTime;
    private Long mergeFileCompletedTime;

    private static Timer instance = null;

    protected Timer() {
    }

    public static Timer getInstance() {
        if (Objects.isNull(instance)) {
            instance = new Timer();
        }
        return instance;
    }

    /**
     * @return the startTime
     */
    public Long getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime() {
        this.startTime = System.currentTimeMillis();
    }

    /**
     * @return the downloadCompletedTime
     */
    public Long getDownloadCompletedTime() {
        return downloadCompletedTime;
    }

    /**
     * @param downloadCompletedTime the downloadCompletedTime to set
     */
    public void setDownloadCompletedTime() {
        this.downloadCompletedTime = System.currentTimeMillis();
    }

    /**
     * @return the mergeFileCompletedTime
     */
    public Long getMergeFileCompletedTime() {
        return mergeFileCompletedTime;
    }

    /**
     * @param mergeFileCompletedTime the mergeFileCompletedTime to set
     */
    public void setMergeFileCompletedTime() {
        this.mergeFileCompletedTime = System.currentTimeMillis();
    }

}