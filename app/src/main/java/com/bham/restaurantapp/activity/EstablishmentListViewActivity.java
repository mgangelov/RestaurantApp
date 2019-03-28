package com.bham.restaurantapp.activity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bham.restaurantapp.R;
import com.bham.restaurantapp.background.async.EstablishmentsAsyncTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    private static final String TAG = "EstablishmentListViewActivity";
    private RecyclerView rView;
    private TextView pageNumberTextView;
    private int pageNumber;
    private int pageSize;
    private int businessType;
    private int region;
    private int authority;
    private String searchValue;
    private int maxDistanceLimit;
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

        Log.i(TAG, "onCreate: reached before check");
        Log.i(TAG, "onCreate: longitude" + getIntent().getStringExtra("longitude"));
        Log.i(TAG, "onCreate: latitude" + getIntent().getStringExtra("latitude"));
        if (getIntent().getStringExtra("longitude") != null && getIntent().getStringExtra("latitude") != null) {
            Log.i(TAG, "onCreate: loading coords");
            longitude = getIntent().getStringExtra("longitude");
            latitude = getIntent().getStringExtra("latitude");
        } else if (getIntent().getStringExtra("searchValue") != null) {
            searchValue = getIntent().getStringExtra("searchValue");
            Log.i(TAG, "onCreate: setting serachvalue");
        }
        maxDistanceLimit = getIntent().getIntExtra("maxDistanceLimit", DEFAULT_MAX_DISTANCE_LIMIT);
        Log.i(TAG, "onCreate: max disti value: " + maxDistanceLimit);
        businessType = getIntent().getIntExtra("businessType", DEFAULT_BUSINESS_TYPE_ID);
        region = getIntent().getIntExtra("region", DEFAULT_REGION_ID);
        authority = getIntent().getIntExtra("authority", DEFAULT_AUTHORITY_ID);
        sortOptionKey = getIntent().getStringExtra("sortOptionKey");
        ratingKey = getIntent().getIntExtra("minRating", DEFAULT_MIN_RATING);
        Log.i(TAG, "onCreate: reached before second check");
        getPageResults();
    }

    public void onGoToPreviousPage(View view) {
        Log.i(TAG, "onGoToPreviousPage: loaded previous page");
        this.pageNumber -= 1;
        if (this.pageNumber != 0) {
            getPageResults();
        } else
            this.pageNumber += 1;
    }

    public void onGoToNextPage(View view) {
        Log.i(TAG, "onGoToNextPage: loaded next page");
        int totalPages = Integer.valueOf(
                pageNumberTextView.getText().toString().split("/")[1]
        );
        if (this.pageNumber + 1 <= totalPages) {
            this.pageNumber += 1;
            getPageResults();
        }
    }

    private void getPageResults() {
        final AlertDialog.Builder noResultsFound = new AlertDialog.Builder(this)
                .setPositiveButton(android.R.string.yes, null);
        Log.i(TAG, "getPageResults: reached getPageREsults");
        if (searchValue != null) {
            Log.i(TAG, "onCreate: SearchValue is " + searchValue);
            new EstablishmentsAsyncTask(
                    rView,
                    pageNumberTextView,
                    noResultsFound,
                    this
            )
                    .execute(
                            searchValue,
                            String.valueOf(businessType),
                            String.valueOf(region),
                            String.valueOf(authority),
                            String.valueOf(pageNumber),
                            String.valueOf(pageSize),
                            String.valueOf(sortOptionKey),
                            String.valueOf(ratingKey)
                    );
        } else if (longitude != null && latitude != null) {
            Log.i(TAG, "onCreate: Long and lat are " + longitude + " " + latitude);
            Log.i(TAG, "getPageResults: max disti value here " + maxDistanceLimit);
            new EstablishmentsAsyncTask(
                    rView,
                    pageNumberTextView,
                    noResultsFound,
                    this
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
                            String.valueOf(sortOptionKey),
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

    public void showLegend(View view) {
        android.app.AlertDialog.Builder showLegend = new android.app.AlertDialog.Builder(this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        showLegend.setTitle("Establishments Legend");
        showLegend.setMessage(
                TextUtils.concat(
                        Html.fromHtml("<font color='#98d4bb'>Distributors/Transporters</font>", Build.VERSION.SDK_INT),
                        "\n",
                        Html.fromHtml("<font color='#a9a9a9'>Farmers/growers</font>", Build.VERSION.SDK_INT),
                        "\n",
                        Html.fromHtml("<font color='#fffac8'>Hospitals/Childcare/Caring Premises</font>", Build.VERSION.SDK_INT),
                        "\n",
                        Html.fromHtml("<font color='#F5E2E4'>Hotel/bed & breakfast/guest house</font>", Build.VERSION.SDK_INT),
                        "\n",
                        Html.fromHtml("<font color='#EEBAB2'>Importers/Exporters</font>", Build.VERSION.SDK_INT),
                        "\n",
                        Html.fromHtml("<font color='#f58231'>Manufacturers/packers</font>", Build.VERSION.SDK_INT),
                        "\n",
                        Html.fromHtml("<font color='#F5F3E7'>Mobile caterer</font>", Build.VERSION.SDK_INT),
                        "\n",
                        Html.fromHtml("<font color='#42d4f4'>Other catering premises</font>", Build.VERSION.SDK_INT),
                        "\n",
                        Html.fromHtml("<font color='#CCD4BF'>Pub/bar/nightclub</font>", Build.VERSION.SDK_INT),
                        "\n",
                        Html.fromHtml("<font color='#bfef45'>Restaurant/Cafe/Canteen</font>", Build.VERSION.SDK_INT),
                        "\n",
                        Html.fromHtml("<font color='#fabebe'>Retailers - other</font>", Build.VERSION.SDK_INT),
                        "\n",
                        Html.fromHtml("<font color='#ffe119'>Retailers - supermarkets/hypermarkets</font>", Build.VERSION.SDK_INT),
                        "\n",
                        Html.fromHtml("<font color='#e6beff'>School/college/university</font>", Build.VERSION.SDK_INT),
                        "\n",
                        Html.fromHtml("<font color='#e5db9c'>Takeaway/sandwich shop</font>", Build.VERSION.SDK_INT)
                )
        );
        showLegend.setPositiveButton("OK", null);
        showLegend.show();
    }
}
