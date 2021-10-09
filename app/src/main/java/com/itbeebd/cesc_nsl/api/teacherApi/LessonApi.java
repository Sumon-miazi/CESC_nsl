package com.itbeebd.cesc_nsl.api.teacherApi;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.api.BaseService;
import com.itbeebd.cesc_nsl.api.RetrofitRequestBody;
import com.itbeebd.cesc_nsl.dao.TeacherDao;
import com.itbeebd.cesc_nsl.interfaces.BooleanResponse;
import com.itbeebd.cesc_nsl.interfaces.ResponseObj;
import com.itbeebd.cesc_nsl.sugarClass.TeacherSections;
import com.itbeebd.cesc_nsl.utils.CustomProgressDialog;
import com.itbeebd.cesc_nsl.utils.dummy.LessonFile;
import com.itbeebd.cesc_nsl.utils.dummy.TeacherLessonPlan;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LessonApi extends BaseService {
    private final Context context;
    private final RetrofitRequestBody requestBody;
    private final CustomProgressDialog progressDialog;
    private final TeacherDao teacherDao;

    public LessonApi(Context context, String message){
        this.context = context;
        this.teacherDao = new TeacherDao();
        requestBody = new RetrofitRequestBody();
        this.progressDialog = new CustomProgressDialog(context, message);
    }
    
    
    public void insertLessonPlan(String token, JsonObject attendances, ArrayList<String> selectedFiles, BooleanResponse booleanResponse){
        progressDialog.show();

        List<MultipartBody.Part> parts = new ArrayList<>();
        for (int i=0; i < selectedFiles.size(); i++){
            parts.add(getImageFile(selectedFiles.get(i), "teacher_upload_file_details["+i+"]", getMimeType(new File(selectedFiles.get(i)))));
        }

        Call<ResponseBody> attendanceCall = service.serviceWithJsonObjectAndFile(token, ApiUrls.ADD_LESSON_PLAN, attendances, parts);
        attendanceCall.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONObject jsonObject = null;
                if(response.isSuccessful() && response != null){
                    System.out.println(">>>>>>>>>> insertLessonPlan " + response.body());
                    try {
                        jsonObject =  new JSONObject(response.body().string());
                        System.out.println(">>>>>>>>>> insertLessonPlan " + jsonObject);
                        System.out.println(">>>>>>>>>> insertLessonPlan response");

                        booleanResponse.response(jsonObject.optBoolean("isSuccessful"), jsonObject.optString("message"));
                        //   booleanResponse.response(jsonObject.optBoolean("isSuccessful"), jsonObject.optString("message"));

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>> insertLessonPlan catch " + e.fillInStackTrace());

                        booleanResponse.response(false, e.getLocalizedMessage());
                    }
                }
                else {
                    System.out.println(">>>>>>>>>> insertLessonPlan " + response.isSuccessful());
                    System.out.println(">>>>>>>>>> insertLessonPlan " + response);
                    booleanResponse.response(response.isSuccessful(), response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(">>>>>>>>>> insertLessonPlan " + t.getLocalizedMessage());
                booleanResponse.response(false, t.getLocalizedMessage());

            }
        });
    }


    /*
        public void insertLessonPlan(String token, JsonObject attendances, BooleanResponse booleanResponse){
        progressDialog.show();
        Call<ResponseBody> attendanceCall = service.serviceWithJsonObjectAndFile(token, ApiUrls.ADD_LESSON_PLAN, attendances);
        attendanceCall.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONObject jsonObject = null;
                if(response.isSuccessful() && response != null){
                    System.out.println(">>>>>>>>>> insertLessonPlan " + response.body());
                    try {
                        jsonObject =  new JSONObject(response.body().string());
                        System.out.println(">>>>>>>>>> insertLessonPlan " + jsonObject);
                        System.out.println(">>>>>>>>>> insertLessonPlan response");

                        booleanResponse.response(jsonObject.optBoolean("isSuccessful"), jsonObject.optString("message"));
                        //   booleanResponse.response(jsonObject.optBoolean("isSuccessful"), jsonObject.optString("message"));

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>> insertLessonPlan catch " + e.fillInStackTrace());

                        booleanResponse.response(false, e.getLocalizedMessage());
                    }
                }
                else {
                    System.out.println(">>>>>>>>>> insertLessonPlan " + response.isSuccessful());
                    System.out.println(">>>>>>>>>> insertLessonPlan " + response);
                    booleanResponse.response(response.isSuccessful(), response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(">>>>>>>>>> insertLessonPlan " + t.getLocalizedMessage());
                booleanResponse.response(false, t.getLocalizedMessage());

            }
        });
    }
     */


    public void getTeacherLessonPlan(String token, int classId, int sectionId, ResponseObj responseObj){
        progressDialog.show();
        Call<ResponseBody> studentByClassSectionCall = service.getRequestPath(token, ApiUrls.TEACHER_LESSON_PLAN, requestBody.attendanceList(classId, sectionId));
        studentByClassSectionCall.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                JSONArray jsonArray = null;
                if(response.isSuccessful() && response != null){
                    System.out.println(">>>>>>>>>> " + response.body());
                    try {
                        jsonArray =  new JSONArray(response.body().string());
                        System.out.println(">>>>>>>>>> " + jsonArray);

                        if(jsonArray.length() == 0){
                            responseObj.data(null, "No data found");
                            return;
                        }


                        Gson gson = new Gson();
                        ArrayList<TeacherLessonPlan> teacherLessonPlans = new ArrayList<>();

                        for(int i = 0; i < jsonArray.length(); i++){
                            TeacherLessonPlan teacherLessonPlan = gson.fromJson(jsonArray.getJSONObject(i).toString(), TeacherLessonPlan.class);
                            JSONArray teacher_upload_file_sections = jsonArray.getJSONObject(i).getJSONArray("teacher_upload_file_sections");
                            JSONArray teacher_upload_file_details = jsonArray.getJSONObject(i).getJSONArray("teacher_upload_file_details");

                            ArrayList<TeacherSections> teacherSections = new ArrayList<>();
                            for(int j = 0; j < teacher_upload_file_sections.length(); j++ ){
                                TeacherSections sections = new TeacherSections();
                                sections.setName(teacher_upload_file_sections.getJSONObject(j).optString("name"));
                                sections.setSectionId(teacher_upload_file_sections.getJSONObject(j).optInt("section_id"));
                                teacherSections.add(sections);
                            }
                            teacherLessonPlan.setSections(teacherSections);

                            ArrayList<LessonFile> lessonFiles = new ArrayList<>();
                            for(int k = 0; k < teacher_upload_file_details.length(); k++ ){
                                LessonFile file = new LessonFile(
                                        teacher_upload_file_details.getJSONObject(k).optString("file"),
                                        teacher_upload_file_details.getJSONObject(k).optString("original_name")
                                );

                                lessonFiles.add(file);
                            }
                            teacherLessonPlan.setFiles(lessonFiles);

                            teacherLessonPlans.add(teacherLessonPlan);
                        }

                        responseObj.data(teacherLessonPlans, "Successful");
                        //  booleanResponse.response(true, jsonObject.toString());
                        //   booleanResponse.response(jsonObject.optBoolean("isSuccessful"), jsonObject.optString("message"));

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>> catch " + e.fillInStackTrace());

                        responseObj.data(null, e.getLocalizedMessage());
                    }
                }
                else {
                    System.out.println(">>>>>>>>>> " + response.isSuccessful());
                    System.out.println(">>>>>>>>>> " + response);
                    responseObj.data(null, response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(">>>>>>>>>> studentAttendance " + t.getLocalizedMessage());
                responseObj.data(null, t.getLocalizedMessage());

            }
        });
    }


    protected String getMimeType(File file) {
        Uri uri = Uri.fromFile(file);
        String mimeType = null;
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
        }
        return mimeType;
    }
}
