package com.bham.restaurantapp.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bham.restaurantapp.R;
import com.bham.restaurantapp.adapter.EstablishmentAdapter;
import com.bham.restaurantapp.background.FsaAsyncTask;
import com.bham.restaurantapp.background.FsaDataController;
import com.bham.restaurantapp.model.fsa.EstablishmentResult;

import java.io.IOException;
import java.lang.ref.WeakReference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Retrofit;

public class ViewAllEstablishmentsActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private RecyclerView rView;
    private TextView pageNumberTextView;
    private final String BASE_URL = "http://api.ratings.food.gov.uk/";
    private int pageNumber;
    private int pageSize;
    private FsaDataController fsaData;

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
        new FsaAsyncTask(new WeakReference<>(rView), new WeakReference<>(pageNumberTextView))
                .execute(String.valueOf(pageNumber), String.valueOf(pageSize));
    }

    public void onGoToPreviousPage(View view) {
        this.pageNumber -= 1;
        if (this.pageNumber != 0) {
            try {
                this.fsaData.connectAndGetApiData(this.pageNumber, this.pageSize);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            this.pageNumber += 1;

    }

    public void onGoToNextPage(View view) {
        this.pageNumber += 1;
        try {
            this.fsaData.connectAndGetApiData(this.pageNumber, this.pageSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
