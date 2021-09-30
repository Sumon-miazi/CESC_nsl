package com.itbeebd.cesc_nsl.utils.dummy;

import java.util.ArrayList;

public class QuizArchive {
    private String subjectName;
    private String examTitle;
    private String examDate;

    private ArrayList<Quiz> quizArrayList;

    public QuizArchive(){

    }

    public QuizArchive(String subjectName, String examTitle, String examDate) {
        this.subjectName = subjectName;
        this.examTitle = examTitle;
        this.examDate = examDate;
    }

    public void setQuizArrayList(ArrayList<Quiz> quizArrayList) {
        this.quizArrayList = quizArrayList;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public String getExamDate() {
        return examDate;
    }

    public ArrayList<Quiz> getQuizArrayList() {
        return quizArrayList;
    }
}
