
package com.sjl.rfm.model;

/**
 * 文件信息类
 *
 * @author Kelly
 * @version 1.0.0
 * @filename FileInfo.java
 * @time 2019/12/19 15:34
 * @copyright(C) 2019 song
 */
public class FileInfo {
    private String name;
    private String url;
    /**
     * 1目录，0文件
     */
    private int isDir;
    /**
     * 文件大小
     */
    private long size;
    /**
     * 文件大小格式化
     */
    private String sizeString;
    /**
     * 文件修改时间
     */
    private long dateModified;
    private String dateModifiedString;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIsDir() {
        return isDir;
    }

    public void setIsDir(int isDir) {
        this.isDir = isDir;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getSizeString() {
        return sizeString;
    }

    public void setSizeString(String sizeString) {
        this.sizeString = sizeString;
    }

    public long getDateModified() {
        return dateModified;
    }

    public void setDateModified(long dateModified) {
        this.dateModified = dateModified;
    }

    public String getDateModifiedString() {
        return dateModifiedString;
    }

    public void setDateModifiedString(String dateModifiedString) {
        this.dateModifiedString = dateModifiedString;
    }
}