package com.bham.restaurantapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bham.restaurantapp.R;
import com.bham.restaurantapp.background.async.EstablishmentsAsyncTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewAllEstablishmentsActivity extends AppCompatActivity {
    private static final String TAG = "ViewAllEstablishmentsActivity";
    private RecyclerView rView;
    private TextView pageNumberTextView;
    private int pageNumber;
    private int pageSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            this.pageNumber = 1;
            this.pageSize = 10;
        }
        setContentView(R.layout.activity_view_all_establishments);
        pageNumberTextView = findViewById(R.id.pageNumberTextView);
        rView = findViewById(R.id.testRecyclerView);
        rView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rView.setLayoutManager(lm);
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("pageNumber", pageNumber);
        outState.putInt("pageSize", pageSize);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, String.format("Restored values %d %d", savedInstanceState.getInt("pageNumber"), savedInstanceState.getInt("pageSize")));
        this.pageNumber = savedInstanceState.getInt("pageNumber");
        this.pageSize = savedInstanceState.getInt("pageSize");
    }
}
