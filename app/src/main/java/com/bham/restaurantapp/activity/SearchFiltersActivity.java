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

import static com.bham.restaurantapp.Globals.DEFAULT_AUTHORITY_POSITION;
import static com.bham.restaurantapp.Globals.DEFAULT_BUSINESS_TYPE_POSITION;
import static com.bham.restaurantapp.Globals.DEFAULT_MAX_DISTANCE_LIMIT;
import static com.bham.restaurantapp.Globals.DEFAULT_MIN_RATING;
import static com.bham.restaurantapp.Globals.DEFAULT_REGION_POSITION;

public class SearchFiltersActivity extends AppCompatActivity {
    private static final String TAG = "SearchFiltersActivity";
    private Spinner businessTypesSpinner;
    private Spinner regionSpinner;
    private Spinner authoritiesSpinner;
    private float maxDistanceLimit;
    private int minRating;
    TextView maxDistanceLimitProgressTextView;
    TextView minRatingProgressTextView;
    private SeekBar maxDistanceSeekBar;
    private SeekBar minRatingSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filters);
        populateFilterSettings();
    }

    private void populateFilterSettings() {
        businessTypesSpinner = findViewById(R.id.businessTypesSpinner);
        regionSpinner = findViewById(R.id.regionsSpinner);
        authoritiesSpinner = findViewById(R.id.authoritiesSpinner);
        Log.i(TAG, "onCreate: Initialising the seek bars");
        maxDistanceSeekBar = findViewById(R.id.maxDistanceLimitSeekBar);
        maxDistanceLimit = getIntent()
                .getFloatExtra("maxDistanceLimit", DEFAULT_MAX_DISTANCE_LIMIT);
        maxDistanceSeekBar.setProgress((int) (maxDistanceLimit * 10));
        maxDistanceSeekBar.setOnSeekBarChangeListener(maxDistanceLimitChangeListener);
        maxDistanceLimitProgressTextView =
                findViewById(R.id.maxDistanceLimitProgressTextView);
        maxDistanceLimitProgressTextView.setText(String.valueOf(
                convertIntToFloat(maxDistanceSeekBar.getProgress())
        ));
        minRating = getIntent()
                .getIntExtra("minRating", DEFAULT_MIN_RATING);
        minRatingSeekBar = findViewById(R.id.minRatingSeekBar);
        minRatingSeekBar.setProgress(minRating);
        minRatingSeekBar.setOnSeekBarChangeListener(minRatingChangeListener);
        minRatingProgressTextView =
                findViewById(R.id.minRatingProgressTextView);
        minRatingProgressTextView.setText(
                String.valueOf(minRatingSeekBar.getProgress())
        );
        Log.i(TAG, "Populating business types Spinner");
        new PopulateFiltersAsyncTask(
                getApplicationContext(),
                businessTypesSpinner,
                regionSpinner,
                authoritiesSpinner,
                getIntent().getIntExtra("businessTypePosition", DEFAULT_BUSINESS_TYPE_POSITION),
                getIntent().getIntExtra("regionPosition", DEFAULT_REGION_POSITION),
                getIntent().getIntExtra("authorityPosition", DEFAULT_AUTHORITY_POSITION)
        ).execute();
    }

    SeekBar.OnSeekBarChangeListener maxDistanceLimitChangeListener =
            new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    maxDistanceLimitProgressTextView.setText(
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

    SeekBar.OnSeekBarChangeListener minRatingChangeListener =
            new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    minRatingProgressTextView.setText(String.valueOf(progress));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    minRating = seekBar.getProgress();
                }
            };

    public float convertIntToFloat(int progress) {
        return .1f * progress;
    }

    public void clearFilterChanges(View view) {
        Log.i(TAG, "Clearing all filter changes");
        maxDistanceSeekBar.setProgress((int) (DEFAULT_MAX_DISTANCE_LIMIT * 10));
        maxDistanceLimitProgressTextView.setText(String.valueOf(
                convertIntToFloat(maxDistanceSeekBar.getProgress())
        ));
        minRatingSeekBar.setProgress(DEFAULT_MIN_RATING);
        minRatingProgressTextView.setText(
                String.valueOf(minRatingSeekBar.getProgress())
        );
        new PopulateFiltersAsyncTask(
                getApplicationContext(),
                businessTypesSpinner,
                regionSpinner,
                authoritiesSpinner,
                DEFAULT_BUSINESS_TYPE_POSITION,
                DEFAULT_REGION_POSITION,
                DEFAULT_AUTHORITY_POSITION
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
        Log.i(TAG, "submitFilters: minRating is " + minRating);
        searchScreenIntent.putExtra(
                "minRating",
                minRating
        );
        startActivity(searchScreenIntent);
    }

    public void dismissChanges(View view) {
        populateFilterSettings();
        this.finish();
    }
}
