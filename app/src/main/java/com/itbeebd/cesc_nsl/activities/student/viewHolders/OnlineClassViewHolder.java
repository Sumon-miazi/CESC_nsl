package com.itbeebd.cesc_nsl.activities.student.viewHolders;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.VideoPlayerActivity;
import com.itbeebd.cesc_nsl.activities.genericClasses.BaseViewHolder;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.utils.FileDownloader;
import com.itbeebd.cesc_nsl.utils.dummy.OnlineClass;

public class OnlineClassViewHolder extends BaseViewHolder<OnlineClass, OnRecyclerObjectClickListener<OnlineClass>> {

    private final TextView subjectName;
    private final TextView lessonTitle;
    private final TextView teacherName;
    private final TextView teacherDesignation;
    private final ImageView teacherImage;
    private final ImageView downloadBtn;
    private final ImageView watchBtn;
    private final ImageView bgImage;
    private final FileDownloader fileDownloader;
    private final Context context;

    public OnlineClassViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        this.fileDownloader = new FileDownloader(context);
        subjectName = itemView.findViewById(R.id.textView5);
        lessonTitle = itemView.findViewById(R.id.lessonTitleId);
        teacherName = itemView.findViewById(R.id.teacherNameId);
        teacherDesignation = itemView.findViewById(R.id.designationId);
        teacherImage = itemView.findViewById(R.id.teacherImageViewId);
        downloadBtn = itemView.findViewById(R.id.downloadId);
        watchBtn = itemView.findViewById(R.id.watchId);
        bgImage = itemView.findViewById(R.id.bookImageId);
    }

    @Override
    public void onBind(OnlineClass item, @Nullable OnRecyclerObjectClickListener<OnlineClass> listener) {
        subjectName.setText(item.getSubjectName());
        lessonTitle.setText(item.getLessonTitle());
        teacherName.setText(item.getTeacherName());
        teacherDesignation.setText(item.getTeacherDesignation());
        lessonTitle.setText(item.getLessonTitle());

        setUpImage(teacherImage, item.getTeacherImage());
        setUpImage(bgImage, item.getBgImage());

        System.out.println("file >>>>>>>> " + item.getFile());
        System.out.println("url >>>>>>>> " + item.getUrl());

        if(!item.getFile().equals("null")){
            downloadBtn.setVisibility(View.VISIBLE);
            watchBtn.setVisibility(View.GONE);
        }
        else if(!item.getUrl().equals("null")) {
            downloadBtn.setVisibility(View.GONE);
            watchBtn.setVisibility(View.VISIBLE);
        }
        else {
            downloadBtn.setVisibility(View.GONE);
            watchBtn.setVisibility(View.GONE);
        }

        downloadBtn.setOnClickListener(view -> {
             downloadLessonFile(item);
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse(ApiUrls.BASE_IMAGE_URL + item.getFile()));
//            context.startActivity(intent);
        });

        watchBtn.setOnClickListener(view -> {
            Intent intent = new Intent(context, VideoPlayerActivity.class);
            intent.putExtra("web_url", item.getUrl());
       //     intent.setData(Uri.parse(item.getUrl()));
            context.startActivity(intent);

//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse(item.getUrl()));
//            context.startActivity(intent);
        });
    }

    private void setUpImage(ImageView imageView, String url){
        if (url != null) {
            Glide.with(context)
                    .load(ApiUrls.BASE_IMAGE_URL + url)
//                    .placeholder(R.drawable.default_male)
//                    .error(R.drawable.default_male)
//                    .fallback(R.drawable.default_male)
                    .into(imageView);
        }
    }

    private void downloadLessonFile(OnlineClass onlineClass){

        new FileDownloader(context).downloadFile( 22,ApiUrls.BASE_IMAGE_URL + onlineClass.getFile(),
                onlineClass.getFile().substring(onlineClass.getFile().lastIndexOf("/") + 1),
                "OnlineClass", (isSuccess, message) -> { });

//
//        if(fileDownloader == null) return;
//        fileDownloader.downloadFile(
//                ApiUrls.BASE_IMAGE_URL + onlineClass.getFile(),
//                onlineClass.getFile().substring(onlineClass.getFile().lastIndexOf("/") + 1),
//                "OnlineClass",
//                (isSuccess, message) -> {
//            try {
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//            }
//            catch (Exception ignore){}
//        });
    }
}