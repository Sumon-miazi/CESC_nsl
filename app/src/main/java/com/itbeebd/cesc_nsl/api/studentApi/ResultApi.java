package com.itbeebd.cesc_nsl.api.studentApi;

import android.content.Context;

import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;
import com.itbeebd.cesc_nsl.interfaces.GetResultExam;
import com.itbeebd.cesc_nsl.sugarClass.ResultObj;
import com.itbeebd.cesc_nsl.utils.TermExam;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultApi extends BaseService {
    private Context context;
    final RetrofitRequestBody requestBody;

    public ResultApi(Context context){
        this.context = context;
        requestBody = new RetrofitRequestBody();
    }

    public void getResult(int examId, String token, GetResultExam responseObj){

        Call<ResponseBody> getResult = service.getResultByExamId(token, requestBody.getResult(examId));
        getResult.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if(response.isSuccessful() && response != null){
                   // System.out.println(">>>>>>>>>> due " + response.body());
                    try {
                        jsonObject =  new JSONObject(response.body().string());
                        JSONArray data =  jsonObject.getJSONArray("data");

                        JSONArray termExams =  jsonObject.getJSONArray("exams");

                        Map<String, Integer> exams = new HashMap<>();
                        List<String> examNameList = new ArrayList<>();

                        for(int i = 0; i < termExams.length(); i++){
                            examNameList.add(termExams.getJSONObject(i).optString("name"));
                            exams.put(termExams.getJSONObject(i).optString("name"), termExams.getJSONObject(i).optInt("id"));
                        }

                        TermExam termExam = new TermExam(exams, examNameList);

                        System.out.println("+++++++ exams " + exams);


                        ArrayList<ResultObj> resultObjArrayList = new ArrayList<>();

                        for(int i = 0; i < data.length(); i++){
                            JSONObject resultJsonObj = data.getJSONObject(i);
                            System.out.println("<<<< i " + resultJsonObj);
                            String examMarkAsString = resultJsonObj.getJSONObject("exams").optString("exam_mark");
                            ResultObj resultObj;
                            if(examMarkAsString.endsWith("null")){
                                resultObj = new ResultObj(
                                        resultJsonObj.getJSONObject("exams").optString("name"),
                                        resultJsonObj.getJSONObject("subject").optString("name")
                                );
                            }
                            else {
                                 resultObj = new ResultObj(
                                         resultJsonObj.getJSONObject("exams").optString("name"),
                                        resultJsonObj.getJSONObject("subject").optString("name"),
                                        resultJsonObj.getJSONObject("exams").getJSONObject("exam_mark").optInt("full_mark"),
                                        resultJsonObj.getJSONObject("exams").getJSONObject("exam_mark").optInt("sub_mark"),
                                        resultJsonObj.getJSONObject("exams").getJSONObject("exam_mark").optInt("obj_mark"),
                                        resultJsonObj.getJSONObject("exams").getJSONObject("exam_mark").optInt("prac_mark"),
                                        resultJsonObj.getJSONObject("exams").getJSONObject("exam_mark").optInt("total_obtained_marks"),
                                        resultJsonObj.getJSONObject("exams").getJSONObject("exam_mark").optInt("ct_mark"),
                                        resultJsonObj.getJSONObject("exams").getJSONObject("exam_mark").optInt("grade_point"),
                                        resultJsonObj.getJSONObject("exams").getJSONObject("exam_mark").optString("grade_letter"),
                                        resultJsonObj.getJSONObject("exams").getJSONObject("exam_mark").optInt("highest_marks")
                                );
                            }

                            resultObjArrayList.add(resultObj);
                        }

                        responseObj.data(resultObjArrayList, termExam, "successful");

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>> due catch " + e.fillInStackTrace());

                        responseObj.data(null, null, e.getLocalizedMessage());
                    }


                }
                else {
                    responseObj.data(null,null, response.message());
                    System.out.println(">>>>>>>>>> " + response.isSuccessful());
                    System.out.println(">>>>>>>>>> " + response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println(">>>>>>>>>> " + t.getLocalizedMessage());
                responseObj.data(null,null, t.getLocalizedMessage());
            }
        });
    }
}
