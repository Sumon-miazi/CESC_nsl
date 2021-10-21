package com.itbeebd.cesc_nsl.utils.dummy;

import java.io.Serializable;
import java.util.ArrayList;

public class LessonPlan implements Serializable {
    private final String teacherName;
    private final String subjectName;
    private final String lessonTitle;
    private final String lastUpdated;

    private ArrayList<LessonFile> lessonFileArrayList;

    public LessonPlan(String teacherName, String subjectName, String lessonTile, String lastUpdated) {
        this.teacherName = teacherName;
        this.subjectName = subjectName;
        this.lessonTitle = lessonTile;
        this.lastUpdated = lastUpdated;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public ArrayList<LessonFile> getLessonFileArrayList() {
        return lessonFileArrayList;
    }

    public void setLessonFileArrayList(ArrayList<LessonFile> lessonFileArrayList) {
        this.lessonFileArrayList = lessonFileArrayList;
    }
}
