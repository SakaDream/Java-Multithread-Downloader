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
                    if (!Utils.isDownloadComplete()) {
                        System.out.format("%s\r", Utils.printProgressBar());
                    } else {
                        System.out.format("%s\n", Utils.printProgressBar());
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