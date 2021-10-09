package com.itbeebd.cesc_nsl.utils.dummy;

import java.io.Serializable;

public class LessonFile implements Serializable {

    private int file_id;
    private int teacher_upload_file_id;
    private String fileUrl;
    private String fullUrl;
    private String fileName;


    public LessonFile(String fileUrl, String fileName) {
        this.fileUrl = fileUrl;
        this.fileName = fileName;
    }

    public LessonFile(int file_id, int teacher_upload_file_id, String fileUrl, String fullUrl, String fileName) {
        this.file_id = file_id;
        this.teacher_upload_file_id = teacher_upload_file_id;
        this.fileUrl = fileUrl;
        this.fullUrl = fullUrl;
        this.fileName = fileName;
    }

    public int getFile_id() {
        return file_id;
    }

    public int getTeacher_upload_file_id() {
        return teacher_upload_file_id;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getFileName() {
        return fileName;
    }
}
