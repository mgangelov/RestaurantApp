package com.bham.restaurantapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bham.restaurantapp.R;
import com.bham.restaurantapp.background.async.RefreshDbAsyncTask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SearchScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
    }

    public void sendEstablishmentSearchEnquiry(View view) {
        EditText establishmentSearchEditText = findViewById(R.id.searchFieldEditText);
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
                "This will delete all data, including favourites");
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
        startActivity(openSearchFiltersIntent);
    }
}
