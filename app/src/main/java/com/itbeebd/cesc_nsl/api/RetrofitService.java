package com.itbeebd.cesc_nsl.api;

import com.google.gson.JsonObject;
import com.itbeebd.cesc_nsl.utils.dummy.GuardianDummy;
import com.itbeebd.cesc_nsl.utils.dummy.StudentDummy;

import java.util.List;
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
    Call<ResponseBody> getRequestPath(
            @Header("Authorization") String authorization,
            @Path("path") String path,
            @Body Map<String, Object> body
    );


    @GET("auth/get-students/{classId}/{sectionId}")
    Call<ResponseBody> getStudentForAttendance(
            @Header("Authorization") String authorization,
            @Path("classId") int classId,
            @Path("sectionId") int sectionId
    );

    @GET("auth/invoiceDetails/{invoice}")
    Call<ResponseBody> getInvoiceDetails(
            @Header("Authorization") String authorization,
            @Path("invoice") String invoice
    );

    @GET("auth/attendance/{id}")
    Call<ResponseBody> getAttendanceByAttendanceId(
            @Header("Authorization") String authorization,
            @Path("id") int attendanceId
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



    @FormUrlEncoded
    @POST("auth/teacher-login")
    Call<ResponseBody> teacherLogin(
            @Field("email") String studentId,
            @Field("password") String password,
            @Field("fcm_token") String fcm_token
    );

  //  @FormUrlEncoded
    @POST("auth/{path}")
    Call<ResponseBody> serviceWithJsonObject(
            @Header("Authorization") String authorization,
            @Path("path") String path,
            @Body JsonObject attendances
    );

//    @Multipart
//    @POST("auth/{path}")
//    Call<ResponseBody> serviceWithJsonObjectAndFile(
//            @Header("Authorization") String authorization,
//            @Path("path") String path,
//           // @Part MultipartBody.Part image,
//            @Part("data") JsonObject attendances,
//            @Part MultipartBody.Part file1,
//            @Part MultipartBody.Part file2,
//            @Part MultipartBody.Part file3,
//            @Part MultipartBody.Part file4,
//            @Part MultipartBody.Part file5
//    );

    @Multipart
    @POST("auth/{path}")
    Call<ResponseBody> serviceWithJsonObjectAndFile(
            @Header("Authorization") String authorization,
            @Path("path") String path,
            @Part("data") JsonObject attendances,
            @Part List<MultipartBody.Part> file1
    );

//    @Multipart
//    @POST("auth/{path}")
//    Call<ResponseBody> serviceWithJsonObjectAndFile(
//            @Header("Authorization") String authorization,
//            @Path("path") String path,
//           // @Part MultipartBody.Part image,
//            @Part("json") JsonObject attendances
//    );

}
