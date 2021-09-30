package com.itbeebd.cesc_nsl.api;

public class ApiUrls {

    public static final String BASE_URL = "http://192.168.1.251";
    public static final String BASE_IMAGE_URL = "http://192.168.1.251/cescms";

    // if you make any changes in base url change also in BaseService class
//    public static final String BASE_URL = "https://cesc.edu.bd";
//    public static final String BASE_IMAGE_URL = "https://cesc.edu.bd";
    public static final String BASE_URL_API = BASE_URL + "/api/";

    public static final String INVOICE_URL =  BASE_IMAGE_URL + "/invoiceDetails/";
    public static final String DUE_HISTORY =  "dueHistory";
    public static final String PAYMENT_HISTORY =  "paymentHistory";
    public static final String ADD_PAYMENT =  "addPayment";
    public static final String DASHBOARD_HEADER_INFO =  "dashboard-header-info";
    public static final String RESULT =  "result";
    public static final String LOGOUT =  "logout";
    public static final String ONLINE_CLASS =  "online-class";
    public static final String QUIZ_ARCHIVE =  "quiz-archive";
}
