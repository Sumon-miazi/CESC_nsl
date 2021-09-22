package com.itbeebd.cesc_nsl.utils.dummy;

public class GuardianDummy {

    private int guardian_id;
    private int student_id;
    private String relation;
    private String name;
    private String occupation;
    private String mobile;
    private String location;
    private String blood_group;
    private String designation;
    private String organization;
    private String email;
    private String imageUrl;

    public GuardianDummy(){}

    public GuardianDummy(int student_id, int guardian_id , String relation, String name, String occupation, String mobile, String location, String blood_group, String designation, String organization, String email, String imageUrl) {
        this.student_id = student_id;
        this.guardian_id = guardian_id;
        this.relation = relation;
        this.name = name;
        this.occupation = occupation;
        this.mobile = mobile;
        this.location = location;
        this.blood_group = blood_group;
        this.designation = designation;
        this.organization = organization;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public int getGuardian_id() {
        return guardian_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public String getRelation() {
        return relation;
    }

    public String getName() {
        return name;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddress() {
        return location;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public String getDesignation() {
        return designation;
    }

    public String getOrganization() {
        return organization;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
