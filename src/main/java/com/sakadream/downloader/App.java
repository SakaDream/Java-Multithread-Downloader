package com.sakadream.downloader;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App {
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws InterruptedException, ApplicationException {
        if (args.length == 0) {
            System.out.println(
                    "Please input URL, ex: java -jar target\\multithread-downloader-1.0-SNAPSHOT-jar-with-dependencies.jar https://example.com/path/file.txt");
        } else {
            DownloadFile downloadFile = null;
            try {
                URL url = new URL(args[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                long fileLength = connection.getContentLengthLong();
                String filename = Utils.getFilename(url, connection);

                LOG.info("File length: {}", fileLength);
                LOG.info("Filename: {}", filename);

                downloadFile = new DownloadFile();
                downloadFile.setFilename(filename);
                downloadFile.setUrl(url);
                downloadFile.setRandomString();

                List<DownloadPart> downloadParts = new ArrayList<>();

                List<List<Long>> listOfStartBytesAndEndBytes = Utils.getListOfStartBytesAndEndBytes(fileLength,
                        Constaints.NUMBER_OF_CONNECTIONS);

                for (int i = 0; i < listOfStartBytesAndEndBytes.size(); i++) {
                    List<Long> startBytesAndEndBytes = listOfStartBytesAndEndBytes.get(i);
                    DownloadPart downloadPart = new DownloadPart(
                            Utils.getFilePartName(filename, downloadFile.getRandomString(), i),
                            startBytesAndEndBytes.get(Constaints.START_BYTES_INDEX),
                            startBytesAndEndBytes.get(Constaints.END_BYTES_INDEX));
                    downloadParts.add(downloadPart);
                }

                downloadFile.setDownloadParts(downloadParts);

                Downloader downloader = new Downloader(downloadFile);
                downloader.startDownload();
                File result = Utils.mergeFiles(downloadFile, Constaints.FULL_DOWNLOAD_FOLDER);

                LOG.info("Downloaded! Download file location: {}", result.getAbsolutePath());
            } catch (MalformedURLException mie) {
                LOG.error("Invalid URL format!");
            } catch (IOException ioe) {
                LOG.error("Error when openning connection!");
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            } finally {
                Utils.bulkDeleteDownloadPartFiles(downloadFile);
            }
        }
    }
}
