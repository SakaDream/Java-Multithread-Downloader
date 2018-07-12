package com.sakadream.downloader;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws InterruptedException, ApplicationException {
        if (args.length == 0) {
            System.out.println(
                    "Please input URL, ex: java -jar target\\multithread-downloader-1.0-SNAPSHOT-jar-with-dependencies.jar https://example.com/path/file.txt");
        } else {
            DownloadFile downloadFile = DownloadFile.getInstance();
            HttpURLConnection.setFollowRedirects(false);
            try {
                String urlStr = Utils.getUrl(args);
                Config config = Config.getInstance();
                Utils.setConfig(args);
                Utils.checkDownloadsDirectory();

                if (Objects.isNull(urlStr)) {
                    System.err.println("URL not found!");
                    System.exit(1);
                }

                Utils.getDownloadFileInfo(urlStr);
                String filename = DownloadFile.getInstance().getFilename();

                System.out.format("Filename: %s\n", filename);
                System.out.format("File size: %s\n", Utils.humanReadableByteCount(downloadFile.getFileSize(), true));

                boolean isPartialDownload = Utils.canPartialDownloading();
                System.out.format("Partial Download? %s\n", isPartialDownload);

                System.out.println();

                List<DownloadPart> downloadParts = new ArrayList<>();

                if (isPartialDownload) {
                    List<List<Long>> listOfStartBytesAndEndBytes = Utils.getListOfStartBytesAndEndBytes(
                            downloadFile.getFileSize(), config.getNumberOfConnections());

                    for (int i = 0; i < listOfStartBytesAndEndBytes.size(); i++) {
                        List<Long> startBytesAndEndBytes = listOfStartBytesAndEndBytes.get(i);
                        DownloadPart downloadPart = new DownloadPart(
                                Utils.getFilePartName(filename, downloadFile.getRandomString(), i),
                                startBytesAndEndBytes.get(Constaints.START_BYTES_INDEX),
                                startBytesAndEndBytes.get(Constaints.END_BYTES_INDEX));
                        downloadParts.add(downloadPart);
                    }
                } else {
                    DownloadPart downloadPart = new DownloadPart(
                            Utils.getFilePartName(filename, downloadFile.getRandomString(), 0), 0L,
                            downloadFile.getFileSize());
                    downloadParts.add(downloadPart);
                }

                downloadFile.setDownloadParts(downloadParts);

                ProgressMonitor.getInstance().start();
                Downloader.getInstance().startDownload();
                File result = Utils.mergeFiles(config.getDownloadsLocation());

                System.out.println();

                System.out.format("Download complete! File location: %s\n", result.getAbsolutePath());
                Timer timer = Timer.getInstance();
                Long downloadDuration = timer.getDownloadCompletedTime() - timer.getStartTime();
                System.out.format("Download time: %s\n", Utils.convertDurationInMilisToString(downloadDuration));
                System.out.format("Average download speed: %s\n",
                        Utils.humanReadableSpeed(downloadFile.getFileSize(), downloadDuration, true));
            } catch (MalformedURLException mie) {
                mie.printStackTrace();
                System.err.println("Invalid URL format!");
            } catch (IOException ioe) {
                System.err.println("Error when openning connection!");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Utils.bulkDeleteDownloadPartFiles();
            }
        }
    }
}
