package com.itbeebd.cesc_nsl.activities.student.adapters.viewHolders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.adapters.genericClasses.BaseViewHolder;
import com.itbeebd.cesc_nsl.activities.student.adapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.sugarClass.Book;
import com.itbeebd.cesc_nsl.utils.FileDownloader;

public class LibraryBookViewHolder  extends BaseViewHolder<Book, OnRecyclerObjectClickListener<Book>> {

    private final FileDownloader fileDownloader;
    private final TextView bookNameViewId;
    private final TextView bookAuthorNameViewId;
    private final ImageView bookImageId;
    private final ImageView bookDownloadBtnId;

    private Context context;

    public LibraryBookViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        this.fileDownloader = new FileDownloader(context);

        bookNameViewId = itemView.findViewById(R.id.bookNameViewId);
        bookAuthorNameViewId = itemView.findViewById(R.id.bookAuthorNameViewId);
        bookImageId = itemView.findViewById(R.id.bookImageId);
        bookDownloadBtnId = itemView.findViewById(R.id.bookDownloadBtnId);
    }

    @Override
    public void onBind(Book item, @Nullable OnRecyclerObjectClickListener<Book> listener) {
        bookNameViewId.setText(item.getBookName());
        bookAuthorNameViewId.setText(item.getBookAuthorName());
        if( item.getBookImageUrl() != null){
            Glide.with(context)
                    .load(ApiUrls.BASE_IMAGE_URL + item.getBookImageUrl())
                    .error(R.drawable.ic_menu_book)
                    .into(bookImageId);
        }
        
        bookDownloadBtnId.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(item.getBookUrl()));
            context.startActivity(intent);
            // downloadLessonFile(item);
        });
    }

    private void downloadLessonFile(Book book){
        if(fileDownloader == null) return;
        fileDownloader.downloadFile(book.getBookUrl(), book.getBookName(), "Books", (isSuccess, message) -> {
            try {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
            catch (Exception ignore){}
        });
    }
}