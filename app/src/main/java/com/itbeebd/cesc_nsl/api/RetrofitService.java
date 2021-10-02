package com.itbeebd.cesc_nsl.api;

import com.itbeebd.cesc_nsl.utils.dummy.GuardianDummy;
import com.itbeebd.cesc_nsl.utils.dummy.StudentDummy;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RetrofitService {

    @FormUrlEncoded
    @POST("auth/login")
    Call<ResponseBody> studentLogin(
            @Field("studentid") String studentId,
            @Field("password") String password,
            @Field("fcm_token") String fcm_token
    );

    @Multipart
    @POST("auth/profile/update-request")
    Call<ResponseBody> editStudentProfile(
            @Header("Authorization") String authorization,
            @Part("student") StudentDummy student,
            @Part("Father")GuardianDummy father,
            @Part("Mother") GuardianDummy mother,
            @Part MultipartBody.Part student_image,
            @Part MultipartBody.Part mother_image,
            @Part MultipartBody.Part father_image
            );

//
//    @POST("auth/profile/update-request")
//    Call<ResponseBody> editStudentProfile(
//            @Header("Authorization") String authorization
//    );

    @GET("auth/{path}")
    Call<ResponseBody> getRequestPath(
            @Header("Authorization") String authorization,
            @Path("path") String path
    );

    @POST("auth/{path}")
    Call<ResponseBody> postRequestPath(
            @Header("Authorization") String authorization,
            @Path("path") String path
    );

    @POST("auth/result")
    Call<ResponseBody> getResultByExamId(
            @Header("Authorization") String authorization,
            @Body Map<String, Object> body
    );

    @POST("auth/dbblcheckout")
    Call<ResponseBody> getDbblUrl(
            @Header("Authorization") String authorization,
            @Body Map<String, Object> body
    );


    @POST("auth/class-routine")
    Call<ResponseBody> getClassRoutineByDayName(
            @Header("Authorization") String authorization,
            @Body Map<String, Object> body
    );

    @POST("auth/attendance")
    Call<ResponseBody> getAttendanceByMonthName(
            @Header("Authorization") String authorization,
            @Body Map<String, Object> body
    );

}
