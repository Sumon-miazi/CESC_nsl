package com.itbeebd.cesc_nsl.utils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TermExam implements Serializable {

    private Map<String, Integer> exams;
    private List<String> examList;

    public TermExam(Map<String, Integer> exams, List<String> examList) {
        this.exams = exams;
        this.examList = examList;
    }

    public Map<String, Integer> getExams() {
        return exams;
    }

    public List<String> getExamList() {
        return examList;
    }

    public int getExamId(String name){
        return exams.get(name);
    }
}