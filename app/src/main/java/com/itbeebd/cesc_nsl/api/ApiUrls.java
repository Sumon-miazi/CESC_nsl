package com.itbeebd.cesc_nsl.api;

public class ApiUrls {

    public static final String IP_ADDRESS = "http://192.168.1.251";
    public static final String BASE_IMAGE_URL = IP_ADDRESS + "/cescms";
    public static final String BASE_URL_API = BASE_IMAGE_URL + "/api/";

    // if you make any changes in base url change also in BaseService class
//    public static final String IP_ADDRESS = "https://cesc.edu.bd";
//    public static final String BASE_IMAGE_URL = IP_ADDRESS;
//    public static final String BASE_URL_API = IP_ADDRESS + "/api/";

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
