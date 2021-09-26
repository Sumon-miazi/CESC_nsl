package com.itbeebd.cesc_nsl.api;

import java.util.HashMap;
import java.util.Map;

public class RetrofitRequestBody {

    public RetrofitRequestBody(){

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

}
