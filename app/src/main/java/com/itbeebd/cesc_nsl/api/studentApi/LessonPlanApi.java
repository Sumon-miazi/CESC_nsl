package com.itbeebd.cesc_nsl.api.studentApi;

import android.content.Context;

import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;

public class LessonPlanApi extends BaseService {

    private Context context;
    final RetrofitRequestBody requestBody;

    public LessonPlanApi(Context context) {
        this.context = context;
        requestBody = new RetrofitRequestBody();
    }

}
