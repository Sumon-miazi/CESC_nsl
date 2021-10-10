package com.itbeebd.cesc_nsl.utils.dummy;

import com.google.gson.annotations.SerializedName;

public class AbsentFeeObject {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String StudentName;

    @SerializedName("payment_category")
    private String paymentCategory;

    @SerializedName("studentid")
    private int studentId;

    @SerializedName("roll")
    private int roll;

    @SerializedName("total_working_day")
    private int totalDays;

    @SerializedName("total_present")
    private int totalPresent;

    @SerializedName("total_absent")
    private int totalAbsent;

    @SerializedName("weber")
    private int weber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getPaymentCategory() {
        return paymentCategory;
    }

    public void setPaymentCategory(String paymentCategory) {
        this.paymentCategory = paymentCategory;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public int getTotalPresent() {
        return totalPresent;
    }

    public void setTotalPresent(int totalPresent) {
        this.totalPresent = totalPresent;
    }

    public int getTotalAbsent() {
        return totalAbsent;
    }

    public void setTotalAbsent(int totalAbsent) {
        this.totalAbsent = totalAbsent;
    }

    public int getWeber() {
        return weber;
    }

    public void setWeber(int weber) {
        this.weber = weber;
    }
}
