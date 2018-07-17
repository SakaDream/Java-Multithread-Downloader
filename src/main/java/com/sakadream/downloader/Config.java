package com.sakadream.downloader;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Objects;

import org.apache.commons.lang3.SystemUtils;

/**
 * Config
 */
public class Config {

    private Integer numberOfConnections;
    private String downloadsLocation;
    private Boolean useSystemProxy;
    private String proxyUsername;
    private String proxyPassword;
    private Boolean enableCertificateValidation;
    private String userAgent;

    private static Config instance = null;

    protected Config() {
    }

    public static Config getInstance() {
        if (Objects.isNull(instance)) {
            instance = new Config();
        }
        return instance;
    }

    /**
     * @return the numberOfConnections
     */
    public Integer getNumberOfConnections() {
        return numberOfConnections;
    }

    /**
     * @param numberOfConnections the numberOfConnections to set
     */
    public void setNumberOfConnections(Integer numberOfConnections) {
        this.numberOfConnections = numberOfConnections;
    }

    /**
     * @return the downloadsLocation
     */
    public String getDownloadsLocation() {
        return downloadsLocation;
    }

    /**
     * @param downloadsLocation the downloadsLocation to set
     */
    public void setDownloadsLocation(String downloadsLocation) {
        this.downloadsLocation = downloadsLocation;
    }

    /**
     * @return the useSystemProxy
     */
    public Boolean getUseSystemProxy() {
        return useSystemProxy;
    }

    /**
     * @param useSystemProxy the useSystemProxy to set
     */
    public void setUseSystemProxy(Boolean useSystemProxy) {
        this.useSystemProxy = useSystemProxy;
    }

    /**
     * @return the proxyUsername
     */
    public String getProxyUsername() {
        return proxyUsername;
    }

    /**
     * @param proxyUsername the proxyUsername to set
     */
    public void setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
    }

    /**
     * @return the proxyPassword
     */
    public String getProxyPassword() {
        return new String(Base64.getDecoder().decode(this.proxyPassword));
    }

    /**
     * @param proxyPassword the proxyPassword to set
     */
    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = Base64.getEncoder().encodeToString(proxyPassword.getBytes(Charset.defaultCharset()));
    }

    /**
     * @return the enableCertificateValidation
     */
    public Boolean getEnableCertificateValidation() {
        return enableCertificateValidation;
    }

    /**
     * @param enableCertificateValidation the enableCertificateValidation to set
     */
    public void setEnableCertificateValidation(Boolean enableCertificateValidation) {
        this.enableCertificateValidation = enableCertificateValidation;
    }

    /**
     * @return the userAgent
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * @param userAgent the userAgent to set
     */
    public void setUserAgent() {
        if (SystemUtils.IS_OS_WINDOWS) {
            this.userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36";
        } else if (SystemUtils.IS_OS_LINUX) {
            this.userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36";
        } else if (SystemUtils.IS_OS_MAC) {
            this.userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36";
        } else if (SystemUtils.IS_OS_UNIX) {
            this.userAgent = "Mozilla/5.0 (X11; U; Unix; en-US) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36";
        } else {
            this.userAgent = "Mozilla/5.0 (Unknown OS) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36";
        }
    }

}