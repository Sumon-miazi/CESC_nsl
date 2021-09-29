package com.itbeebd.cesc_nsl.utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileDownloader{
    private Fetch fetch;
    private Context context;
    private final NotificationReminder notificationReminder;
    private String fileName;
    private String fileUrl;
    FetchListener fetchListener;

    public FileDownloader(Context context) {
        this.context = context;
        notificationReminder = new NotificationReminder(context);
        FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(context)
                .setDownloadConcurrentLimit(3)
                .build();

        fetch= Fetch.Impl.getInstance(fetchConfiguration);

        fetchListener = new FetchListener() {
            @Override
            public void onWaitingNetwork(@NonNull Download download) {

            }

            @Override
            public void onStarted(@NonNull Download download, @NonNull List<? extends DownloadBlock> list, int i) {

            }

            @Override
            public void onResumed(@NonNull Download download) {

            }

            @Override
            public void onRemoved(@NonNull Download download) {

            }

            @Override
            public void onQueued(@NonNull Download download, boolean b) {

            }

            @Override
            public void onProgress(@NonNull Download download, long l, long l1) {

            }

            @Override
            public void onPaused(@NonNull Download download) {

            }

            @Override
            public void onError(@NonNull Download download, @NonNull Error error, @Nullable Throwable throwable) {
                try {
                    //Remove listener when done.
                    //  if(fetch != null) fetch.removeListener(fetchListener);
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                }
                catch (Exception ignore){}
            }

            @Override
            public void onDownloadBlockUpdated(@NonNull Download download, @NonNull DownloadBlock downloadBlock, int i) {

            }

            @Override
            public void onDeleted(@NonNull Download download) {

            }

            @Override
            public void onCompleted(@NonNull Download download) {
                try {
                    //Remove listener when done.
                    //  if(fetch != null) fetch.removeListener(fetchListener);
                    Toast.makeText(context, "Download complete", Toast.LENGTH_SHORT).show();
                   System.out.println(">>>>>>> file url " + fileUrl);
                    notificationReminder.sendNotification(fileName, "Download Complete ", fileUrl);
                }
                catch (Exception ignore){}
            }

            @Override
            public void onCancelled(@NonNull Download download) {

            }

            @Override
            public void onAdded(@NonNull Download download) {

            }
        };

    }



    public void downloadFile(String url, String fileName, String dirPath, BooleanResponse booleanResponse){

        this.fileName = fileName;
        this.fileUrl = getDownloadPath(fileName, dirPath);

        Request request = new Request(url, fileUrl);
        request.setPriority(Priority.HIGH);
        request.setNetworkType(NetworkType.ALL);
        // request.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG");
        // request.addHeader("clientKey", String.valueOf(Math.random() * 100000 + 1));
        request.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG");

        fetch.enqueue(request, updatedRequest -> {
            //Request was successfully enqueued for download.
            booleanResponse.response(true, "Downloading");
        }, error -> {
            //An error occurred enqueuing the request.
            booleanResponse.response(false, error.toString());
            System.out.println("<><><><> request " + request.getUrl());
            System.out.println("<><><><> request " + error.getHttpResponse());
            System.out.println("<><><><> request " + error.toString());
        });

        fetch.addListener(fetchListener);

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
}
