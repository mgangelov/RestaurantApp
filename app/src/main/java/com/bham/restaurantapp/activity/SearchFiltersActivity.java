package com.bham.restaurantapp.activity;

import android.content.Intent;
import android.database.Cursor;
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
                authoritiesSpinner,
                getIntent().getIntExtra("businessTypePosition", 0),
                getIntent().getIntExtra("regionPosition", 0),
                getIntent().getIntExtra("authorityPosition", 0)
        ).execute();
    }

    public void clearFilterChanges(View view) {
        Log.i(TAG, "Clearing all filter changes");
        new PopulateFiltersAsyncTask(
                getApplicationContext(),
                businessTypesSpinner,
                regionSpinner,
                authoritiesSpinner,
                0,
                0,
                0
        ).execute();
    }

    public void submitFilters(View view) {
        Cursor businessTypeCursor = (Cursor) businessTypesSpinner.getSelectedItem();
        Cursor regionCursor = (Cursor) regionSpinner.getSelectedItem();
        Cursor authorityCursor = (Cursor) authoritiesSpinner.getSelectedItem();
        String businessTypeResult = businessTypeCursor.getString(
                businessTypeCursor.getColumnIndex("business_type_name")
        );
        String regionResult = regionCursor.getString(
                regionCursor.getColumnIndex("region_name")
        );
        String authorityResult = authorityCursor.getString(
                authorityCursor.getColumnIndex("authority_name")
        );
        Log.i(TAG, String.format(
                "The selected values are %s\n%s\n%s",
                businessTypeResult,
                regionResult,
                authorityResult
        ));
        Log.i(TAG, String.format(
                "The selected ids are %s %s %s",
                businessTypeCursor.getInt(businessTypeCursor.getColumnIndex("_id")),
                regionCursor.getInt(regionCursor.getColumnIndex("_id")),
                authorityCursor.getInt(authorityCursor.getColumnIndex("_id"))
        ));


        Intent searchScreenIntent = new Intent(this, SearchScreenActivity.class);
        searchScreenIntent.putExtra(
                "businessType",
                businessTypeCursor.getInt(
                        businessTypeCursor.getColumnIndex("_id")
                )
        );
        searchScreenIntent.putExtra(
                "businessTypePosition", businessTypesSpinner.getSelectedItemPosition()
        );
        searchScreenIntent.putExtra(
                "region",
                regionCursor.getInt(
                        regionCursor.getColumnIndex("_id")
                )
        );
        searchScreenIntent.putExtra(
                "regionPosition", regionSpinner.getSelectedItemPosition()
        );
        searchScreenIntent.putExtra(
                "authority",
                authorityCursor.getInt(
                        authorityCursor.getColumnIndex("_id")
                )
        );
        searchScreenIntent.putExtra(
                "authorityPosition", authoritiesSpinner.getSelectedItemPosition()
        );
        startActivity(searchScreenIntent);
    }

}
