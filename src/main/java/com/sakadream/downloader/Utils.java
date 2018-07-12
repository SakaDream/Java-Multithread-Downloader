package com.sakadream.downloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

/**
 * Utils
 */
public class Utils {

    public static void checkDownloadsDirectory() throws ApplicationException {
        Config config = Config.getInstance();
        Path path = Paths.get(config.getDownloadsLocation());
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new ApplicationException("Create default Downloads directory failed!");
            }
        }
    }

    public static String getUrl(String[] args) {
        Pattern pattern = Pattern.compile(Constaints.URL_REGEX);
        for (String arg : args) {
            Matcher matcher = pattern.matcher(arg);
            if (matcher.matches()) {
                return arg;
            }
        }
        return null;
    }

    public static boolean validateArgument(String[] args) throws ApplicationException {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
            case Constaints.CONNECTIONS_ARGUMENT_SHORT:
            case Constaints.CONNECTIONS_ARGUMENT_LONG:
                if (i + 1 >= args.length) {
                    throw new ApplicationException("Invalid number of connections argument's value.");
                }
                try {
                    Integer.valueOf(args[i + 1]);
                } catch (NumberFormatException nfe) {
                    throw new ApplicationException("Invalid number of connections argument's value.");
                }
                break;
            case Constaints.DOWNLOADS_LOCATION_ARGUMENT_SHORT:
            case Constaints.DOWNLOADS_LOCATION_ARGUMENT_LONG:
                if (i + 1 >= args.length) {
                    throw new ApplicationException("Invalid Downloads location argument's value.");
                }
                break;
            }
        }
        return true;
    }

    public static void setConfig(String[] args) throws ApplicationException {
        Config config = Config.getInstance();
        if (args.length == 1) {
            config.setNumberOfConnections(Constaints.DEFAULT_NUMBER_OF_CONNECTIONS);
            config.setDownloadsLocation(Constaints.DEFAULT_DOWNLOAD_FOLDER);
        } else if (validateArgument(args)) {
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                case Constaints.CONNECTIONS_ARGUMENT_SHORT:
                case Constaints.CONNECTIONS_ARGUMENT_LONG:
                    config.setNumberOfConnections(Integer.valueOf(args[i + 1]));
                    break;
                case Constaints.DOWNLOADS_LOCATION_ARGUMENT_SHORT:
                case Constaints.DOWNLOADS_LOCATION_ARGUMENT_LONG:
                    config.setDownloadsLocation(args[i + 1]);
                    break;
                }
            }
            if (Objects.isNull(config.getNumberOfConnections())) {
                config.setNumberOfConnections(Constaints.DEFAULT_NUMBER_OF_CONNECTIONS);
            }
            if (Objects.isNull(config.getDownloadsLocation())) {
                config.setDownloadsLocation(Constaints.DEFAULT_DOWNLOAD_FOLDER);
            }
        }
    }

    private static String getFilename(HttpURLConnection connection) {
        Pattern pattern = Pattern.compile(
                "filename=(?:([\\x21-\\x7E&&[^\\Q()<>[]@,;:\\\"/?=\\E]]++)|\"((?:(?:(?:\r\n)?[\t ])+|[^\r\"\\\\]|\\\\[\\x00-\\x7f])*)\")");
        Matcher matcher = pattern.matcher(connection.getHeaderField("content-disposition"));
        if (matcher.find()) {
            String result = matcher.group();
            return result.substring(result.lastIndexOf('=') + 1);
        } else {
            return null;
        }
    }

    private static String getFilename(URL url) {
        String filePath = url.getFile();
        if (filePath == null) {
            return null;
        } else {
            return filePath.substring(filePath.lastIndexOf('/') + 1, filePath.length());
        }
    }

    public static String getFilename(URL url, HttpURLConnection connection) {
        if (url == null & connection == null)
            return null;
        else if (url != null & connection == null)
            return getFilename(url);
        else if (url == null & connection != null)
            return getFilename(connection);
        else {
            String result = getFilename(url);
            if (result == null)
                return getFilename(connection);
            else
                return result;
        }
    }

    public static String setFilename(String downloadLocation, String originalFilename) {
        String filename = FilenameUtils.getBaseName(originalFilename);
        if (new File(downloadLocation, originalFilename).exists()) {
            int i = 1;
            while (true) {
                String newFilename = new StringBuilder(filename).append('(').append(i).append(").")
                        .append(FilenameUtils.getExtension(originalFilename)).toString();
                if (!new File(downloadLocation, newFilename).exists()) {
                    return newFilename;
                } else {
                    i++;
                }
            }
        } else {
            return originalFilename;
        }
    }

    public static String getFilePartName(URL url, HttpURLConnection connection, String randomString, int partId) {
        String filename = getFilename(url, connection);
        return getFilePartName(filename, randomString, partId);
    }

    public static String getFilePartName(String filename, String randomString, int partId) {
        String name = filename.substring(0, filename.lastIndexOf('.'));
        String extension = filename.substring(filename.lastIndexOf('.') + 1, filename.length());

        return new StringBuilder(name).append('_').append(randomString).append('_').append(partId).append('.')
                .append(extension).toString();
    }

    private static List<Long> splitSize(long fileLength, long totalOfParts) {
        List<Long> listOfSplit = new ArrayList<>();
        long size = fileLength / totalOfParts;
        long remainder = fileLength % totalOfParts;
        int i = 1;
        while (i < totalOfParts) {
            listOfSplit.add(size);
            i++;
        }
        listOfSplit.add(size + remainder);
        return listOfSplit;
    }

    private static List<Long> getStartBytesAndEndBytes(List<Long> listOfSplit, int partNumber) {
        List<Long> startBytesAndEndBytes = new ArrayList<>();
        if (partNumber < listOfSplit.size()) {
            long startBytes = 0;
            long endBytes = 0;
            for (int i = 0; i < listOfSplit.size(); i++) {
                if (i < partNumber) {
                    startBytes += listOfSplit.get(i);
                }
                if (i <= partNumber) {
                    endBytes += listOfSplit.get(i);
                }
            }
            startBytes += 1;
            if (startBytes == 1) {
                startBytes = 0;
            }
            startBytesAndEndBytes.add(startBytes);
            startBytesAndEndBytes.add(endBytes);
        }
        return startBytesAndEndBytes;
    }

    public static List<List<Long>> getListOfStartBytesAndEndBytes(long fileLength, long totalOfParts) {
        List<List<Long>> listOfStartBytesAndEndBytes = new ArrayList<>();
        List<Long> listOfSplit = splitSize(fileLength, totalOfParts);
        for (int i = 0; i < totalOfParts; i++) {
            List<Long> startBytesAndEndBytes = getStartBytesAndEndBytes(listOfSplit, i);
            listOfStartBytesAndEndBytes.add(startBytesAndEndBytes);
        }
        return listOfStartBytesAndEndBytes;
    }

    public static void bulkSetFilename(List<DownloadPart> downloadParts, String filename, String randomString) {
        if (!CollectionUtils.isEmpty(downloadParts)) {
            int length = downloadParts.size();
            for (int i = 0; i < length; i++) {
                String newFilename = getFilePartName(filename, randomString, i + 1);
                DownloadPart part = downloadParts.get(i);
                part.setFilename(newFilename);
                downloadParts.set(i, part);
            }
        }
    }

    public static void bulkCreateDownloadPartFiles() throws ApplicationException {
        bulkCreateDownloadPartFiles(DownloadFile.getInstance().getDownloadParts());
    }

    public static void bulkCreateDownloadPartFiles(List<DownloadPart> downloadParts) throws ApplicationException {
        if (!CollectionUtils.isEmpty(downloadParts)) {
            try {
                for (DownloadPart downloadPart : downloadParts) {
                    File file = new File(Constaints.TMP_FOLDER, downloadPart.getFilename());
                    if (!file.exists()) {
                        file.createNewFile();
                    } else {
                        file.delete();
                        file.createNewFile();
                    }
                }
            } catch (IOException e) {
                throw new ApplicationException("Create files failed!");
            }
        }
    }

    public static void bulkDeleteDownloadPartFiles() {
        bulkDeleteDownloadPartFiles(DownloadFile.getInstance().getDownloadParts());
    }

    public static void bulkDeleteDownloadPartFiles(List<DownloadPart> downloadParts) {
        if (!CollectionUtils.isEmpty(downloadParts)) {
            for (DownloadPart downloadPart : downloadParts) {
                File file = new File(Constaints.TMP_FOLDER, downloadPart.getFilename());
                if (file.exists()) {
                    file.delete();
                }
            }
        }
    }

    public static File mergeFiles(String downloadLocation) throws ApplicationException {
        Timer timer = Timer.getInstance();
        DownloadFile downloadFile = DownloadFile.getInstance();

        List<DownloadPart> downloadParts = downloadFile.getDownloadParts();
        sortDownloadParts(downloadParts);
        File downloadDir = new File(downloadLocation);
        if (!downloadDir.isDirectory()) {
            timer.setMergeFileCompletedTime();
            throw new ApplicationException("Invalid directory");
        } else {
            File result = null;
            try {
                result = new File(downloadDir, downloadFile.getFilename());
                FileOutputStream fos = new FileOutputStream(result);
                for (DownloadPart downloadPart : downloadParts) {
                    File file = new File(Constaints.TMP_FOLDER, downloadPart.getFilename());
                    FileInputStream fis = new FileInputStream(file);
                    IOUtils.copy(fis, fos);
                    fis.close();
                }
                fos.close();

            } catch (IOException e) {
                throw new ApplicationException("Merge files failed!");
            }
            timer.setMergeFileCompletedTime();
            return result;
        }
    }

    private static void sortDownloadParts(List<DownloadPart> input) {
        input.sort(new Comparator<DownloadPart>() {

            @Override
            public int compare(DownloadPart part1, DownloadPart part2) {
                return part1.getFilename().compareTo(part2.getFilename());
            }

        });
    }

    public static String convertDurationInMilisToString(Long durationTime) {
        Long durationTimeInSecs = durationTime / 1000;
        return String.format("%dd %02dh %02dm %02ds", durationTimeInSecs / 86400, (durationTimeInSecs % 86400) / 3600,
                (durationTimeInSecs % 3600) / 60, durationTimeInSecs % 60);
    }

    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit)
            return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public static String humanReadableSpeed(long bytes, long timeInMilis, boolean si) {
        long timeInSecs = timeInMilis / 1000;
        long bytesPerSeconds = bytes / timeInSecs;
        StringBuilder stringBuilder = new StringBuilder(humanReadableByteCount(bytesPerSeconds, si));
        return stringBuilder.append("/s").toString();
    }

    public static Long getCurrentDownloadedSize() {
        return Downloader.getInstance().getDownloaderChildThreads().stream()
                .mapToLong(DownloaderChildThread::getDownloadedFileSize).sum();
    }

    public static boolean isDownloadComplete() {
        return DownloadFile.getInstance().getFileSize().longValue() == Long.valueOf(getCurrentDownloadedSize())
                .longValue();
    }

    public static boolean isReadyToDownload() {
        return DownloadFile.getInstance().getFileSize() > 0;
    }

    public static String printProgressBar() {
        long fileSize = DownloadFile.getInstance().getFileSize();
        try {
            long currentDownloadedSize = getCurrentDownloadedSize();
            double percent = Math.round(((double) currentDownloadedSize / fileSize) * 100);
            long currentProgressNode = Math.round(percent / 100 * Constaints.PROGRESS_BAR_MAX);

            StringBuilder progressBarBuilder = new StringBuilder();

            for (long i = 0; i < currentProgressNode; i++) {
                progressBarBuilder.append('#');
            }

            return String.format("[%-" + Constaints.PROGRESS_BAR_MAX + "s]\t%d%%\t%s/%s \t ",
                    progressBarBuilder.toString(), Math.round(percent),
                    humanReadableByteCount(currentDownloadedSize, true), humanReadableByteCount(fileSize, true));
        } catch (ArithmeticException ae) {
            return String.format("[%-" + Constaints.PROGRESS_BAR_MAX + "s]\t%s%%\t%s/%s \t ", "", "0%",
                    humanReadableByteCount(0, true), humanReadableByteCount(fileSize, true));
        }
    }

}