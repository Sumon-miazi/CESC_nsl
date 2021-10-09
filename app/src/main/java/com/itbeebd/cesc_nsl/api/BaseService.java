package com.itbeebd.cesc_nsl.api;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class BaseService  {

    Retrofit retrofit;
    protected final RetrofitService service;

    public BaseService() {
      //  this.BASE_URL = "https://cesc.edu.bd/api/";
        retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiUrls.BASE_URL_API)
                //   .baseUrl("http://192.168.1.251/cescms/api/")
                .build();
        service = retrofit.create(RetrofitService.class);
    }

    protected MultipartBody.Part getImageFile(String imageFilePath, String name){
        if(imageFilePath == null || imageFilePath.isEmpty())
            return null;
        File file = new File(imageFilePath); // initialize file here
        //   System.out.println(">>>>>>>>> file " + file.toString());
        String imageName = imageFilePath.substring(imageFilePath.lastIndexOf("/")+1);
        return MultipartBody.Part.createFormData(name, imageName, RequestBody.create(MediaType.parse("image/*"), file));

        // RequestBody.create(MediaType.parse("image/*"), file)
    }


    protected MultipartBody.Part getImageFile(String imageFilePath, String name, String mimeType){
        if(imageFilePath == null || imageFilePath.isEmpty())
            return null;
        File file = new File(imageFilePath); // initialize file here
        //   System.out.println(">>>>>>>>> file " + file.toString());
        String imageName = imageFilePath.substring(imageFilePath.lastIndexOf("/")+1);

        RequestBody requestBody = RequestBody.create(MediaType.parse(mimeType), file);
        return MultipartBody.Part.createFormData(name, imageName, requestBody);

        // RequestBody.create(MediaType.parse("image/*"), file)
    }

//
//    protected String getMimeType(File file) {
//        Uri uri = Uri.fromFile(file);
//        String mimeType = null;
//        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
//            ContentResolver cr = context.getContentResolver();
//            mimeType = cr.getType(uri);
//        } else {
//            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
//            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
//        }
//        return mimeType;
//    }
}
