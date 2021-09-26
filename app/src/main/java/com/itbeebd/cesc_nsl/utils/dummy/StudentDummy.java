package com.itbeebd.cesc_nsl.utils.dummy;

public class StudentDummy {

    private String religion;
    private String blood;
    private String date_of_birth;
    private String gender;
    private String present_address;
    private String permanent_address;
    private String email;
    private String mobile;
    private String nationality;
    private String previous_school;
    private String helth_problem;
    private String identification_mark;

    public StudentDummy(){}
    public StudentDummy( String religion, String blood, String date_of_birth, String gender, String present_address, String permanent_address, String email, String mobile, String nationality, String previous_school, String helth_problem, String identification_mark) {
        this.religion = religion;
        this.blood = blood;
        this.date_of_birth = date_of_birth;
        this.gender = gender;
        this.present_address = present_address;
        this.permanent_address = permanent_address;
        this.email = email;
        this.mobile = mobile;
        this.nationality = nationality;
        this.previous_school = previous_school;
        this.helth_problem = helth_problem;
        this.identification_mark = identification_mark;
    }

    public String getReligion() {
        return religion;
    }

    public String getBlood() {
        return blood;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public String getGender() {
        return gender;
    }

    public String getPresent_address() {
        return present_address;
    }

    public String getPermanent_address() {
        return permanent_address;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getNationality() {
        return nationality;
    }

    public String getPrevious_school() {
        return previous_school;
    }

    public String getHelth_problem() {
        return helth_problem;
    }

    public String getIdentification_mark() {
        return identification_mark;
    }
}
