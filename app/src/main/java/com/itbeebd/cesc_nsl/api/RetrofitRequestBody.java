package com.itbeebd.cesc_nsl.api;

import java.util.HashMap;
import java.util.Map;

public class RetrofitRequestBody {

    public RetrofitRequestBody(){

    }

    public Map<String, Object> studentLogin(int sectionId){
        Map<String, Object> map = new HashMap<>();
        map.put("section_id", sectionId);
        return map;
    }

}
