package com.bham.restaurantapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.bham.restaurantapp.R;
import com.bham.restaurantapp.background.async.DeleteAllFavouritesAsyncTask;
import com.bham.restaurantapp.background.async.RefreshDbAsyncTask;
import com.bham.restaurantapp.background.async.SearchScreenAsyncTask;
import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import static com.bham.restaurantapp.Globals.DEFAULT_AUTHORITY_ID;
import static com.bham.restaurantapp.Globals.DEFAULT_AUTHORITY_POSITION;
import static com.bham.restaurantapp.Globals.DEFAULT_BUSINESS_TYPE_ID;
import static com.bham.restaurantapp.Globals.DEFAULT_BUSINESS_TYPE_POSITION;
import static com.bham.restaurantapp.Globals.DEFAULT_MAX_DISTANCE_LIMIT;
import static com.bham.restaurantapp.Globals.DEFAULT_MIN_RATING;
import static com.bham.restaurantapp.Globals.DEFAULT_REGION_ID;
import static com.bham.restaurantapp.Globals.DEFAULT_REGION_POSITION;

public class SearchScreenActivity extends AppCompatActivity {
    private static final String TAG = "SearchScreenActivity";
    private EditText establishmentSearchEditText;
    private Spinner sortBySpinner;
    private int businessType;
    private int region;
    private int authority;
    private float maxDistanceLimit;
    private int minRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
        sortBySpinner = findViewById(R.id.sortBySpinner);
        new SearchScreenAsyncTask(
                getApplicationContext(),
                sortBySpinner,
                getIntent().getFloatExtra("maxDistanceLimit", DEFAULT_MAX_DISTANCE_LIMIT)
        )
                .execute();
        establishmentSearchEditText = findViewById(R.id.searchFieldEditText);
        if (savedInstanceState != null)
            establishmentSearchEditText.setText(savedInstanceState.getString("searchValue"));
        else if (getIntent().getStringExtra("searchValue") != null)
            establishmentSearchEditText.setText(
                    String.valueOf(getIntent().getStringExtra("searchValue"))
            );

        Log.i(TAG, "Recreating activity");
        businessType = getIntent().getIntExtra("businessType", DEFAULT_BUSINESS_TYPE_ID);
        region = getIntent().getIntExtra("region", DEFAULT_REGION_ID);
        authority = getIntent().getIntExtra("authority", DEFAULT_AUTHORITY_ID);
        maxDistanceLimit = getIntent().getFloatExtra("maxDistanceLimit", DEFAULT_MAX_DISTANCE_LIMIT);
        minRating = getIntent().getIntExtra("minRating", DEFAULT_MIN_RATING);
        if (
                businessType != DEFAULT_BUSINESS_TYPE_ID ||
                        region != DEFAULT_REGION_ID ||
                        authority != DEFAULT_AUTHORITY_ID ||
                        maxDistanceLimit != DEFAULT_MAX_DISTANCE_LIMIT ||
                        minRating != DEFAULT_MIN_RATING
        ) {
            Log.i(TAG, "Entered if statement");
            MaterialButton addFiltersButton = findViewById(R.id.addSearchFiltersButton);
            addFiltersButton.setText(
                    getString(R.string.search_filters_button_modify_filters)
            );
        }
    }


    public void sendEstablishmentSearchEnquiry(View view) {
        Intent sendSearchValueIntent = new Intent(this, EstablishmentListViewActivity.class);
        sendSearchValueIntent.putExtra("searchValue", establishmentSearchEditText.getText().toString());
        sendSearchValueIntent.putExtra("businessType", businessType);
        sendSearchValueIntent.putExtra("region", region);
        sendSearchValueIntent.putExtra("authority", authority);
        sendSearchValueIntent.putExtra("maxDistanceLimit", maxDistanceLimit);
        sendSearchValueIntent.putExtra("minRating", minRating);

        Cursor sortByCursor = (Cursor) sortBySpinner.getSelectedItem();
        String test = sortByCursor.getString(sortByCursor.getColumnIndex("sort_option_key"));
        Log.i(TAG, "viewAllEstablishmentsEnquiry: Sort option is " + test);
        sendSearchValueIntent.putExtra(
                "sortOptionKey",
                sortByCursor.getString(sortByCursor.getColumnIndex("sort_option_key"))
        );

        startActivity(sendSearchValueIntent);
    }

    public void viewAllEstablishmentsEnquiry(View view) {
        Intent viewAllEstablishmentsIntent = new Intent(this, ViewAllEstablishmentsActivity.class);
        Cursor sortByCursor = (Cursor) sortBySpinner.getSelectedItem();
        String test = sortByCursor.getString(sortByCursor.getColumnIndex("sort_option_key"));
        Log.i(TAG, "viewAllEstablishmentsEnquiry: Sort option is " + test);
        viewAllEstablishmentsIntent.putExtra(
                "sortOptionKey",
                sortByCursor.getString(sortByCursor.getColumnIndex("sort_option_key"))
        );

        startActivity(viewAllEstablishmentsIntent);
    }

    public void refreshRoomDB(View view) {
        final AlertDialog.Builder successAlert = new AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage("DB refreshed")
                .setPositiveButton(android.R.string.yes, null);

        AlertDialog.Builder confirmationAlertDialog = new AlertDialog.Builder(this);
        confirmationAlertDialog.setTitle("Refresh DB");
        confirmationAlertDialog.setMessage("Are you sure you want to refresh database?" +
                "This will delete all database data.");
        confirmationAlertDialog.setPositiveButton(android.R.string.yes, (dialog, which) ->
                new RefreshDbAsyncTask(successAlert)
                        .execute());
        confirmationAlertDialog.setNegativeButton(android.R.string.no, null);
        confirmationAlertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        confirmationAlertDialog.show();

    }

    public void openSearchFiltersActivity(View view) {
        Intent openSearchFiltersIntent = new Intent(this, SearchFiltersActivity.class);
        openSearchFiltersIntent.putExtra("businessType", businessType);
        openSearchFiltersIntent.putExtra("region", region);
        openSearchFiltersIntent.putExtra("authority", authority);
        openSearchFiltersIntent.putExtra("searchValue", establishmentSearchEditText.getText().toString());
        openSearchFiltersIntent.putExtra(
                "businessTypePosition",
                getIntent().getIntExtra("businessTypePosition", DEFAULT_BUSINESS_TYPE_POSITION)
        );
        openSearchFiltersIntent.putExtra(
                "regionPosition",
                getIntent().getIntExtra("regionPosition", DEFAULT_REGION_POSITION)
        );
        openSearchFiltersIntent.putExtra(
                "authorityPosition",
                getIntent().getIntExtra("authorityPosition", DEFAULT_AUTHORITY_POSITION)
        );
        openSearchFiltersIntent.putExtra("maxDistanceLimit", maxDistanceLimit);
        startActivity(openSearchFiltersIntent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "Storing state");
        outState.putString("searchValue", establishmentSearchEditText.getText().toString());
    }

    public void deleteFavourites(View view) {
        final AlertDialog.Builder successAlert = new AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage("Favourites removed")
                .setPositiveButton(android.R.string.yes, null);

        AlertDialog.Builder confirmationAlertDialog = new AlertDialog.Builder(this);
        confirmationAlertDialog.setTitle("Delete favourites");
        confirmationAlertDialog.setMessage("Are you sure you want to delete favourites?");
        confirmationAlertDialog.setPositiveButton(android.R.string.yes, (dialog, which) ->
                new DeleteAllFavouritesAsyncTask(successAlert)
                        .execute()
        );
        confirmationAlertDialog.setNegativeButton(android.R.string.no, null);
        confirmationAlertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        confirmationAlertDialog.show();

    }

    public void openFavourites(View view) {
        Intent openFavouritesIntent = new Intent(this, FavouritesListViewActivity.class);
        startActivity(openFavouritesIntent);
    }

    public void openLogin(View view) {
        Intent i = new Intent(this, LoginScreenActivity.class);
        startActivity(i);
    }
}
