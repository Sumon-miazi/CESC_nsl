package com.itbeebd.cesc_nsl.sugarClass;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.io.Serializable;

public class Guardian extends SugarRecord implements Serializable {

    private Student student;

    @SerializedName("old_id")
    private String old_id;

//    @SerializedName("student_id")
//    private int student_id;

    @SerializedName("relation")
    private String relation;

    @SerializedName("name")
    private String name;

    @SerializedName("image")
    private String profileImage;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("organization")
    private String organization;

    @SerializedName("location")
    private String location;

    @SerializedName("blood_group")
    private String blood_group;

    @SerializedName("occupation")
    private String occupation;

    @SerializedName("designation")
    private String designation;

    @SerializedName("email")
    private String email;

    public Guardian() {

    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getOld_id() {
        return old_id;
    }

    public void setOld_id(String old_id) {
        this.old_id = old_id;
    }

//    public int getStudent_id() {
//        return student_id;
//    }

//    public void setStudent_id(int student_id) {
//        this.student_id = student_id;
//    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
