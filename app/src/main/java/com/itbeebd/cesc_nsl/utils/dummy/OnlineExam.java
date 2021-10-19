package com.itbeebd.cesc_nsl.utils.dummy;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class OnlineExam implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("auto_publish")
    private String autoPublish;

    @SerializedName("admin_id")
    private String adminId;

    @SerializedName("date")
    private String examDate;

    @SerializedName("full_date")
    private String examStartDateTime;

    @SerializedName("exam_end_time")
    private String examEndDateTime;


    @SerializedName("name")
    private String examTitle;

    @SerializedName("pass_mark")
    private int passMark;

    @SerializedName("question_mark")
    private int perQuestionMark;

    @SerializedName("religion")
    private String religion;

    @SerializedName("status")
    private String publishStatus;

    @SerializedName("std_class_id")
    private String stdClassId;

    @SerializedName("teacher_id")
    private int teacherId;

    @SerializedName("total_mark")
    private int totalMark;

    @SerializedName("total_question")
    private int totalQuestion;

    @SerializedName("time")
    private String examStartTime;

    @SerializedName("to_time")
    private String examEndTime;

    private String className;
    private String subjectName;

    private ArrayList<Quiz> quizArrayList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAutoPublish() {
        return autoPublish;
    }

    public void setAutoPublish(String autoPublish) {
        this.autoPublish = autoPublish;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
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

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public int getPassMark() {
        return passMark;
    }

    public void setPassMark(int passMark) {
        this.passMark = passMark;
    }

    public int getPerQuestionMark() {
        return perQuestionMark;
    }

    public void setPerQuestionMark(int perQuestionMark) {
        this.perQuestionMark = perQuestionMark;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getStdClassId() {
        return stdClassId;
    }

    public void setStdClassId(String stdClassId) {
        this.stdClassId = stdClassId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(int totalMark) {
        this.totalMark = totalMark;
    }

    public int getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(int totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public String getExamStartTime() {
        return examStartTime;
    }

    public void setExamStartTime(String examStartTime) {
        this.examStartTime = examStartTime;
    }

    public String getExamEndTime() {
        return examEndTime;
    }

    public void setExamEndTime(String examEndTime) {
        this.examEndTime = examEndTime;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public ArrayList<Quiz> getQuizArrayList() {
        return quizArrayList;
    }

    public void setQuizArrayList(ArrayList<Quiz> quizArrayList) {
        this.quizArrayList = quizArrayList;
    }
}
