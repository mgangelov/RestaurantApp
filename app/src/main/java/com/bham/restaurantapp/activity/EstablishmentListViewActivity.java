package com.bham.restaurantapp.activity;

import android.content.Intent;
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
    private int totalPages;
//    private SharedPreferences preferences;
    private int businessType;
    private int region;
    private int authority;
    private String searchValue;
    private float maxDistanceLimit;
    private String longitude;
    private String latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Add totalPages counter so that I can stop next page
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_establishments);

        pageNumberTextView = findViewById(R.id.pageNumberTextView);
        rView = findViewById(R.id.testRecyclerView);
        rView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rView.setLayoutManager(lm);
        this.pageNumber = 1;
        this.pageSize = 9;

        Intent receiveSearchValueIntent = getIntent();
        searchValue = receiveSearchValueIntent.getStringExtra("searchValue");
        maxDistanceLimit = receiveSearchValueIntent.getFloatExtra("maxDistanceLimit", 3);
        businessType = receiveSearchValueIntent.getIntExtra("businessType", -1);
        region = receiveSearchValueIntent.getIntExtra("region", 99);
        authority = receiveSearchValueIntent.getIntExtra("authority", 8999);
        if (maxDistanceLimit == 3)
            new EstablishmentsAsyncTask(
                    getApplicationContext(),
                    rView,
                    pageNumberTextView)
                    .execute(
                            searchValue,
                            String.valueOf(businessType),
                            String.valueOf(region),
                            String.valueOf(authority),
                            String.valueOf(pageNumber),
                            String.valueOf(pageSize)
                    );
        else {
            // Convert postcode into coordinates so that the maxDistanceLimit
            // option works with the API
            new PostcodeAsyncTask(
                    convertedPostcode -> {
                        // Store the converted coordinate to shared preferences
//                        SharedPreferences.Editor preferencesEditor =
//                                this.preferences.edit();
//                        preferencesEditor.putString(
//                                "longitude", convertedPostcode.longitude
//                        );
//                        preferencesEditor.putString(
//                                "latitude", convertedPostcode.latitude
//                        );
//                        preferencesEditor.apply();
                        longitude = convertedPostcode.longitude;
                        latitude = convertedPostcode.latitude;
                        new EstablishmentsAsyncTask(
                                getApplicationContext(),
                                rView,
                                pageNumberTextView
                        )
                                .execute(
                                        longitude,
                                        latitude,
                                        String.valueOf(businessType),
                                        String.valueOf(region),
                                        String.valueOf(authority),
                                        String.valueOf(maxDistanceLimit),
                                        String.valueOf(pageNumber),
                                        String.valueOf(pageSize)
                                );
                    }
            ).execute(searchValue);
        }
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
        if (maxDistanceLimit == 3) {
            new EstablishmentsAsyncTask(
                    getApplicationContext(),
                    rView,
                    pageNumberTextView)
                    .execute(
                            searchValue,
                            String.valueOf(businessType),
                            String.valueOf(region),
                            String.valueOf(authority),
                            String.valueOf(pageNumber),
                            String.valueOf(pageSize)
                    );
        } else {
            new EstablishmentsAsyncTask(
                    getApplicationContext(),
                    rView,
                    pageNumberTextView
            )
                    .execute(
                            longitude,
                            latitude,
                            String.valueOf(businessType),
                            String.valueOf(region),
                            String.valueOf(authority),
                            String.valueOf(maxDistanceLimit),
                            String.valueOf(pageNumber),
                            String.valueOf(pageSize)
                    );
        }
    }
}
