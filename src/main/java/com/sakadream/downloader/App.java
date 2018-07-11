package com.sakadream.downloader;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
            DownloadFile downloadFile = null;
            try {
                URL url = new URL(args[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                long fileLength = connection.getContentLengthLong();
                String filename = Utils.getFilename(url, connection);

                System.out.format("File length: %s\n", fileLength);
                System.out.format("Filename: %s\n", filename);

                downloadFile = new DownloadFile();
                downloadFile.setFilename(filename);
                downloadFile.setUrl(url);
                downloadFile.setRandomString();

                List<DownloadPart> downloadParts = new ArrayList<>();

                List<List<Long>> listOfStartBytesAndEndBytes = Utils.getListOfStartBytesAndEndBytes(fileLength,
                        Constaints.DEFAULT_NUMBER_OF_CONNECTIONS);

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
                File result = Utils.mergeFiles(downloadFile, Constaints.DEFAULT_DOWNLOAD_FOLDER);

                System.out.format("Downloaded! Download file location:: %s\n", result.getAbsolutePath());
            } catch (MalformedURLException mie) {
                System.err.println("Invalid URL format!");
            } catch (IOException ioe) {
                System.err.println("Error when openning connection!");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Utils.bulkDeleteDownloadPartFiles(downloadFile);
            }
        }
    }
}
