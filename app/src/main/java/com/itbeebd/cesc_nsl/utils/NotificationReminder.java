package com.itbeebd.cesc_nsl.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.webkit.MimeTypeMap;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.itbeebd.cesc_nsl.R;

import java.io.File;
import java.util.Random;

public class NotificationReminder  {
    private final String TAG = "NotificationReminder";
    private final Context context;

    public NotificationReminder(Context context) {
        this.context = context;
    }

    public void sendNotification(String title, String messageBody, String fileUrl) {

        if(fileUrl == null){
            fileUrl = "/storage/emulated/0/CESC/LessonPlanFiles/fother.jpg";
        }
        System.out.println("file url>>> " + fileUrl);
        File file = new File((Uri.parse("content://" + fileUrl )).getPath());

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, openFile(file), PendingIntent.FLAG_CANCEL_CURRENT);
        String channelId = "cesc_app";
        String channelName ="CESC_APP";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "String")
                .setSmallIcon(R.drawable.ic_happy)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setSound(defaultSoundUri)
                .setAutoCancel(true);

        createNotificationChannel();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

// notificationId is a unique int for each notification that you must define
        int id= new Random(System.currentTimeMillis()).nextInt(1000);
        notificationManager.notify(id, builder.build());
    }
    private Intent openFile(File file) {
        String fileName = file.getName();

        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);


//        String type = "application/" + extension;

//        if (getMimeType(file) == Attachmets) {
//            type = "image/*";
//        }
//        } else if (getAttachmentType(fileName) == AttachmentType.AUDIO) {
//            type = "audio/*";
//        } else if (getAttachmentType(fileName) == AttachmentType.VIDEO) {
//            type = "video/*";
//        } else if (getAttachmentType(fileName) == AttachmentType.WORD) {
//            type = "application/msword";
//        } else if (getAttachmentType(fileName) == AttachmentType.EXCEL) {
//            type = "application/vnd.ms-excel";
//        } else if (getAttachmentType(fileName) == AttachmentType.POWERPOINT) {
//            type = "application/vnd.ms-powerpoint";
//        } else if (getAttachmentType(fileName) == AttachmentType.TXT) {
//            type = "text/*";
//        }

        String type = getMimeType(file);
      //  System.out.println(">>>>>>>>>>>>>>> mime type " + type);
        Uri path = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent chooser = Intent.createChooser(intent, "Open file with");
        intent.setDataAndType(path, type);

        System.out.println(">>>>>>>>>>>>>>> path type " + path + " " + type);

        return chooser;
    }

    private String getMimeType(File file) {
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

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "CESC";
            String description = "APp";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("String", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
