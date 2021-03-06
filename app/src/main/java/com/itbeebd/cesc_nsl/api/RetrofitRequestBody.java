package com.itbeebd.cesc_nsl.api;

import com.itbeebd.cesc_nsl.utils.DBBL;

import java.util.HashMap;
import java.util.Map;

public class RetrofitRequestBody {

    public RetrofitRequestBody(){

    }

    public Map<String, Object> getDbblUrl(DBBL dbbl){
        Map<String, Object> map = new HashMap<>();
        map.put("amount", dbbl.getAmount());
        map.put("cardtype", dbbl.getCardtype());
        map.put("txnrefnum", dbbl.getTxnrefnum());
        map.put("account_id", dbbl.getAccount_id());
        return map;
    }


    public Map<String, Object> getResult(int examId){
        Map<String, Object> map = new HashMap<>();
        map.put("exam_id", examId);
        return map;
    }

    public Map<String, Object> examId(int id){
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return map;
    }

    public Map<String, Object> mapBody(String name){
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        return map;
    }

    public Map<String, Object> resetPassword(int studentid, String password){
        Map<String, Object> map = new HashMap<>();
        map.put("studentid", studentid);
        map.put("password", password);
        return map;
    }

    public Map<String, Object> attendanceList(int classId, int sectionId) {
        Map<String, Object> map = new HashMap<>();
        map.put("std_class_id", classId);
        map.put("section_id", sectionId);
        return map;
    }


    public Map<String, Object> studentList(String year, int classId, int sectionId, String status, String filter_name, String filter_value) {
        Map<String, Object> map = new HashMap<>();
        map.put("academic_year", year);
        map.put("std_class_id", classId);
        map.put("section_id", sectionId);
        map.put("status", status);
        map.put("field_name", filter_name);
        map.put("value", filter_value);
        return map;
    }

    public Map<String, Object> studentDetails(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("studentid", id);
        return map;
    }

    public Map<String, Object> attendanceSummery(int classId, int sectionId, String fromDate, String toDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("std_class_id", classId);
        map.put("section_id", sectionId);
        map.put("from_date", fromDate);
        map.put("to_date", toDate);
        return map;
    }

    public Map<String, Object> submitLiveQuiz(int examId, int rightAnswer, int wrongAnswer) {
        Map<String, Object> map = new HashMap<>();
        map.put("examId", examId);
        map.put("rightAnswer", rightAnswer);
        map.put("wrongAnswer", wrongAnswer);
        return map;
    }

    public Map<String, Object> sendNotification(String title, String message, int selectedClassId, int selectedSectionId) {
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("message", message);
        map.put("std_class_id", selectedClassId);
        map.put("section_id", selectedSectionId);
        return map;
    }

    public Map<String, Object> getOnlineExamList(int classId, String fromDate, String toDate, String status) {
        Map<String, Object> map = new HashMap<>();
        map.put("std_class_id", classId);
        map.put("from_date", fromDate);
        map.put("to_date", toDate);
        map.put("status", status);
        return map;
    }
}
