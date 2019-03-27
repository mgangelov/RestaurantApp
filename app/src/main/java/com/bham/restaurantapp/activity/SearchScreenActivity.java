package com.bham.restaurantapp.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.bham.restaurantapp.App;
import com.bham.restaurantapp.R;
import com.bham.restaurantapp.background.async.DeleteAllFavouritesAsyncTask;
import com.bham.restaurantapp.background.async.RefreshDbAsyncTask;
import com.google.android.material.button.MaterialButton;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER;
import static com.bham.restaurantapp.Globals.DEFAULT_AUTHORITY_ID;
import static com.bham.restaurantapp.Globals.DEFAULT_AUTHORITY_POSITION;
import static com.bham.restaurantapp.Globals.DEFAULT_BUSINESS_TYPE_ID;
import static com.bham.restaurantapp.Globals.DEFAULT_BUSINESS_TYPE_POSITION;
import static com.bham.restaurantapp.Globals.DEFAULT_MAX_DISTANCE_LIMIT;
import static com.bham.restaurantapp.Globals.DEFAULT_MIN_RATING;
import static com.bham.restaurantapp.Globals.DEFAULT_REGION_ID;
import static com.bham.restaurantapp.Globals.DEFAULT_REGION_POSITION;

public class SearchScreenActivity extends AppCompatActivity {
    private final int FINE_LOCATION_PERMISSION = 1;
    private LocationManager locManager;
    private LocationListener locListener;
    private static final String TAG = "SearchScreenActivity";
    private EditText establishmentSearchEditText;
    private Spinner sortBySpinner;
    private String longitude = "";
    private String latitude = "";
    private boolean useLocation = false;
    private int businessType;
    private int region;
    private int authority;
    private int maxDistanceLimit;
    private int minRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
        establishmentSearchEditText = findViewById(R.id.searchFieldEditText);
        sortBySpinner = findViewById(R.id.sortBySpinner);
        sortBySpinner.setAdapter(new SimpleCursorAdapter(
                getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                App.getInstance().getDb().sortOptionsDAO().getAllCursor(),
                new String[]{"sort_option_name"},
                new int[]{android.R.id.text1},
                FLAG_REGISTER_CONTENT_OBSERVER
        ));
        if (savedInstanceState != null) {
            Log.i(TAG, "onCreate: saved instance stae is not null!");
            int sortPosition = savedInstanceState.getInt("sortPosition");
            Log.i(TAG, "onCreate: sortPosition: " + sortPosition);
            sortBySpinner.setSelection(sortPosition);
            if (savedInstanceState.getString("searchValue") != null) {
                Log.i(TAG, "onCreate: restoring search value");
                establishmentSearchEditText.setText(savedInstanceState.getString("searchValue"));
            } else {
                Log.i(TAG, "onCreate: restoring coordinates");
                establishmentSearchEditText.setText(
                        String.format(
                                Locale.ENGLISH,
                                "%s %s",
                                savedInstanceState.getString("longitude"),
                                savedInstanceState.getString("latitude")
                        )
                );
                useLocation = true;
            }
        }

        if (getIntent().getStringExtra("searchValue") != null)
            establishmentSearchEditText.setText(getIntent().getStringExtra("searchValue"));
        else if (getIntent().getStringExtra("longitude") != null && getIntent().getStringExtra("latitude") != null) {
            establishmentSearchEditText.setText(String.format(
                    "%s %s",
                    getIntent().getStringExtra("longitude"),
                    getIntent().getStringExtra("latitude")
            ));
            useLocation = true;
        }

        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                Log.i(TAG, "onLocationChanged: logiiii " + longitude);
                Log.i(TAG, "onLocationChanged: latiiii " + latitude);
                establishmentSearchEditText.setText(String.format(Locale.ENGLISH, "%f %f", longitude, latitude));
                useLocation = true;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        Log.i(TAG, "Recreating activity");
        businessType = getIntent().getIntExtra("businessType", DEFAULT_BUSINESS_TYPE_ID);
        region = getIntent().getIntExtra("region", DEFAULT_REGION_ID);
        authority = getIntent().getIntExtra("authority", DEFAULT_AUTHORITY_ID);
        maxDistanceLimit = getIntent().getIntExtra("maxDistanceLimit", DEFAULT_MAX_DISTANCE_LIMIT);
        Log.i(TAG, "onCreate: max distance received is " + maxDistanceLimit);
        minRating = getIntent().getIntExtra("minRating", DEFAULT_MIN_RATING);
        Log.i(TAG, "onCreate: minRating received is " + minRating);
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
        if (useLocation) {
            String coords = establishmentSearchEditText.getText().toString();
            sendSearchValueIntent.putExtra("longitude", coords.split(" ")[0]);
            sendSearchValueIntent.putExtra("latitude", coords.split(" ")[1]);
        } else
            sendSearchValueIntent.putExtra("searchValue", establishmentSearchEditText.getText().toString());
        sendSearchValueIntent.putExtra("businessType", businessType);
        sendSearchValueIntent.putExtra("region", region);
        sendSearchValueIntent.putExtra("authority", authority);
        Log.i(TAG, "sendEstablishmentSearchEnquiry: max dist limit " + maxDistanceLimit);
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
        if (!useLocation)
            openSearchFiltersIntent.putExtra("searchValue", establishmentSearchEditText.getText().toString());
        else {
            openSearchFiltersIntent.putExtra("longitude", establishmentSearchEditText.getText().toString().split(" ")[0]);
            openSearchFiltersIntent.putExtra("latitude", establishmentSearchEditText.getText().toString().split(" ")[1]);
        }
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
        openSearchFiltersIntent.putExtra("minRating", minRating);
        startActivity(openSearchFiltersIntent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "Storing state");
        outState.putInt("sortPosition", sortBySpinner.getSelectedItemPosition());
        if (useLocation) {
            Log.i(TAG, "onSaveInstanceState: storing coords");
            outState.putString("longitude", establishmentSearchEditText.getText().toString().split(" ")[0]);
            outState.putString("latitude", establishmentSearchEditText.getText().toString().split(" ")[1]);
        } else
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

    public void accessCurrentLocation(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(view.getContext(), android.R.anim.fade_in));
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                requestLocPerms();
            } else {
                requestLocPerms();
                locManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locListener, Looper.getMainLooper());
            }
        } else {
            locManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locListener, Looper.getMainLooper());
        }
    }

    private void requestLocPerms() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_PERMISSION);
    }
}
