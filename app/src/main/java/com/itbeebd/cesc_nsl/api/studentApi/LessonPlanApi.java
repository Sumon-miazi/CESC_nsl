package com.itbeebd.cesc_nsl.api.studentApi;

import android.content.Context;

import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;
import com.itbeebd.cesc_nsl.utils.CustomProgressDialog;

public class LessonPlanApi extends BaseService {

    private final Context context;
    final RetrofitRequestBody requestBody;
    private final CustomProgressDialog progressDialog;

    public LessonPlanApi(Context context) {
        this.context = context;
        requestBody = new RetrofitRequestBody();
        this.progressDialog = new CustomProgressDialog(context, "Loading...");
    }

}
