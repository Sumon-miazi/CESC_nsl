package com.itbeebd.cesc_nsl.utils.dummy;

public class OnlineClass {

    private final String subjectName;
    private final String lessonTitle;
    private final String teacherName;
    private final String teacherDesignation;
    private final String teacherImage;
    private final String bgImage;
    private final String file;
    private final String url;

    public OnlineClass(String subjectName, String lessonTitle, String teacherName, String teacherDesignation, String teacherImage, String bgImage,String file, String url) {
        this.subjectName = subjectName;
        this.lessonTitle = lessonTitle;
        this.teacherName = teacherName;
        this.teacherDesignation = teacherDesignation;
        this.teacherImage = teacherImage;
        this.bgImage = bgImage;
        this.file = file;
        this.url = url;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getTeacherDesignation() {
        return teacherDesignation;
    }

    public String getTeacherImage() {
        return teacherImage;
    }

    public String getBgImage() {
        return bgImage;
    }

    public String getFile() {
        return file;
    }

    public String getUrl() {
        return url;
    }
}
