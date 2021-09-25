package com.itbeebd.cesc_nsl.sugarClass;

import com.orm.SugarRecord;

import java.io.Serializable;

public class ResultObj extends SugarRecord implements Serializable {
    private String subjectName;
    private String fullMark;
    private String subjectiveMark;
    private String objectiveMark;
    private String practicalMark;
    private String totalMark;
    private String ctMark;
    private String cgp;
    private String grade;

    public ResultObj(String subjectName, String fullMark, String subjectiveMark, String practicalMark, String objectiveMark, String totalMark, String ctMark, String cgp, String grade) {
        this.subjectName = subjectName;
        this.fullMark = fullMark;
        this.subjectiveMark = subjectiveMark;
        this.objectiveMark = objectiveMark;
        this.practicalMark = practicalMark;
        this.totalMark = totalMark;
        this.ctMark = ctMark;
        this.cgp = cgp;
        this.grade = grade;
    }

    public String getPracticalMark() {
        return practicalMark;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getFullMark() {
        return fullMark;
    }

    public String getSubjectiveMark() {
        return subjectiveMark;
    }

    public String getObjectiveMark() {
        return objectiveMark;
    }

    public String getTotalMark() {
        return totalMark;
    }

    public String getCtMark() {
        return ctMark;
    }

    public String getCgp() {
        return cgp;
    }

    public String getGrade() {
        return grade;
    }
}