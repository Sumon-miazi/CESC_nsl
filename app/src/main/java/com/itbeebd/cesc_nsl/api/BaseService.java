package com.itbeebd.cesc_nsl.api;

import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BaseService  {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://cesc.edu.bd/api/")
            .build();

    protected final RetrofitService service = retrofit.create(RetrofitService.class);

}
