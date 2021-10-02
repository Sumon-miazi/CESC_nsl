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
import android.os.Environment;
import android.webkit.MimeTypeMap;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.FileProvider;

import com.itbeebd.cesc_nsl.R;

import java.io.File;
import java.util.Random;

public class NotificationReminder extends FileProvider  {
    private final String TAG = "NotificationReminder";
    private final Context context;

    public NotificationReminder(Context context) {
        this.context = context;
    }

    public void sendNotification(String title, String messageBody, String fileUrl) {

        System.out.println("file url>>> " + fileUrl);
        File file = new File((Uri.parse("content://" + fileUrl )).getPath());

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, openFile(file), PendingIntent.FLAG_CANCEL_CURRENT);
        String channelId = context.getResources().getString(R.string.notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.notification_logo)
                .setContentTitle(title)
                .setContentText(messageBody)
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

        String type = getMimeType(file);
        Uri path = Uri.fromFile(file);
        String u_path = path.toString();

        File cesc_file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/", "CESC/");
        File newFile = new File(cesc_file, path.toString().substring(u_path.lastIndexOf("CESC/") + 5));
    //    System.out.println(">>>>>>>>>>>>>>> newFile" + newFile);

        Uri contentUri = getUriForFile(context, "com.itbeebd.cesc_nsl.utils.NotificationReminder", newFile);

    //    System.out.println(">>>>>>>>>>>>>>> contentUri" + contentUri);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(contentUri, type);

     //   System.out.println(">>>>>>>>>>>>>>> path type " + path + " " + type);

        return intent;
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
            String channelId = context.getResources().getString(R.string.notification_channel_id);
            String channelName = context.getResources().getString(R.string.notification_channel_name);
            String description = context.getResources().getString(R.string.notification_channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
