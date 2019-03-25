package com.bham.restaurantapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bham.restaurantapp.R;
import com.bham.restaurantapp.background.async.EstablishmentsAsyncTask;
import com.bham.restaurantapp.background.async.PostcodeAsyncTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.bham.restaurantapp.Globals.DEFAULT_AUTHORITY_ID;
import static com.bham.restaurantapp.Globals.DEFAULT_BUSINESS_TYPE_ID;
import static com.bham.restaurantapp.Globals.DEFAULT_MAX_DISTANCE_LIMIT;
import static com.bham.restaurantapp.Globals.DEFAULT_MIN_RATING;
import static com.bham.restaurantapp.Globals.DEFAULT_PAGE_NUMBER;
import static com.bham.restaurantapp.Globals.DEFAULT_PAGE_SIZE;
import static com.bham.restaurantapp.Globals.DEFAULT_REGION_ID;

public class EstablishmentListViewActivity extends AppCompatActivity {
    private RecyclerView rView;
    private TextView pageNumberTextView;
    private int pageNumber;
    private int pageSize;
    private int businessType;
    private int region;
    private int authority;
    private String searchValue;
    private float maxDistanceLimit;
    private String longitude;
    private String latitude;
    private String sortOptionKey;
    private int ratingKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_establishments_list);
        if (savedInstanceState != null) {
            this.pageNumber = savedInstanceState.getInt("pageNumber");
            this.pageSize = savedInstanceState.getInt("pageSize");
        } else {
            this.pageNumber = DEFAULT_PAGE_NUMBER;
            this.pageSize = DEFAULT_PAGE_SIZE;
        }

        TextView establishmentsTitleTextView = findViewById(R.id.establishmentsTitleTextView);
        establishmentsTitleTextView.setText(
                getString(R.string.search_results_establishments_title)
        );
        establishmentsTitleTextView.setBackgroundColor(
                ContextCompat.getColor(this, android.R.color.holo_red_light)
        );
        pageNumberTextView = findViewById(R.id.pageNumberTextView);
        rView = findViewById(R.id.testRecyclerView);
        rView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rView.setLayoutManager(lm);
        this.pageNumber = 1;
        this.pageSize = 9;

        searchValue = getIntent().getStringExtra("searchValue");
        maxDistanceLimit = getIntent().getFloatExtra("maxDistanceLimit", DEFAULT_MAX_DISTANCE_LIMIT);
        businessType = getIntent().getIntExtra("businessType", DEFAULT_BUSINESS_TYPE_ID);
        region = getIntent().getIntExtra("region", DEFAULT_REGION_ID);
        authority = getIntent().getIntExtra("authority", DEFAULT_AUTHORITY_ID);
        sortOptionKey = getIntent().getStringExtra("sortOptionKey");
        ratingKey = getIntent().getIntExtra("minRating", DEFAULT_MIN_RATING);
        if (maxDistanceLimit == 3)
            new EstablishmentsAsyncTask(
                    getApplicationContext(),
                    rView,
                    pageNumberTextView
            )
                    .execute(
                            searchValue,
                            String.valueOf(businessType),
                            String.valueOf(region),
                            String.valueOf(authority),
                            String.valueOf(pageNumber),
                            String.valueOf(pageSize),
                            sortOptionKey,
                            String.valueOf(ratingKey)
                    );
        else {
            // Convert postcode into coordinates so that the maxDistanceLimit
            // option works with the API
            new PostcodeAsyncTask(
                    convertedPostcode -> {
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
                                        String.valueOf(pageSize),
                                        sortOptionKey,
                                        String.valueOf(ratingKey)
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
        int totalPages = Integer.valueOf(
                pageNumberTextView.getText().toString().split("/")[1]
        );
        if (this.pageNumber + 1 <= totalPages) {
            this.pageNumber += 1;
            getPageResults();
        }
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
                            String.valueOf(pageSize),
                            sortOptionKey,
                            String.valueOf(ratingKey)
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
                            String.valueOf(pageSize),
                            sortOptionKey,
                            String.valueOf(ratingKey)
                    );
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("pageNumber", pageNumber);
        outState.putInt("pageSize", pageSize);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPageResults();
    }
}
