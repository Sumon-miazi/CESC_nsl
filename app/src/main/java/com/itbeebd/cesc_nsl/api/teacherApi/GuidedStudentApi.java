package com.itbeebd.cesc_nsl.api.teacherApi;

import android.content.Context;

import com.google.gson.Gson;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;
import com.itbeebd.cesc_nsl.api.studentApi.LoginApi;
import com.itbeebd.cesc_nsl.dao.TeacherDao;
import com.itbeebd.cesc_nsl.interfaces.BooleanResponse;
import com.itbeebd.cesc_nsl.sugarClass.Guardian;
import com.itbeebd.cesc_nsl.sugarClass.Student;
import com.itbeebd.cesc_nsl.utils.CustomProgressDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuidedStudentApi extends BaseService {
    private final Context context;
    private final RetrofitRequestBody requestBody;
    private final CustomProgressDialog progressDialog;
    private final TeacherDao teacherDao;
    private final Gson gson;

    public GuidedStudentApi(Context context, String message){
        this.context = context;
        this.gson = new Gson();
        this.teacherDao = new TeacherDao();
        requestBody = new RetrofitRequestBody();
        this.progressDialog = new CustomProgressDialog(context, message);
    }

    public void getGuidedStudent(String token, BooleanResponse booleanResponse){
        progressDialog.show();
        Call<ResponseBody> guidedStudentCall = service.getRequestPath(token, ApiUrls.GUIDED_STUDENT);
        guidedStudentCall.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONArray data = null;
                if(response.isSuccessful() && response != null){
                    System.out.println(">>>>>>>>>> " + response.body());
                    try {
                        data =  new JSONArray(response.body().string());
                        System.out.println(">>>>>>>>>> " + data);

                        LoginApi studentLoginApi = new LoginApi(context, "");

                        boolean flag = true;

                        for(int i = 0; i < data.length(); i++){
                            flag = saveStudentInfo(data.getJSONObject(i).getJSONObject("student"));
                        }

                        booleanResponse.response(flag, "Successful");
                        //   booleanResponse.response(jsonObject.optBoolean("isSuccessful"), jsonObject.optString("message"));

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>> catch " + e.fillInStackTrace());

                        booleanResponse.response(false, e.getLocalizedMessage());
                    }
                }
                else {
                    System.out.println(">>>>>>>>>> " + response.isSuccessful());
                    System.out.println(">>>>>>>>>> " + response);
                    booleanResponse.response(response.isSuccessful(), response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(">>>>>>>>>> studentAttendance " + t.getLocalizedMessage());
                booleanResponse.response(false, t.getLocalizedMessage());

            }
        });
    }

    private boolean saveStudentInfo(JSONObject user){
        try {
            Student student = gson.fromJson(user.toString(), Student.class);
            student.setClassName(user.getJSONObject("std_class").optString("name"));
            student.setSectionName(user.getJSONObject("section").optString("name"));
            student.save();

            if (!user.optString("guardians").equals("null")) {
                JSONArray guardianJsonArray = user.getJSONArray("guardians");
                if (guardianJsonArray.length() != 0) {
                    for (int i = 0; i < guardianJsonArray.length(); i++) {
                        Guardian guardian = gson.fromJson(guardianJsonArray.get(i).toString(), Guardian.class);
                        guardian.setStudent(student);
                        guardian.save();
                    }
                }
            }
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
