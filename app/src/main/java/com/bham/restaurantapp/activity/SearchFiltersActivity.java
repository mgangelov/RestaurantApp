package com.bham.restaurantapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bham.restaurantapp.R;
import com.bham.restaurantapp.background.async.PopulateFiltersAsyncTask;

import androidx.appcompat.app.AppCompatActivity;

public class SearchFiltersActivity extends AppCompatActivity {
    private static final String TAG = "SearchFiltersActivity";
    private Spinner businessTypesSpinner;
    private Spinner regionSpinner;
    private Spinner authoritiesSpinner;
    private float maxDistanceLimit;
    TextView seekBarCurrentProgressTextView;
    private SeekBar maxDistanceSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filters);
        businessTypesSpinner = findViewById(R.id.businessTypesSpinner);
        regionSpinner = findViewById(R.id.regionsSpinner);
        authoritiesSpinner = findViewById(R.id.authoritiesSpinner);
        maxDistanceSeekBar = findViewById(R.id.maxDistanceLimitSeekBar);
        maxDistanceLimit = getIntent()
                .getFloatExtra("maxDistanceLimit", 3);
        maxDistanceSeekBar.setProgress((int) (maxDistanceLimit * 10));
        maxDistanceSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        seekBarCurrentProgressTextView =
                findViewById(R.id.seekBarCurrentProgressTextView);
        seekBarCurrentProgressTextView.setText(String.valueOf(
                convertIntToFloat(maxDistanceSeekBar.getProgress())
                ));
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

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            seekBarCurrentProgressTextView.setText(
                    String.valueOf(convertIntToFloat(progress))
            );
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            maxDistanceLimit = convertIntToFloat(seekBar.getProgress());

        }
    };

    public float convertIntToFloat(int progress) {
        return .1f * progress;
    }

    public void clearFilterChanges(View view) {
        Log.i(TAG, "Clearing all filter changes");
        maxDistanceSeekBar.setProgress(30);
        seekBarCurrentProgressTextView.setText(String.valueOf(
                convertIntToFloat(maxDistanceSeekBar.getProgress())
                ));
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
                "businessTypePosition",
                businessTypesSpinner.getSelectedItemPosition()
        );
        searchScreenIntent.putExtra(
                "region",
                regionCursor.getInt(
                        regionCursor.getColumnIndex("_id")
                )
        );
        searchScreenIntent.putExtra(
                "regionPosition",
                regionSpinner.getSelectedItemPosition()
        );
        searchScreenIntent.putExtra(
                "authority",
                authorityCursor.getInt(
                        authorityCursor.getColumnIndex("_id")
                )
        );
        searchScreenIntent.putExtra(
                "authorityPosition",
                authoritiesSpinner.getSelectedItemPosition()
        );
        searchScreenIntent.putExtra(
                "maxDistanceLimit",
                maxDistanceLimit
        );
        Log.i(TAG, String.format("submitFilters: searchValue is %s", getIntent().getStringExtra("searchValue")));
        searchScreenIntent.putExtra(
                "searchValue",
                getIntent().getStringExtra("searchValue")
        );
        startActivity(searchScreenIntent);
    }

}
