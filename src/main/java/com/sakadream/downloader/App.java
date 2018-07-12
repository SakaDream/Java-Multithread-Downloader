package com.sakadream.downloader;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
            try {
                String urlStr = Utils.getUrl(args);
                Config config = Config.getInstance();
                Utils.setConfig(args);
                Utils.checkDownloadsDirectory();

                if (Objects.isNull(urlStr)) {
                    System.err.println("URL not found!");
                    System.exit(1);
                }

                URL url = new URL(urlStr);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                long fileSize = connection.getContentLengthLong();
                String filename = Utils.getFilename(url, connection);

                downloadFile.setFilename(Utils.setFilename(config.getDownloadsLocation(), filename));
                downloadFile.setFileSize(fileSize);
                downloadFile.setUrl(url);
                downloadFile.setRandomString();

                System.out.format("Filename: %s\n", filename);
                System.out.format("File size: %s\n\n", Utils.humanReadableByteCount(downloadFile.getFileSize(), true));

                List<DownloadPart> downloadParts = new ArrayList<>();

                List<List<Long>> listOfStartBytesAndEndBytes = Utils
                        .getListOfStartBytesAndEndBytes(downloadFile.getFileSize(), config.getNumberOfConnections());

                for (int i = 0; i < listOfStartBytesAndEndBytes.size(); i++) {
                    List<Long> startBytesAndEndBytes = listOfStartBytesAndEndBytes.get(i);
                    DownloadPart downloadPart = new DownloadPart(
                            Utils.getFilePartName(filename, downloadFile.getRandomString(), i),
                            startBytesAndEndBytes.get(Constaints.START_BYTES_INDEX),
                            startBytesAndEndBytes.get(Constaints.END_BYTES_INDEX));
                    downloadParts.add(downloadPart);
                }

                downloadFile.setDownloadParts(downloadParts);

                ProgressMonitor.getInstance().start();
                Downloader.getInstance().startDownload();
                File result = Utils.mergeFiles(config.getDownloadsLocation());

                System.out.format("\nDownload complete! File location: %s\n", result.getAbsolutePath());
                Timer timer = Timer.getInstance();
                Long downloadDuration = timer.getDownloadCompletedTime() - timer.getStartTime();
                System.out.format("Download time: %s\n", Utils.convertDurationInMilisToString(downloadDuration));
                System.out.format("Average download speed: %s\n",
                        Utils.humanReadableSpeed(downloadFile.getFileSize(), downloadDuration, true));
            } catch (MalformedURLException mie) {
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
