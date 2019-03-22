package com.bham.restaurantapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bham.restaurantapp.R;
import com.bham.restaurantapp.background.async.EstablishmentsAsyncTask;
import com.bham.restaurantapp.background.async.PostcodeAsyncTask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EstablishmentListViewActivity extends AppCompatActivity {
    private RecyclerView rView;
    private TextView pageNumberTextView;
    private int pageNumber;
    private int pageSize;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_establishment_list_view);
        setContentView(R.layout.activity_view_all_establishments);

        pageNumberTextView = findViewById(R.id.pageNumberTextView);
        rView = findViewById(R.id.testRecyclerView);
        rView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rView.setLayoutManager(lm);
        this.pageNumber = 1;
        this.pageSize = 10;

        this.preferences = getPreferences(MODE_PRIVATE);
        Intent receiveSearchValueIntent = getIntent();
        String postcode = receiveSearchValueIntent.getStringExtra("searchValue");

        new PostcodeAsyncTask(
                convertedPostcode -> {
                    // Store the converted coordinate to shared preferences
                    SharedPreferences.Editor preferencesEditor =
                            this.preferences.edit();
                    preferencesEditor.putString(
                            "longitude", convertedPostcode.longitude
                    );
                    preferencesEditor.putString(
                            "latitude", convertedPostcode.latitude
                    );
                    preferencesEditor.apply();
                    new EstablishmentsAsyncTask(rView, pageNumberTextView)
                            .execute(
                                    convertedPostcode.longitude,
                                    convertedPostcode.latitude,
                                    String.valueOf(1),
                                    String.valueOf(pageNumber),
                                    String.valueOf(pageSize)
                            );
                }
        ).execute(postcode);
    }

    public void onGoToPreviousPage(View view) {
        this.pageNumber -= 1;
        if (this.pageNumber != 0) {
            getPageResults();
        } else
            this.pageNumber += 1;
    }

    public void onGoToNextPage(View view) {
        this.pageNumber += 1;
        getPageResults();
    }

    private void getPageResults() {
        new EstablishmentsAsyncTask(this.rView, this.pageNumberTextView)
                .execute(
                        preferences.getString("longitude", null),
                        preferences.getString("latitude", null),
                        preferences.getString("maxdistance", String.valueOf(1)),
                        String.valueOf(this.pageNumber),
                        String.valueOf(this.pageSize)
                );
    }
}
