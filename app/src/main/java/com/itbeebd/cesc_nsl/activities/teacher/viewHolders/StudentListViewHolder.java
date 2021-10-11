package com.itbeebd.cesc_nsl.activities.teacher.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.BaseViewHolder;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.sugarClass.Guardian;
import com.itbeebd.cesc_nsl.sugarClass.Student;

public class StudentListViewHolder  extends BaseViewHolder<Student, OnRecyclerObjectClickListener<Student>> {

    private final Context context;
    private final CardView studentInfoCardId;
    private final TextView listNoId;
    private final TextView studentNameViewId;
    private final TextView std_idViewId;
    private final TextView rollViewId;
    private final TextView categoryId;
    private final TextView sl_motherNameId;
    private final TextView sl_motherPhoneId;
    private final ImageView studentProfile;


    public StudentListViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        this.studentInfoCardId = itemView.findViewById(R.id.studentInfoCardId);
        this.listNoId = itemView.findViewById(R.id.listNoId);
        this.studentProfile = itemView.findViewById(R.id.studentProfileViewId);
        this.studentNameViewId = itemView.findViewById(R.id.studentNameViewId);
        this.std_idViewId = itemView.findViewById(R.id.std_idViewId);
        this.rollViewId = itemView.findViewById(R.id.rollViewId);
        this.categoryId = itemView.findViewById(R.id.categoryId);
        this.sl_motherNameId = itemView.findViewById(R.id.sl_motherNameId);
        this.sl_motherPhoneId = itemView.findViewById(R.id.sl_motherPhoneId);
    }

    @Override
    public void onBind(Student item, @Nullable OnRecyclerObjectClickListener<Student> listener) {

        listNoId.setText(String.format("SN: %d", this.getPosition() + 1));
        System.out.println("image >>> " + item.getImage());
        if(item.getImage() != null) setProfileImage(studentProfile, item.getImage());
        studentNameViewId.setText(item.getName());
        std_idViewId.setText(String.format("Student ID : %d", item.getStudentId()));
        rollViewId.setText(String.format("Roll No : %d", item.getRoll()));
        categoryId.setText(String.format("Category : %s", item.getCategory()));
        Guardian mother = item.getMother();
        if(mother != null){
            sl_motherNameId.setText(String.format("Mother : %s", mother.getName()));
            sl_motherPhoneId.setText(String.format("Phone : %s", mother.getMobile()));
        }

        studentInfoCardId.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });
    }

    private void setProfileImage(ImageView imageView, String imageUrl) {
        Glide.with(context)
                .load(ApiUrls.BASE_IMAGE_URL + imageUrl)
                .placeholder(R.drawable.default_male)
                .error(R.drawable.default_male)
                .fallback(R.drawable.default_male)
                .into(imageView);
    }
}
