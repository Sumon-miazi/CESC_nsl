package com.itbeebd.cesc_nsl.api;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class BaseService  {

    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
           // .baseUrl("https://cesc.edu.bd/api/")
            .baseUrl("http://192.168.1.251/cescms/api/")
            .build();

    protected final RetrofitService service = retrofit.create(RetrofitService.class);

    protected MultipartBody.Part getImageFile(String imageFilePath, String name){
        if(imageFilePath == null || imageFilePath.isEmpty())
            return null;
        File file = new File(imageFilePath); // initialize file here
        //   System.out.println(">>>>>>>>> file " + file.toString());
        String imageName = imageFilePath.substring(imageFilePath.lastIndexOf("/")+1);
        return MultipartBody.Part.createFormData(name, imageName, okhttp3.RequestBody.create(MediaType.parse("image/*"), file));
    }

}
