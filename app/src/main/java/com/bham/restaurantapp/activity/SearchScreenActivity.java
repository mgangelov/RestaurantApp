package com.bham.restaurantapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.bham.restaurantapp.R;
import com.bham.restaurantapp.background.async.RefreshDbAsyncTask;
import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SearchScreenActivity extends AppCompatActivity {
    private static final String TAG = "SearchScreenActivity";
    EditText establishmentSearchEditText;
    private int businessType;
    private int region;
    private int authority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
        establishmentSearchEditText = findViewById(R.id.searchFieldEditText);
        Log.i(TAG, "Recreating activity");
        businessType = getIntent().getIntExtra("businessType", -1);
        region = getIntent().getIntExtra("region", 99);
        authority = getIntent().getIntExtra("authority", 8999);
        if (businessType != -1 || region != 99 || authority != 8999) {
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
        startActivity(sendSearchValueIntent);
    }

    public void viewAllEstablishmentsEnquiry(View view) {
        Intent viewAllEstablishmentsIntent = new Intent(this, ViewAllEstablishmentsActivity.class);
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
        confirmationAlertDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new RefreshDbAsyncTask(getApplicationContext(), successAlert)
                        .execute();
            }
        });
        confirmationAlertDialog.setNegativeButton(android.R.string.no, null);
        confirmationAlertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        confirmationAlertDialog.show();

    }

    public void openSearchFiltersActivity(View view) {
        Intent openSearchFiltersIntent = new Intent(this, SearchFiltersActivity.class);
        openSearchFiltersIntent.putExtra("businessType", businessType);
        openSearchFiltersIntent.putExtra("region", region);
        openSearchFiltersIntent.putExtra("authority", authority);
        openSearchFiltersIntent.putExtra(
                "businessTypePosition",
                getIntent().getIntExtra("businessTypePosition", 0)
        );
        openSearchFiltersIntent.putExtra(
                "regionPosition",
                getIntent().getIntExtra("regionPosition", 0)
        );
        openSearchFiltersIntent.putExtra(
                "authorityPosition",
                getIntent().getIntExtra("authorityPosition", 0)
        );
        startActivity(openSearchFiltersIntent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("searchTerm", establishmentSearchEditText.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        establishmentSearchEditText.setText(savedInstanceState.getString("searchTerm"));

    }
}
