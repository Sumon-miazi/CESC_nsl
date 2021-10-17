package com.itbeebd.cesc_nsl.api.studentApi;

import android.content.Context;

import com.google.gson.Gson;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.dao.StudentDao;
import com.itbeebd.cesc_nsl.interfaces.BooleanResponse;
import com.itbeebd.cesc_nsl.sugarClass.Guardian;
import com.itbeebd.cesc_nsl.sugarClass.Student;
import com.itbeebd.cesc_nsl.sugarClass.Transport;
import com.itbeebd.cesc_nsl.utils.CustomProgressDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginApi extends BaseService {

    private Context context;
    final RetrofitRequestBody requestBody;
    private CustomProgressDialog progressDialog;
    private final StudentDao studentDao;

    public LoginApi(Context context, String message){
        this.context = context;
        this.studentDao = new StudentDao();
        requestBody = new RetrofitRequestBody();
        this.progressDialog = new CustomProgressDialog(context, message);
    }

    public void studentLogin(String studentId, String password, String fcm_token, BooleanResponse booleanResponse){
        progressDialog.show();
        Call<ResponseBody> studentLogin = service.studentLogin(studentId, password, fcm_token);
        studentLogin.enqueue(new Callback<ResponseBody>(){

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

                        if(!jsonObject.optString("isSuccessful").equals("true")){
                            booleanResponse.response(false, jsonObject.optString("message"));
                            return;
                        }
                        Student student = gson.fromJson(jsonObject.getJSONObject("user").toString(), Student.class);
                        student.setClassName(jsonObject.getJSONObject("user").getJSONObject("stdclass").optString("name"));
                        student.setSectionName(jsonObject.getJSONObject("user").getJSONObject("section").optString("name"));
                       // student.save();
                        studentDao.saveStudent(student);

                        if(! jsonObject.getJSONObject("user").optString("transport_info").equalsIgnoreCase("null")){
                            Transport transport = gson.fromJson(jsonObject.getJSONObject("user").getJSONObject("transport_info").toString(), Transport.class);
                            transport.setStudent(student);
                      //      transport.save();
                            studentDao.saveTransport(transport);
                        }
                   //     Guardian guardian = gson.fromJson(jsonObject.getJSONObject("user").getJSONArray("guardians").toString(), Guardian.class);

                        if(! jsonObject.getJSONObject("user").optString("guardians").equals("null")){
                            JSONArray guardianJsonArray = jsonObject.getJSONObject("user").getJSONArray("guardians");
                            if(guardianJsonArray.length() != 0){
                                for(int i = 0; i < guardianJsonArray.length(); i++){
                                    Guardian guardian = gson.fromJson(guardianJsonArray.get(i).toString(), Guardian.class);
                                    guardian.setStudent(student);
                                //    guardian.save();
                                    studentDao.saveGuardian(guardian, guardian.getRelation());
                                }
                            }
                        }

                        System.out.println("+++++++++++ token " + jsonObject.optString("token"));
                        CustomSharedPref.getInstance(context).setAuthToken("Bearer " + jsonObject.optString("token"));

                        CustomSharedPref.getInstance(context).setUserType("student");
                        CustomSharedPref.getInstance(context).setUserId(studentId);
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

    public void changePassword(String token, String path, int studentid, String newPassword, BooleanResponse booleanResponse){
        progressDialog.show();
        Call<ResponseBody> studentLogout = service.getRequestPath(token, path, requestBody.resetPassword(studentid, newPassword));
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
