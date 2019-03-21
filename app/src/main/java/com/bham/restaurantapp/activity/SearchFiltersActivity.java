package com.bham.restaurantapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;

import com.bham.restaurantapp.R;
import com.bham.restaurantapp.background.async.PopulateFiltersAsyncTask;

import androidx.appcompat.app.AppCompatActivity;

public class SearchFiltersActivity extends AppCompatActivity {
    private static final String TAG = "SearchFiltersActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filters);
        Spinner businessTypesSpinner = findViewById(R.id.businessTypesSpinner);
        Log.i(TAG, "Populating business types Spinner");
        new PopulateFiltersAsyncTask(
                getApplicationContext(),
                businessTypesSpinner
        ).execute();
    }
}
