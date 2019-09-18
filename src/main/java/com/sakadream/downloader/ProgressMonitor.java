package com.sakadream.downloader;

import java.util.Objects;

/**
 * ProgressMonitor
 */
public class ProgressMonitor extends Thread {

    private static ProgressMonitor instance = null;

    protected ProgressMonitor() {
    }

    public static ProgressMonitor getInstance() {
        if (Objects.isNull(instance)) {
            instance = new ProgressMonitor();
        }
        return instance;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
                if (Utils.isReadyToDownload()) {
                    System.out.format("\r%s", Utils.printProgressBar());
                    if (Utils.isDownloadComplete()) {
                        System.out.println();
                        break;
                    }
                } else {
                    continue;
                }
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

}