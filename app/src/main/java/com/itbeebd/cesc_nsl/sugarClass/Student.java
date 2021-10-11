package com.itbeebd.cesc_nsl.sugarClass;

import com.google.gson.annotations.SerializedName;
import com.itbeebd.cesc_nsl.dao.StudentDao;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;

public class Student extends SugarRecord implements Serializable {

    @SerializedName("old_id")
    private String old_id;

    @SerializedName("role_id")
    private String role_id;

    @SerializedName("std_class_id")
    private String std_class_id;

    private String className;

    @SerializedName("section_id")
    private int section_id;

    private String sectionName;

    @Ignore
    private Guardian mother;

    @Ignore
    private Guardian father;

    @SerializedName("guide_teacher_id")
    private String guide_teacher_id;

    @SerializedName("house_id")
    private String house_id;

    @SerializedName("fcm_token")
    private String fcm_token;

    @SerializedName("category")
    private String category;

    @SerializedName("studentid")
    private int studentId;

    @SerializedName("name")
    private String name;

    @SerializedName("roll")
    private int roll;

    @SerializedName("payment_category")
    private String payment_category;

    @SerializedName("gender")
    private String gender;

    @SerializedName("religion")
    private String religion;

    @SerializedName("blood")
    private String blood;

    @SerializedName("academic_year")
    private int academic_year;

    @SerializedName("status")
    private String status;

    @SerializedName("class_monitor")
    private int class_monitor;

    @SerializedName("image")
    private String image;

    @SerializedName("update_password")
    private String update_password;

    @SerializedName("nationality")
    private String nationality;

    @SerializedName("date_of_birth")
    private String date_of_birth;

    @SerializedName("present_address")
    private String present_address;

    @SerializedName("permanent_address")
    private String permanent_address;

    @SerializedName("previous_school")
    private String previous_school;

    @SerializedName("group")
    private String studentGroup;

    @SerializedName("email")
    private String email;

    @SerializedName("pre")
    private String pre;

    @SerializedName("isPromotion")
    private int isPromotion;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("new_roll")
    private String new_roll;

    @SerializedName("new_section_id")
    private String new_section_id;

    @SerializedName("admitted_date")
    private String admitted_date;

    @SerializedName("admitted_class")
    private String admitted_class;

    @SerializedName("promotion_date")
    private String promotion_date;

    @SerializedName("type")
    private int type;

    @SerializedName("alumni")
    private int alumni;

    @SerializedName("blood_group")
    private String blood_group;

    @SerializedName("identification_mark")
    private String identification_mark;

    @SerializedName("helth_problem")
    private String helth_problem;

    @SerializedName("invoice_generate")
    private int invoice_generate;

    @SerializedName("admission_id")
    private String admission_id;


    public Student(){

    }


    public void setMother(Guardian mother) {
        this.mother = mother;
    }

    public void setFather(Guardian father) {
        this.father = father;
    }

    public Guardian getMotherObject() {
        return mother;
    }

    public Guardian getFatherObject() {
        return father;
    }


    public Guardian getMother() {
        if(mother == null) mother = new StudentDao().getGuardian(this, "Mother");
        return mother;
    }

    public Guardian getFather() {
        if(father == null) father =  new StudentDao().getGuardian(this, "Father");
        return father;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public String getOld_id() {
        return old_id;
    }

    public void setOld_id(String old_id) {
        this.old_id = old_id;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getStd_class_id() {
        return std_class_id;
    }

    public void setStd_class_id(String std_class_id) {
        this.std_class_id = std_class_id;
    }

    public int getSection_id() {
        return section_id;
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }

    public String getGuide_teacher_id() {
        return guide_teacher_id;
    }

    public void setGuide_teacher_id(String guide_teacher_id) {
        this.guide_teacher_id = guide_teacher_id;
    }

    public String getHouse_id() {
        return house_id;
    }

    public void setHouse_id(String house_id) {
        this.house_id = house_id;
    }

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public String getPayment_category() {
        return payment_category;
    }

    public void setPayment_category(String payment_category) {
        this.payment_category = payment_category;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public int getAcademic_year() {
        return academic_year;
    }

    public void setAcademic_year(int academic_year) {
        this.academic_year = academic_year;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getClass_monitor() {
        return class_monitor;
    }

    public void setClass_monitor(int class_monitor) {
        this.class_monitor = class_monitor;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUpdate_password() {
        return update_password;
    }

    public void setUpdate_password(String update_password) {
        this.update_password = update_password;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
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

    public String getPrevious_school() {
        return previous_school;
    }

    public void setPrevious_school(String previous_school) {
        this.previous_school = previous_school;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPre() {
        return pre;
    }

    public void setPre(String pre) {
        this.pre = pre;
    }

    public int getIsPromotion() {
        return isPromotion;
    }

    public void setIsPromotion(int isPromotion) {
        this.isPromotion = isPromotion;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNew_roll() {
        return new_roll;
    }

    public void setNew_roll(String new_roll) {
        this.new_roll = new_roll;
    }

    public String getNew_section_id() {
        return new_section_id;
    }

    public void setNew_section_id(String new_section_id) {
        this.new_section_id = new_section_id;
    }

    public String getAdmitted_date() {
        return admitted_date;
    }

    public void setAdmitted_date(String admitted_date) {
        this.admitted_date = admitted_date;
    }

    public String getAdmitted_class() {
        return admitted_class;
    }

    public void setAdmitted_class(String admitted_class) {
        this.admitted_class = admitted_class;
    }

    public String getPromotion_date() {
        return promotion_date;
    }

    public void setPromotion_date(String promotion_date) {
        this.promotion_date = promotion_date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAlumni() {
        return alumni;
    }

    public void setAlumni(int alumni) {
        this.alumni = alumni;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getIdentification_mark() {
        return identification_mark;
    }

    public void setIdentification_mark(String identification_mark) {
        this.identification_mark = identification_mark;
    }

    public String getHelth_problem() {
        return helth_problem;
    }

    public void setHelth_problem(String helth_problem) {
        this.helth_problem = helth_problem;
    }

    public int getInvoice_generate() {
        return invoice_generate;
    }

    public void setInvoice_generate(int invoice_generate) {
        this.invoice_generate = invoice_generate;
    }

    public String getAdmission_id() {
        return admission_id;
    }

    public void setAdmission_id(String admission_id) {
        this.admission_id = admission_id;
    }

}
