package com.itbeebd.cesc_nsl.activities.student.viewHolders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.BaseViewHolder;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.utils.FileDownloader;
import com.itbeebd.cesc_nsl.utils.dummy.LessonFile;
import com.itbeebd.cesc_nsl.utils.dummy.LessonPlan;
import com.parassidhu.simpledate.SimpleDateKt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LessonPlanViewHolder extends BaseViewHolder<LessonPlan, OnRecyclerObjectClickListener<LessonPlan>> {

    private final FileDownloader fileDownloader;
    private final TextView teacherName;
    private final TextView subjectName;
    private final TextView lessonTitle;
    private final TextView lastUpdated;
    private final ImageView lessonFileDownloaderBtnId;

    private final Context context;

    public LessonPlanViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        this.fileDownloader = new FileDownloader(context);

        teacherName = itemView.findViewById(R.id.lp_teacherNameId);
        subjectName = itemView.findViewById(R.id.lp_subjectNameId);
        lessonTitle = itemView.findViewById(R.id.lp_lessonTitleId);
        lastUpdated = itemView.findViewById(R.id.lp_lessonUpdateDatesId);
        lessonFileDownloaderBtnId = itemView.findViewById(R.id.lessonFileDownloaderBtnId);
    }

    @Override
    public void onBind(LessonPlan item, @Nullable OnRecyclerObjectClickListener<LessonPlan> listener) {
        System.out.println("<<<< " + item.getTeacherName());
        teacherName.setText(item.getTeacherName());
        subjectName.setText(item.getSubjectName());
        lessonTitle.setText(item.getLessonTitle());
        lastUpdated.setText(SimpleDateKt.toDateStandard(getDateFromString(item.getLastUpdated())));

        lessonFileDownloaderBtnId.setOnClickListener(view -> {
            if(item.getLessonFileArrayList() != null) downloadLessonFiles(item.getLessonFileArrayList());
        });
    }

    private Date getDateFromString(String dateString){
        System.out.println("????????? " + dateString);
        Date date;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(dateString);
            System.out.println("????????? " + date);
        } catch (Exception ignore) {
            ignore.printStackTrace();
            date = new Date();
        }
        return date;
    }

    private void downloadLessonFiles(ArrayList<LessonFile> lessonFileArrayList){

        if(lessonFileArrayList.size() == 1){
     //   if(false){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(lessonFileArrayList.get(0).getFileUrl()));
                context.startActivity(intent);
        }
        else {
            if(fileDownloader == null) return;
            fileDownloader.downloadFiles(lessonFileArrayList,"LessonPlanFiles", (isSuccess, message) -> {
                try {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
                catch (Exception ignore){}
            });
        }
    }
}