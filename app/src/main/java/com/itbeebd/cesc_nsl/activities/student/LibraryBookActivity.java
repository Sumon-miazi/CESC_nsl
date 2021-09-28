package com.itbeebd.cesc_nsl.activities.student;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.adapters.LibraryBookAdapter;
import com.itbeebd.cesc_nsl.sugarClass.Book;

import java.util.ArrayList;

public class LibraryBookActivity extends AppCompatActivity {

    private RecyclerView libraryRecyclerView;
    private ArrayList<Book> bookArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_book);

        Toolbar mToolBar =  (Toolbar) findViewById(R.id.libraryToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("LIBRARY BOOK");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        libraryRecyclerView = findViewById(R.id.libraryRecyclerViewId);

        if(getIntent().hasExtra("books")){
            bookArrayList = (ArrayList<Book>) getIntent().getSerializableExtra("books");
            if(bookArrayList != null) setLibraryAdapter();
        }
    }

    private void setLibraryAdapter() {
        LibraryBookAdapter libraryBookAdapter = new LibraryBookAdapter(this);
        libraryBookAdapter.setItems(bookArrayList);
        libraryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        libraryRecyclerView.setAdapter(libraryBookAdapter);
    }
}