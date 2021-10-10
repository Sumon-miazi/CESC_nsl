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

    public Map<String, Object> mapBody(String name){
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
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
}
