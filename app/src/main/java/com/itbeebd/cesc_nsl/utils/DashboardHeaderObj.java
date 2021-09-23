package com.itbeebd.cesc_nsl.utils;

public class DashboardHeaderObj {
    private String totalNotifications;
    private String totalOnlineClass;
    private String totalLessonPlan;
    private String totalQuiz;
    private String totalQuizArchive;

    public DashboardHeaderObj(String totalNotifications, String totalOnlineClass, String totalLessonPlan, String totalQuiz, String totalQuizArchive) {
        this.totalNotifications = totalNotifications;
        this.totalOnlineClass = totalOnlineClass;
        this.totalLessonPlan = totalLessonPlan;
        this.totalQuiz = totalQuiz;
        this.totalQuizArchive = totalQuizArchive;
    }

    public String getTotalNotifications() {
        return totalNotifications;
    }

    public String getTotalOnlineClass() {
        return totalOnlineClass;
    }

    public String getTotalLessonPlan() {
        return totalLessonPlan;
    }

    public String getTotalQuiz() {
        return totalQuiz;
    }

    public String getTotalQuizArchive() {
        return totalQuizArchive;
    }
}
