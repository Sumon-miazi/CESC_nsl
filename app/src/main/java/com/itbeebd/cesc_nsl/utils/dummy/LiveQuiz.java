package com.itbeebd.cesc_nsl.utils.dummy;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class LiveQuiz implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("auto_publish")
    private String auto_publish;

    @SerializedName("std_class_id")
    private String std_class_id;

    @SerializedName("section_id")
    private String section_id;

    @SerializedName("subject_id")
    private String subject_id;

    @SerializedName("status")
    private String status;

    @SerializedName("date")
    private String examDate;

    @SerializedName("time")
    private String fromTime;

    @SerializedName("to_time")
    private String toTime;

    @SerializedName("teacher_id")
    private int teacher_id;

    @SerializedName("admin_id")
    private String admin_id;

    @SerializedName("can_view_reslt")
    private String can_view_result;

    @SerializedName("final_submit")
    private String final_submit;

    @SerializedName("can_view_exam_paper")
    private String can_view_exam_paper;



    private String subjectName;

    @SerializedName("name")
    private String examTitle;

    @SerializedName("full_date")
    private String examStartDateTime;

    @SerializedName("exam_end_time")
    private String examEndDateTime;
   // private String examDate;

    private ArrayList<Quiz> quizArrayList;

    public LiveQuiz(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuto_publish() {
        return auto_publish;
    }

    public void setAuto_publish(String auto_publish) {
        this.auto_publish = auto_publish;
    }

    public String getStd_class_id() {
        return std_class_id;
    }

    public void setStd_class_id(String std_class_id) {
        this.std_class_id = std_class_id;
    }

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getCan_view_result() {
        return can_view_result;
    }

    public void setCan_view_result(String can_view_result) {
        this.can_view_result = can_view_result;
    }

    public String getFinal_submit() {
        return final_submit;
    }

    public void setFinal_submit(String final_submit) {
        this.final_submit = final_submit;
    }

    public String getCan_view_exam_paper() {
        return can_view_exam_paper;
    }

    public void setCan_view_exam_paper(String can_view_exam_paper) {
        this.can_view_exam_paper = can_view_exam_paper;
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
