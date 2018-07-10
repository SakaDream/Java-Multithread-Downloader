package com.sakadream.downloader;

class DownloadPart {

    private String filename;
    private Long startByte;
    private Long endByte;

    public DownloadPart() {
        super();
    }

    public DownloadPart(String filename, Long startByte, Long endByte) {
        super();
        this.filename = filename;
        this.startByte = startByte;
        this.endByte = endByte;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return the startByte
     */
    public Long getStartByte() {
        return startByte;
    }

    /**
     * @param startByte the startByte to set
     */
    public void setStartByte(Long startByte) {
        this.startByte = startByte;
    }

    /**
     * @return the endByte
     */
    public Long getEndByte() {
        return endByte;
    }

    /**
     * @param endByte the endByte to set
     */
    public void setEndByte(Long endByte) {
        this.endByte = endByte;
    }

}