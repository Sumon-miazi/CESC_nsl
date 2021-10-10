package com.itbeebd.cesc_nsl.utils.dummy;

import java.io.Serializable;

public class TeacherRoutine implements Serializable {
    private String className;
    private String subjectName;
    private String winterTime;
    private String summerTime;

    public TeacherRoutine(String className, String subjectName, String winterTime, String summerTime) {
        this.className = className;
        this.subjectName = subjectName;
        this.winterTime = winterTime;
        this.summerTime = summerTime;
    }

    public String getClassName() {
        return className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getWinterTime() {
        return winterTime;
    }

    public String getSummerTime() {
        return summerTime;
    }
}
