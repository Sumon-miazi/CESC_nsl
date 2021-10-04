package com.itbeebd.cesc_nsl.activities.student.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.student.adapters.viewHolders.LibraryBookViewHolder;
import com.itbeebd.cesc_nsl.sugarClass.Book;

public class LibraryBookAdapter extends GenericRecyclerAdapter<Book, OnRecyclerObjectClickListener<Book>, LibraryBookViewHolder> {

    private Context context;

    public LibraryBookAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public LibraryBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LibraryBookViewHolder(inflate(R.layout.single_library_book_view, parent), context);
    }

}