package com.itbeebd.cesc_nsl.api.studentApi;

import android.content.Context;

import com.google.gson.Gson;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;
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
    private Context context;
    final RetrofitRequestBody requestBody;
    private CustomProgressDialog progressDialog;

    public QuizApi(Context context){
        this.context = context;
        requestBody = new RetrofitRequestBody();
        this.progressDialog = new CustomProgressDialog(context, "Loading...");
    }

    public void getQuizArchive(String token, ResponseObj responseObj){
        progressDialog.show();
        Call<ResponseBody> quizArchiveCall = service.getRequestPath(token, ApiUrls.QUIZ_ARCHIVE);
        quizArchiveCall.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONArray data;
                if(response.isSuccessful() && response != null){

                    try {
                        data =  new JSONArray(response.body().string());

                        System.out.println(">>>>>>>>>> quizArchiveCall " + data);


                        ArrayList<QuizArchive> quizArchiveArrayList = new ArrayList<>();

                        for(int i = 0; i < data.length(); i++){

                            JSONObject quizArchiveJson = data.getJSONObject(i);
                            QuizArchive quizArchive = new QuizArchive(
                                    quizArchiveJson.getJSONObject("subject").optString("name"),
                                    quizArchiveJson.getJSONObject("quiz").optString("name"),
                                    quizArchiveJson.optString("student_exam_start_time")
                            );


                            JSONArray questionJsonArray = quizArchiveJson.getJSONObject("quiz").getJSONArray("question");
                            ArrayList<Quiz> quizArrayList = new ArrayList<>();

                            for (int j = 0; j < questionJsonArray.length(); j++){
                                JSONObject object = questionJsonArray.getJSONObject(i);

                                Quiz quiz = new Quiz(
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


                }
                else {
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

    public void getLiveQuizList(String token, ResponseObj responseObj){
        progressDialog.show();
        Call<ResponseBody> apiCall = service.getRequestPath(token, ApiUrls.LIVE_QUIZ_LIST);
        apiCall.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONArray data;
                if(response.isSuccessful() && response != null){

                    try {
                        Gson gson = new Gson();
                        data =  new JSONArray(response.body().string());

                        System.out.println(">>>>>>>>>> quizArchiveCall " + data);

                        ArrayList<LiveQuiz> liveQuizArrayList = new ArrayList<>();

                        for(int i = 0; i < data.length(); i++){
                            LiveQuiz liveQuiz = gson.fromJson( data.getJSONObject(i).toString(), LiveQuiz.class);
                            liveQuizArrayList.add(liveQuiz);
                        }

                        responseObj.data(liveQuizArrayList, "successful");

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>> classRoutine catch " + e.fillInStackTrace());

                        responseObj.data(null, e.getLocalizedMessage());
                    }


                }
                else {
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
