package com.itbeebd.cesc_nsl.utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.interfaces.BooleanResponse;
import com.itbeebd.cesc_nsl.utils.dummy.LessonFile;
import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Error;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2.FetchListener;
import com.tonyodev.fetch2.NetworkType;
import com.tonyodev.fetch2.Priority;
import com.tonyodev.fetch2.Request;
import com.tonyodev.fetch2core.DownloadBlock;
import com.tonyodev.fetch2okhttp.OkHttpDownloader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class FileDownloader{
    private final Fetch fetch;
    private final Context context;
    private final NotificationReminder notificationReminder;
    private String fileName;
    private String fileDownloadPathWithFileName;
    FetchListener fetchListener;
    private int notificationId = 1;
    NotificationManagerCompat notificationManager;
    NotificationCompat.Builder builder;

    public FileDownloader(Context context) {
        this.context = context;
        notificationReminder = new NotificationReminder(context);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(context)
                .setDownloadConcurrentLimit(20)
                .setHttpDownloader(new OkHttpDownloader(okHttpClient))
                .build();

        fetch= Fetch.Impl.getInstance(fetchConfiguration);

        fetchListener = new FetchListener() {
            @Override
            public void onWaitingNetwork(@NonNull Download download) {
              //  Toast.makeText(context, "onWaitingNetwork", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStarted(@NonNull Download download, @NonNull List<? extends DownloadBlock> list, int i) {
             //   Toast.makeText(context, "Downloading", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResumed(@NonNull Download download) {
              //  Toast.makeText(context, "onResumed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRemoved(@NonNull Download download) {
            //    Toast.makeText(context, "onRemoved", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onQueued(@NonNull Download download, boolean b) {
              //  Toast.makeText(context, "onQueued", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(@NonNull Download download, long l, long l1) {
           //     Toast.makeText(context, "onProgress", Toast.LENGTH_SHORT).show();
                if(builder != null){
                    builder.setContentText("Downloading...").setProgress(
                            100,
                            download.getProgress(),
                            false);
                    notificationManager.notify(notificationId, builder.build());
                }
//                if (request.getId() == download.getId()) {
//                    updateDownload(download, l);
//                }
//                int progress = download.getProgress();
            }

            @Override
            public void onPaused(@NonNull Download download) {
              //  Toast.makeText(context, "onPaused", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NonNull Download download, @NonNull Error error, @Nullable Throwable throwable) {
                try {
                    //Remove listener when done.
                    //  if(fetch != null) fetch.removeListener(fetchListener);
                    Toast.makeText(context, "error " + error.toString(), Toast.LENGTH_SHORT).show();
                    if(builder != null){
                        builder.setContentText("Error occurred").setProgress(0,0,false);
                        notificationManager.notify(notificationId, builder.build());
                    }
                }
                catch (Exception ignore){}
            }

            @Override
            public void onDownloadBlockUpdated(@NonNull Download download, @NonNull DownloadBlock downloadBlock, int i) {
            //    Toast.makeText(context, "onDownloadBlockUpdated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleted(@NonNull Download download) {
             //   Toast.makeText(context, "onDeleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCompleted(@NonNull Download download) {
                try {
                    //Remove listener when done.
                    //  if(fetch != null) fetch.removeListener(fetchListener);
                 //   Toast.makeText(context, "Download complete", Toast.LENGTH_SHORT).show();
                    System.out.println(">>>>>>> file url " + fileDownloadPathWithFileName);
                    notificationManager.cancel(notificationId);
                    notificationReminder.sendNotification(notificationId, fileName, "Download Complete ", fileDownloadPathWithFileName);
                }
                catch (Exception ignore){
                    ignore.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull Download download) {
              //  Toast.makeText(context, "onCancelled", Toast.LENGTH_SHORT).show();
                if(builder != null){
                    builder.setContentText("Download cancel").setProgress(0,0,false);
                    notificationManager.notify(notificationId, builder.build());
                }
            }

            @Override
            public void onAdded(@NonNull Download download) {
              //  Toast.makeText(context, "onAdded", Toast.LENGTH_SHORT).show();
            }
        };

        fetch.addListener(fetchListener);
    }



    public void downloadFile(String url, String fileName, String dirPath, BooleanResponse booleanResponse){

        this.fileName = fileName;
        this.fileDownloadPathWithFileName = getDownloadPath(fileName, dirPath);

        Request request = new Request(url, fileDownloadPathWithFileName);
        request.setPriority(Priority.HIGH);
        request.setNetworkType(NetworkType.ALL);
        // request.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG");
        // request.addHeader("clientKey", String.valueOf(Math.random() * 100000 + 1));
        request.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG");

        fetch.enqueue(request, updatedRequest -> {
            //Request was successfully enqueued for download.
         //   new NotificationReminder(context).sendNotification("hello", "ji", download.getUrl());
            booleanResponse.response(true, "Downloading");
        }, error -> {
            //An error occurred enqueuing the request.
            booleanResponse.response(false, error.toString());
            System.out.println("<><><><> request " + request.getUrl());
            System.out.println("<><><><> request " + error.getHttpResponse());
            System.out.println("<><><><> request " + error.toString());
        });

    }

//    public void downloadFile(LessonFile lessonFile, BooleanResponse booleanResponse){
//    //    String url = "http:www.example.com/test.txt";
//    //    String file = "/downloads/test.txt";
//        System.out.println(">>>>  file url " + lessonFile.getFileUrl());
//        System.out.println(">>>>  file name " + lessonFile.getFileName());
//
//        Request request = new Request(lessonFile.getFileUrl(), getDownloadPath(lessonFile.getFileName()));
//        request.setPriority(Priority.HIGH);
//        request.setNetworkType(NetworkType.ALL);
//       // request.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG");
//       // request.addHeader("clientKey", String.valueOf(Math.random() * 100000 + 1));
//        request.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG");
//
//        fetch.enqueue(request, updatedRequest -> {
//            //Request was successfully enqueued for download.
//            booleanResponse.response(true, "Downloading");
//        }, error -> {
//            //An error occurred enqueuing the request.
//            booleanResponse.response(false, error.toString());
//            System.out.println("<><><><> request " + request.getUrl());
//            System.out.println("<><><><> request " + error.getHttpResponse());
//            System.out.println("<><><><> request " + error.toString());
//        });
//
//
//
//        fetch.addListener(fetchListener);
//
//    }

    public void downloadFiles(ArrayList<LessonFile> files, String dirPath, BooleanResponse booleanResponse){
        for(int i = 0; i < files.size(); i++){
            downloadFile(files.get(i).getFileUrl(), files.get(i).getFileName(), dirPath, booleanResponse::response);
        }
    }

    public void downloadFile(int notificationId, String url, String name, String dirPath, BooleanResponse booleanResponse){
        this.notificationId = notificationId;
        progressNotification();
        downloadFile(url, name, dirPath, booleanResponse::response);
    }

    private String getDownloadPath(String path, String dirPath){

        String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CESC/" + dirPath;

        try {
            File dir = new File(fullPath);
            if (!dir.exists()) {
                dir.mkdirs();
                System.out.println("dir created ");
            }
        }
        catch(Exception ignore){
//            e.printStackTrace();
//            Log.w("creating file error", e.toString());
        }
//        System.out.println(">>>>>> filePath " + fullPath);
//        System.out.println(">>>>>> path " + path);
//        System.out.println(">>>>>> path " + fullPath + "/" + path);
        return fullPath + "/" + path;
    }

    public void releaseDownloader(){
        fetch.close();
        //Remove listener when done.
        fetch.removeListener(fetchListener);
    }

    private void progressNotification(){
        notificationManager = NotificationManagerCompat.from(context);
        builder = new NotificationCompat.Builder(context, context.getResources().getString(R.string.notification_channel_id));
        builder.setContentTitle("File Download")
                .setContentText("Download in progress")
                .setSmallIcon(R.drawable.notification_logo)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

// Issue the initial notification with zero progress
        int PROGRESS_MAX = 100;
        int PROGRESS_CURRENT = 0;
        builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
        notificationManager.notify(notificationId, builder.build());
    }
}
