package com.itbeebd.cesc_nsl.api.studentApi;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;
import com.itbeebd.cesc_nsl.interfaces.BooleanResponse;
import com.itbeebd.cesc_nsl.interfaces.ResponseObj;
import com.itbeebd.cesc_nsl.utils.CustomProgressDialog;
import com.itbeebd.cesc_nsl.utils.dummy.LiveQuiz;
import com.itbeebd.cesc_nsl.utils.dummy.Quiz;
import com.itbeebd.cesc_nsl.utils.dummy.QuizArchive;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizApi extends BaseService {
    private final Context context;
    final RetrofitRequestBody requestBody;
    private final CustomProgressDialog progressDialog;

    public QuizApi(Context context, String message) {
        this.context = context;
        requestBody = new RetrofitRequestBody();
        this.progressDialog = new CustomProgressDialog(context, message);
    }

    public void getQuizArchive(String authToken, ResponseObj responseObj) {
        progressDialog.show();
        Call<ResponseBody> quizArchiveCall = service.getRequestPath(authToken, ApiUrls.QUIZ_ARCHIVE);
        quizArchiveCall.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONArray data;
                if (response.isSuccessful() && response != null) {

                    try {
                        data = new JSONArray(response.body().string());

                        System.out.println(">>>>>>>>>> quizArchiveCall " + data);


                        ArrayList<QuizArchive> quizArchiveArrayList = new ArrayList<>();

                        for (int i = 0; i < data.length(); i++) {

                            JSONObject quizArchiveJson = data.getJSONObject(i);
                            QuizArchive quizArchive = new QuizArchive(
                                    quizArchiveJson.getJSONObject("subject").optString("name"),
                                    quizArchiveJson.getJSONObject("quiz").optString("name"),
                                    quizArchiveJson.optString("student_exam_start_time")
                            );


                            JSONArray questionJsonArray = quizArchiveJson.getJSONObject("quiz").getJSONArray("question");
                            ArrayList<Quiz> quizArrayList = new ArrayList<>();

                            for (int k = 0; k < questionJsonArray.length(); k++) {
                                JSONObject object = questionJsonArray.getJSONObject(k);

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
                            quizArchive.setQuizArrayList(quizArrayList);
                            quizArchiveArrayList.add(quizArchive);

                        }


                        responseObj.data(quizArchiveArrayList, "successful");

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

    public void getLiveQuizList(String authToken, ResponseObj responseObj) {
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

                        System.out.println(">>>>>>>>>> getLiveQuizList " + jsonObject);

                        ArrayList<LiveQuiz> liveQuizArrayList = new ArrayList<>();

                        for (int i = 0; i < data.length(); i++) {
                            LiveQuiz liveQuiz = gson.fromJson(data.getJSONObject(i).toString(), LiveQuiz.class);
                            liveQuiz.setSubjectName(data.getJSONObject(i).getJSONObject("subject").getString("name"));
                            liveQuizArrayList.add(liveQuiz);
                        }

                        responseObj.data(liveQuizArrayList, "successful");

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

    public void getLiveQuizzes(String authToken, int id, ResponseObj responseObj) {
        progressDialog.show();
        Call<ResponseBody> quizArchiveCall = service.getQuizQuestion(authToken, id);
        quizArchiveCall.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONObject jsonObject;
                if (response.isSuccessful() && response != null) {

                    try {
                        jsonObject = new JSONObject(response.body().string());

                        System.out.println(">>>>>>>>>> quizArchiveCall " + jsonObject);
                        JSONArray question = jsonObject.getJSONArray("question");
                        ArrayList<Quiz> quizArrayList = new ArrayList<>();

                        for (int k = 0; k < question.length(); k++) {
                            JSONObject object = question.getJSONObject(k);

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

    public void submitLiveExam(String authToken, JsonObject jsonObject, BooleanResponse booleanResponse){
        progressDialog.show();
        Call<ResponseBody> studentLogout = service.serviceWithJsonObject(authToken, ApiUrls.LIVE_QUIZ_LIST, jsonObject);
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
                        booleanResponse.response(jsonObject.optBoolean("isSuccessful"), jsonObject.optString("message"));

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
