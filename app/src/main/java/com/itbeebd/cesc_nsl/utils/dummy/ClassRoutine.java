package com.itbeebd.cesc_nsl.utils.dummy;

import java.io.Serializable;

public class ClassRoutine implements Serializable {
    private String subjectName;
    private String teacherName;
    private String winterTime;
    private String summerTime;

    public ClassRoutine() {
    }

    public ClassRoutine(String subjectName, String teacherName, String winterTime, String summerTime) {
        this.subjectName = subjectName;
        this.teacherName = teacherName;
        this.winterTime = winterTime;
        this.summerTime = summerTime;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getWinterTime() {
        return winterTime;
    }

    public String getSummerTime() {
        return summerTime;
    }
}
