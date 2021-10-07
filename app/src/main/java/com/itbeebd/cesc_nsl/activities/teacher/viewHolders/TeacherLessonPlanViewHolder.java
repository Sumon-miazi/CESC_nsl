package com.itbeebd.cesc_nsl.activities.teacher.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.BaseViewHolder;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;

public class TeacherLessonPlanViewHolder extends BaseViewHolder<String, OnRecyclerObjectClickListener<String>> {

    private final Context context;
    private final TextView fileTypeId;
    private final TextView fileName_id;
    private final ImageView removeFileBtnId;

    public TeacherLessonPlanViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        this.fileTypeId = itemView.findViewById(R.id.fileTypeId);
        this.fileName_id = itemView.findViewById(R.id.fileName_id);
        this.removeFileBtnId = itemView.findViewById(R.id.removeFileBtnId);

    }

    @Override
    public void onBind(String item, @Nullable OnRecyclerObjectClickListener<String> listener) {

        String[] typeAndName = getFileTypeAndFileName(item);

        fileTypeId.setText(typeAndName[0]);
        fileName_id.setText(typeAndName[1]);

        removeFileBtnId.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });
    }

    private String[] getFileTypeAndFileName(String file){
        String type = file.substring(file.lastIndexOf(".") + 1);
        String name = file.substring(file.lastIndexOf("/") + 1);

        return new String[]{type, name};
    }
}
