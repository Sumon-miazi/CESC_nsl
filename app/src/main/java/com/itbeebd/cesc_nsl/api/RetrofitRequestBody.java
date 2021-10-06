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
}
