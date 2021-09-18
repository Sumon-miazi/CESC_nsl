package com.itbeebd.cesc_nsl.api;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitService {

    @FormUrlEncoded
    @POST("auth/login")
    Call<ResponseBody> studentLogin(
            @Field("studentid") String studentId,
            @Field("password") String password,
            @Field("fcm_token") String fcm_token
    );

}
