package com.itbeebd.cesc_nsl.utils;

import java.io.Serializable;

public class LessonPlan implements Serializable {
    private String teacherName;
    private String subjectName;
    private String lessonTile;
    private String lastUpdated;

    public LessonPlan(String teacherName, String subjectName, String lessonTile, String lastUpdated) {
        this.teacherName = teacherName;
        this.subjectName = subjectName;
        this.lessonTile = lessonTile;
        this.lastUpdated = lastUpdated;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getLessonTile() {
        return lessonTile;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }
}
