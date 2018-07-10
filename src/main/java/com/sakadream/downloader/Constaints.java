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
    public static final String FULL_DOWNLOAD_FOLDER = Paths.get(HOME_FOLDER, DOWNLOADS).toString();

    // Characters
    public static final String UNDERSCORE = "_";
    public static final String PATH_SEPARATOR = File.pathSeparator;

    // Number of threads / parts / connections
    public static final Integer NUMBER_OF_CONNECTIONS = 10;

    // start bytes / end bytes index
    public static final Integer START_BYTES_INDEX = 0;
    public static final Integer END_BYTES_INDEX = 1;

}