package com.itbeebd.cesc_nsl.api.teacherApi;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.dao.TeacherDao;
import com.itbeebd.cesc_nsl.interfaces.BooleanResponse;
import com.itbeebd.cesc_nsl.sugarClass.Coordinator;
import com.itbeebd.cesc_nsl.sugarClass.Teacher;
import com.itbeebd.cesc_nsl.sugarClass.TeacherClass;
import com.itbeebd.cesc_nsl.sugarClass.TeacherSections;
import com.itbeebd.cesc_nsl.sugarClass.TeacherSubjects;
import com.itbeebd.cesc_nsl.utils.CustomProgressDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginApi extends BaseService {

    private Context context;
    final RetrofitRequestBody requestBody;
    private CustomProgressDialog progressDialog;
    private final TeacherDao teacherDao;

    public LoginApi(Context context, String message){
        this.context = context;
        this.teacherDao = new TeacherDao();
        requestBody = new RetrofitRequestBody();
        this.progressDialog = new CustomProgressDialog(context, message);
    }

    public void teacherLogin(String teacherEmail, String password, String fcm_token, BooleanResponse booleanResponse){
        progressDialog.show();
        Call<ResponseBody> teacherLoginCall = service.teacherLogin(teacherEmail, password, fcm_token);
        teacherLoginCall.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONObject jsonObject = null;
                if(response.isSuccessful() && response != null){
                    System.out.println(">>>>>>>>>> " + response.body());
                    try {
                        Gson gson = new Gson();
                        jsonObject =  new JSONObject(response.body().string());
                        System.out.println(">>>>>>>>>> " + jsonObject);

                        if(!jsonObject.optBoolean("isSuccessful")){
                            booleanResponse.response(false, jsonObject.optString("message"));
                            return;
                        }

                        Teacher teacher = gson.fromJson(jsonObject.getJSONObject("user").toString(), Teacher.class);
                        teacherDao.saveTeacher(teacher);


                        if(! jsonObject.getJSONObject("user").getJSONArray("coordinator").equals("null")){
                     //       JSONArray coordinatorJsonArray = jsonObject.getJSONObject("user").getJSONArray("coordinator");

                            try {
                                ArrayList<Coordinator> coordinatorList = gson.fromJson(
                                        jsonObject.getJSONObject("user").getJSONArray("coordinator").toString(),
                                        new TypeToken<List<Coordinator>>(){}.getType()
                                );

                                teacherDao.saveCoordinator(coordinatorList);
                            }
                            catch (Exception ignore){
                                ignore.printStackTrace();
                            }
                        }

                        if(! jsonObject.getJSONObject("user").getJSONArray("teacher_class").equals("null")){

                            ArrayList<TeacherClass> teacherClasses = gson.fromJson(
                                    jsonObject.getJSONObject("user").getJSONArray("teacher_class").toString(),
                                    new TypeToken<List<TeacherClass>>(){}.getType()
                            );

                            teacherDao.saveTeacherClasses(teacherClasses);
                        }

                        if(! jsonObject.getJSONObject("user").getJSONArray("teacher_subjects").equals("null")){

                            ArrayList<TeacherSubjects> teacherSubjects = gson.fromJson(
                                    jsonObject.getJSONObject("user").getJSONArray("teacher_subjects").toString(),
                                    new TypeToken<List<TeacherSubjects>>(){}.getType()
                            );

                            teacherDao.saveTeacherSubjects(teacherSubjects);
                        }

                        if(! jsonObject.getJSONObject("user").getJSONArray("teacher_sections").equals("null")){

                            ArrayList<TeacherSections> teacherSections = gson.fromJson(
                                    jsonObject.getJSONObject("user").getJSONArray("teacher_sections").toString(),
                                    new TypeToken<List<TeacherSections>>(){}.getType()
                            );

                            teacherDao.saveTeacherSections(teacherSections);
                        }

                        CustomSharedPref.getInstance(context).setAuthToken("Bearer " + jsonObject.optString("token"));

                        CustomSharedPref.getInstance(context).setUserType("teacher");
                        CustomSharedPref.getInstance(context).setUserId(teacherEmail);
                        CustomSharedPref.getInstance(context).setUserPassword(password);

                        booleanResponse.response(jsonObject.optBoolean("isSuccessful"), jsonObject.optString("message"));

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
                System.out.println(">>>>>>>>>> " + t.getLocalizedMessage());
                booleanResponse.response(false, t.getLocalizedMessage());

            }
        });
    }

    public void logout(String token, BooleanResponse booleanResponse){
        progressDialog.show();
        Call<ResponseBody> studentLogout = service.getRequestPath(token, ApiUrls.LOGOUT);
        studentLogout.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONObject jsonObject = null;
                if(response.isSuccessful() && response != null){
                    System.out.println(">>>>>>>>>> " + response.body());
                    try {
                        jsonObject =  new JSONObject(response.body().string());
                        System.out.println(">>>>>>>>>> " + jsonObject);
                        booleanResponse.response(true, jsonObject.optString("message"));

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>> catch " + e.fillInStackTrace());
                        booleanResponse.response(false, e.getLocalizedMessage());
                    }


                }
                else {
                    booleanResponse.response(response.isSuccessful(), response.toString());
                    System.out.println(">>>>>>>>>> " + response.isSuccessful());
                    System.out.println(">>>>>>>>>> " + response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                booleanResponse.response(false, t.getLocalizedMessage());
                System.out.println(">>>>>>>>>> " + t.getLocalizedMessage());
            }
        });
    }

}
