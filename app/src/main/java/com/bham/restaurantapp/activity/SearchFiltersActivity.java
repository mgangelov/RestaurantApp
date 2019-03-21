package com.bham.restaurantapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

import com.bham.restaurantapp.R;
import com.bham.restaurantapp.background.async.PopulateFiltersAsyncTask;

import androidx.appcompat.app.AppCompatActivity;

public class SearchFiltersActivity extends AppCompatActivity {
    private static final String TAG = "SearchFiltersActivity";
    private Spinner businessTypesSpinner;
    private Spinner regionSpinner;
    private Spinner authoritiesSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filters);
        businessTypesSpinner = findViewById(R.id.businessTypesSpinner);
        regionSpinner = findViewById(R.id.regionsSpinner);
        authoritiesSpinner = findViewById(R.id.authoritiesSpinner);
        Log.i(TAG, "Populating business types Spinner");
        new PopulateFiltersAsyncTask(
                getApplicationContext(),
                businessTypesSpinner,
                regionSpinner,
                authoritiesSpinner
        ).execute();
    }

    public void clearFilterChanges(View view) {
        Log.i(TAG, "Clearing all filter changes");
        new PopulateFiltersAsyncTask(
                getApplicationContext(),
                businessTypesSpinner,
                regionSpinner,
                authoritiesSpinner
        ).execute();
    }
}
