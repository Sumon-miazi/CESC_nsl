package com.itbeebd.cesc_nsl.utils.dummy;

import java.io.Serializable;
import java.util.ArrayList;

public class LiveQuiz implements Serializable {
    private int id;
    private String subjectName;
    private String examTitle;
    private String examStartDateTime;
    private String examEndDateTime;
   // private String examDate;

    private ArrayList<Quiz> quizArrayList;

    public LiveQuiz(){

    }

    public LiveQuiz(String subjectName, String examTitle, String examStartDateTime) {
        this.subjectName = subjectName;
        this.examTitle = examTitle;
        this.examStartDateTime = examStartDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public String getExamStartDateTime() {
        return examStartDateTime;
    }

    public void setExamStartDateTime(String examStartDateTime) {
        this.examStartDateTime = examStartDateTime;
    }

    public String getExamEndDateTime() {
        return examEndDateTime;
    }

    public void setExamEndDateTime(String examEndDateTime) {
        this.examEndDateTime = examEndDateTime;
    }

    public ArrayList<Quiz> getQuizArrayList() {
        return quizArrayList;
    }

    public void setQuizArrayList(ArrayList<Quiz> quizArrayList) {
        this.quizArrayList = quizArrayList;
    }
}
