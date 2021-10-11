package com.itbeebd.cesc_nsl.utils.dummy;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Invoice implements Serializable {
    @SerializedName("amount")
    private int amount;

    @SerializedName("paid_amount")
    private int paid_amount;

    @SerializedName("due_amount")
    private int due_amount;

    @SerializedName("status")
    private String status;

    @SerializedName("collected_month")
    private int collected_month;

    @SerializedName("academic_year")
    private String academic_year;

    @SerializedName("account_head_name")
    private String account_head_name;

    @SerializedName("class_type")
    private String classType;

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(int paid_amount) {
        this.paid_amount = paid_amount;
    }

    public int getDue_amount() {
        return due_amount;
    }

    public void setDue_amount(int due_amount) {
        this.due_amount = due_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCollected_month() {
        return collected_month;
    }

    public void setCollected_month(int collected_month) {
        this.collected_month = collected_month;
    }

    public String getAcademic_year() {
        return academic_year;
    }

    public void setAcademic_year(String academic_year) {
        this.academic_year = academic_year;
    }

    public String getAccount_head_name() {
        return account_head_name;
    }

    public void setAccount_head_name(String account_head_name) {
        this.account_head_name = account_head_name;
    }

    public String month(){
        String[] month = new String[]{
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
        };

        return  month[this.collected_month-1];
    }
}
