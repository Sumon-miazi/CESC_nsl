package com.itbeebd.cesc_nsl.api;

public class ApiUrls {

    // Local Server
    public static final String IP_ADDRESS = "http://192.168.0.109";
    public static final String BASE_IMAGE_URL = IP_ADDRESS + "/cescms";
    public static final String BASE_URL_API = BASE_IMAGE_URL + "/api/";

    // live server
//    public static final String IP_ADDRESS = "https://cesc.edu.bd";
//    public static final String BASE_IMAGE_URL = IP_ADDRESS;
//    public static final String BASE_URL_API = IP_ADDRESS + "/api/";

    public static final String INVOICE_URL =  BASE_IMAGE_URL + "/invoiceDetails/";
    public static final String DUE_HISTORY =  "dueHistory";
    public static final String PAYMENT_HISTORY =  "paymentHistory";
    public static final String ADD_PAYMENT =  "addPayment";
    public static final String DBBL =  "dbblcheckout";
    public static final String DASHBOARD_HEADER_INFO =  "dashboard-header-info";
    public static final String RESULT =  "result";
    public static final String LOGOUT =  "logout";
    public static final String ONLINE_CLASS =  "online-class";
    public static final String QUIZ_ARCHIVE =  "quiz-archive";
    public static final String GUIDED_STUDENT =  "guided-Student";
    public static final String STUDENT_LIST =  "student-list";
    public static final String ATTENDANCE_LIST =  "attendance-list";
    public static final String ABSENT_FEE =  "absentFee";
    public static final String ABSENT_FEE_APPROVED =  "absentFee-create";
    public static final String STUDENT_ATTENDANCE =  "student-attendance";
    public static final String TEACHER_LESSON_PLAN =  "teacher-lesson-plan";
    public static final String ADD_LESSON_PLAN =  "store-update-lesson-plan";
    public static final String TEACHER_ROUTINE =  "teacher-routine";




    // Bank account details
    public static final String TBL_API_URL = "https://ibanking.tblbd.com/checkout/checkout_payment.aspx";
    public static final String MERCHANT ="CESC";
    public static final String SUCCESS_URL = "https://cesc.edu.bd/api/student/payment_success";
}
