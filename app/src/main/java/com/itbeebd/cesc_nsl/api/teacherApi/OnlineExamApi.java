package com.itbeebd.cesc_nsl.api.teacherApi;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;
import com.itbeebd.cesc_nsl.interfaces.BooleanResponse;
import com.itbeebd.cesc_nsl.interfaces.ResponseObj;
import com.itbeebd.cesc_nsl.utils.CustomProgressDialog;
import com.itbeebd.cesc_nsl.utils.dummy.OnlineExam;
import com.itbeebd.cesc_nsl.utils.dummy.Quiz;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnlineExamApi extends BaseService {
    private Context context;
    final RetrofitRequestBody requestBody;
    private CustomProgressDialog progressDialog;

    public OnlineExamApi(Context context, String message) {
        this.context = context;
        requestBody = new RetrofitRequestBody();
        this.progressDialog = new CustomProgressDialog(context, message);
    }

    public void addOnlineExam(String token, JsonObject attendances, BooleanResponse booleanResponse){
        progressDialog.show();
        Call<ResponseBody> attendanceCall = service.serviceWithJsonObject(token, ApiUrls.ADD_LIVE_QUIZ ,attendances);
        attendanceCall.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONObject jsonObject = null;
                if(response.isSuccessful() && response != null){
                    System.out.println(">>>>>>>>>> studentAttendance " + response.body());
                    try {
                        jsonObject =  new JSONObject(response.body().string());
                        System.out.println(">>>>>>>>>> studentAttendance " + jsonObject);

                        booleanResponse.response(jsonObject.optBoolean("isSuccessful"), jsonObject.optString("message"));
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

    public void getOnlineExamList(String authToken, ResponseObj responseObj) {
        progressDialog.show();
        Call<ResponseBody> apiCall = service.getRequestPath(authToken, ApiUrls.LIVE_QUIZ_LIST);
        apiCall.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONObject jsonObject;
                if (response.isSuccessful() && response != null) {

                    try {
                        Gson gson = new Gson();
                        jsonObject = new JSONObject(response.body().string());

                        JSONArray data = jsonObject.getJSONArray("data");

                        System.out.println(">>>>>>>>>> quizArchiveCall " + data);

                        ArrayList<OnlineExam> onlineExams = new ArrayList<>();

                        for (int i = 0; i < data.length(); i++) {
                            OnlineExam onlineExam = gson.fromJson(data.getJSONObject(i).toString(), OnlineExam.class);
                            onlineExam.setSubjectName(data.getJSONObject(i).getJSONObject("subject").getString("name"));
                            onlineExams.add(onlineExam);
                        }

                        responseObj.data(onlineExams, "successful");

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>> classRoutine catch " + e.fillInStackTrace());

                        responseObj.data(null, e.getLocalizedMessage());
                    }


                } else {
                    responseObj.data(null, response.message());
                    System.out.println(">>>>>>>>>> " + response.isSuccessful());
                    System.out.println(">>>>>>>>>> " + response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(">>>>>>>>>> " + t.getLocalizedMessage());
                responseObj.data(null, t.getLocalizedMessage());
            }
        });
    }

    public void getOnlineExamQuizzes(String authToken, int id, ResponseObj responseObj) {
        progressDialog.show();
        Call<ResponseBody> quizArchiveCall = service.getRequestPath(authToken, ApiUrls.LIVE_QUIZ_LIST, requestBody.examId(id));
        quizArchiveCall.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONArray data;
                if (response.isSuccessful() && response != null) {

                    try {
                        data = new JSONArray(response.body().string());

                        System.out.println(">>>>>>>>>> quizArchiveCall " + data);
                        ArrayList<Quiz> quizArrayList = new ArrayList<>();

                        for (int j = 0; j < data.length(); j++) {
                            JSONObject object = data.getJSONObject(j);

                            Quiz quiz = new Quiz(
                                    object.optInt("id"),
                                    object.optString("question"),
                                    object.optString("option1"),
                                    object.optString("option2"),
                                    object.optString("option3"),
                                    object.optString("option4"),
                                    object.optInt("answer")
                            );
                            quizArrayList.add(quiz);
                        }

                        responseObj.data(quizArrayList, "successful");

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>> classRoutine catch " + e.fillInStackTrace());

                        responseObj.data(null, e.getLocalizedMessage());
                    }


                } else {
                    responseObj.data(null, response.message());
                    System.out.println(">>>>>>>>>> " + response.isSuccessful());
                    System.out.println(">>>>>>>>>> " + response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(">>>>>>>>>> " + t.getLocalizedMessage());
                responseObj.data(null, t.getLocalizedMessage());
            }
        });
    }

}
