package com.sakadream.downloader;

import java.io.File;
import java.nio.file.Paths;

/**
 * Constaints
 */
public class Constaints {

    // Folders
    public static final String TMP_FOLDER = System.getProperty("java.io.tmpdir");
    public static final String HOME_FOLDER = System.getProperty("user.home");
    private static final String DOWNLOADS = "Downloads";
    public static final String DEFAULT_DOWNLOAD_FOLDER = Paths.get(HOME_FOLDER, DOWNLOADS).toString();

    // Characters
    public static final String UNDERSCORE = "_";
    public static final String PATH_SEPARATOR = File.pathSeparator;

    // Default number of threads / parts / connections
    public static final Integer DEFAULT_NUMBER_OF_CONNECTIONS = 8;

    // start bytes / end bytes index
    public static final Integer START_BYTES_INDEX = 0;
    public static final Integer END_BYTES_INDEX = 1;

    // Arguments
    public static final String CONNECTIONS_ARGUMENT_SHORT = "-c";
    public static final String CONNECTIONS_ARGUMENT_LONG = "--connections";
    public static final String DOWNLOADS_LOCATION_ARGUMENT_SHORT = "-l";
    public static final String DOWNLOADS_LOCATION_ARGUMENT_LONG = "--location";

    // Regex
    public static final String CONTENT_DISPOSITION_REGEX = "filename=(?:([\\x21-\\x7E&&[^\\Q()<>[]@,;:\\\"/?=\\E]]++)|\"((?:(?:(?:\r\n)?[\t ])+|[^\r\"\\\\]|\\\\[\\x00-\\x7f])*)\")";
    public static final String URL_REGEX = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    // Buffer size
    public static final Integer BUFFER_SIZE = 1024;

    // Progress bar
    public static final Integer PROGRESS_BAR_MAX = 50;

    // Headers
    public static final String ACCEPT_RANGES_HEADER = "Accept-Ranges";
    public static final String CONTENT_DISPOSITION_HEADER = "Content-Disposition";
    public static final String LOCATION_HEADER = "Location";

}