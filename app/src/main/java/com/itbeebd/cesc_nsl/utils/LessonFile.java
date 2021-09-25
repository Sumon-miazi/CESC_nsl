package com.itbeebd.cesc_nsl.utils;

import java.io.Serializable;

public class LessonFile implements Serializable {

    private String fileUrl;
    private String fileName;

    public LessonFile(String fileUrl, String fileName) {
        this.fileUrl = fileUrl;
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getFileName() {
        return fileName;
    }
}
