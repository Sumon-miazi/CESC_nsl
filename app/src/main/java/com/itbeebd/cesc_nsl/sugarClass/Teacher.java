package com.itbeebd.cesc_nsl.sugarClass;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.io.Serializable;

public class Teacher extends SugarRecord implements Serializable {

    @SerializedName("name")
    private String name;

    @SerializedName("date_of_birth")
    private String date_of_birth;

    @SerializedName("religion")
    private String religion;

    @SerializedName("gender")
    private String gender;

    @SerializedName("present_address")
    private String present_address;

    @SerializedName("permanent_address")
    private String permanent_address;

    @SerializedName("joining_date")
    private String joining_date;

    @SerializedName("designation")
    private String designation;

    @SerializedName("blood_gorup")
    private String blood_gorup;

    @SerializedName("nationalid")
    private String nationalid;

    @SerializedName("status")
    private String status;

    @SerializedName("role_id")
    private String role_id;

    @SerializedName("email")
    private String email;

    @SerializedName("mobile_no")
    private String mobile_no;

    @SerializedName("image")
    private String image;

    @SerializedName("fcm_token")
    private String fcm_token;

    @SerializedName("department")
    private String department;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPresent_address() {
        return present_address;
    }

    public void setPresent_address(String present_address) {
        this.present_address = present_address;
    }

    public String getPermanent_address() {
        return permanent_address;
    }

    public void setPermanent_address(String permanent_address) {
        this.permanent_address = permanent_address;
    }

    public String getJoining_date() {
        return joining_date;
    }

    public void setJoining_date(String joining_date) {
        this.joining_date = joining_date;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getBlood_gorup() {
        return blood_gorup;
    }

    public void setBlood_gorup(String blood_gorup) {
        this.blood_gorup = blood_gorup;
    }

    public String getNationalid() {
        return nationalid;
    }

    public void setNationalid(String nationalid) {
        this.nationalid = nationalid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
