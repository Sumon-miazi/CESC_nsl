package com.itbeebd.cesc_nsl.activities.student.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.abdulhakeem.seemoretextview.SeeMoreTextView;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.adapters.genericClasses.BaseViewHolder;
import com.itbeebd.cesc_nsl.activities.student.adapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.utils.Notification;

public class StudentNotificationViewHolder extends BaseViewHolder<Notification, OnRecyclerObjectClickListener<Notification>> {
    private final TextView notificationTitle;
    private final SeeMoreTextView notificationBody;
    private Context context;

    public StudentNotificationViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        notificationTitle = itemView.findViewById(R.id.notificationTitleId);
        notificationBody = itemView.findViewById(R.id.notificationBodyId);
    }

    @Override
    public void onBind(Notification item, @Nullable OnRecyclerObjectClickListener<Notification> listener) {

        notificationBody.setTextMaxLength(80); //default is 250 charachters
        notificationBody.expandText(false);

        notificationTitle.setText(item.getTitle());
        notificationBody.setContent(item.getBody());
        System.out.println(">>>>>>> " + item.getBody());
       // notificationBody.toggle();
        notificationBody.setSeeMoreTextColor(R.color.blue_primary); //default is #3F51B5
/*
        if(item.getImage() != null){
            Glide.with(context)
                    .load(ApiUrls.BASE_IMAGE_URL + item.getImage())
                    .into(tempImageView);
        }


        tempName.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });
 */
    }
}