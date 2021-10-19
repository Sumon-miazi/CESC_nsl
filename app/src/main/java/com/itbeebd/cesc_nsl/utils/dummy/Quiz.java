package com.itbeebd.cesc_nsl.utils.dummy;

import java.io.Serializable;

public class Quiz implements Serializable {
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int answer;
    private int checkedAnswer;

    public Quiz(String question, String option1, String option2, String option3, String option4, int answer) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
        this.checkedAnswer = 0;
    }

    public String getQuestion() {
        return question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getCheckedAnswer() {
        return checkedAnswer;
    }

    public void setCheckedAnswer(int checkedAnswer) {
        this.checkedAnswer = checkedAnswer;
    }
}
