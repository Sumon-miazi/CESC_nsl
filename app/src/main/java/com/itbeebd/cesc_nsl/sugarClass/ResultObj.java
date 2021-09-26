package com.itbeebd.cesc_nsl.sugarClass;

import com.orm.SugarRecord;

import java.io.Serializable;

public class ResultObj extends SugarRecord implements Serializable {
    private String subjectName;
    private int fullMark;
    private int subjectiveMark;
    private int objectiveMark;
    private int practicalMark;
    private int totalMark;
    private int ctMark;
    private int cgp;
    private String grade;
    private int highest_mark;

    public ResultObj(String subjectName) {
        this.subjectName = subjectName;
    }

    public ResultObj(String subjectName, int fullMark, int subjectiveMark, int objectiveMark, int practicalMark, int totalMark, int ctMark, int cgp, String grade, int highest_mark) {
        this.subjectName = subjectName;
        this.fullMark = fullMark;
        this.subjectiveMark = subjectiveMark;
        this.objectiveMark = objectiveMark;
        this.practicalMark = practicalMark;
        this.totalMark = totalMark;
        this.ctMark = ctMark;
        this.cgp = cgp;
        this.grade = grade;
        this.highest_mark = highest_mark;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getFullMark() {
        return fullMark;
    }

    public int getSubjectiveMark() {
        return subjectiveMark;
    }

    public int getObjectiveMark() {
        return objectiveMark;
    }

    public int getPracticalMark() {
        return practicalMark;
    }

    public int getTotalMark() {
        return totalMark;
    }

    public int getCtMark() {
        return ctMark;
    }

    public int getCgp() {
        return cgp;
    }

    public String getGrade() {
        return grade == null? "-" : grade;
    }

    public int getHighest_mark() {
        return highest_mark;
    }
}