package com.bham.restaurantapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bham.restaurantapp.R;
import com.bham.restaurantapp.background.async.EstablishmentsAsyncTask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewAllEstablishmentsActivity extends AppCompatActivity {
    private RecyclerView rView;
    private TextView pageNumberTextView;
    private int pageNumber;
    private int pageSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_establishments);
        this.pageNumber = 1;
        this.pageSize = 10;
        pageNumberTextView = findViewById(R.id.pageNumberTextView);
        rView = findViewById(R.id.testRecyclerView);
        rView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rView.setLayoutManager(lm);
        this.pageNumber = 1;
        this.pageSize = 10;
        new EstablishmentsAsyncTask(this.rView, this.pageNumberTextView).execute(String.valueOf(pageNumber), String.valueOf(pageSize));
    }

    public void onGoToPreviousPage(View view) {
        this.pageNumber -= 1;
        if (this.pageNumber != 0) {
            new EstablishmentsAsyncTask(this.rView, this.pageNumberTextView)
                    .execute(String.valueOf(this.pageNumber), String.valueOf(this.pageSize));
        } else
            this.pageNumber += 1;

    }

    public void onGoToNextPage(View view) {
        this.pageNumber += 1;
        new EstablishmentsAsyncTask(this.rView, this.pageNumberTextView)
                .execute(String.valueOf(this.pageNumber), String.valueOf(this.pageSize));
    }
}
