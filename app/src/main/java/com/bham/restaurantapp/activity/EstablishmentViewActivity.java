package com.bham.restaurantapp.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bham.restaurantapp.Globals;
import com.bham.restaurantapp.R;
import com.bham.restaurantapp.background.async.AddFavouriteEstablishmentAsyncTask;
import com.bham.restaurantapp.model.db.entities.EstablishmentEntity;
import com.google.android.material.button.MaterialButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class EstablishmentViewActivity extends AppCompatActivity {
    private static final String TAG = "EstablishmentViewActivity";
    private EstablishmentEntity establishmentEntity;
    private MaterialButton addToFavouritesMaterialButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment_view);
        addToFavouritesMaterialButton = findViewById(R.id.addToFavouritesButton);
        establishmentEntity = new EstablishmentEntity(
                getIntent().getIntExtra("fhrsId", 0),
                getIntent().getStringExtra("businessName"),
                getIntent().getStringExtra("businessType"),
                getIntent().getIntExtra("businessTypeId", 9999),
                getIntent().getStringExtra("addressLine1"),
                getIntent().getStringExtra("addressLine2"),
                getIntent().getStringExtra("addressLine3"),
                getIntent().getStringExtra("addressLine4"),
                getIntent().getStringExtra("postCode"),
                getIntent().getIntExtra("ratingValue", 0),
                getIntent().getStringExtra("ratingDate"),
                getIntent().getStringExtra("localAuthorityName")
        );

        new AddFavouriteEstablishmentAsyncTask(
                getApplicationContext(),
                addToFavouritesMaterialButton,
                Globals.MODES.CHECK_MODE
        ).execute(
                establishmentEntity
        );

        TextView establishmentTitleTextView =
                findViewById(R.id.establishmentTitleTextView);
        establishmentTitleTextView.setBackgroundColor(
                ContextCompat.getColor(
                        getApplicationContext(),
                        getResources().getIdentifier(
                                String.format(
                                        "btypeid%s",
                                        establishmentEntity.businessTypeId
                                ),
                                "color",
                                getPackageName()
                        )
                )
        );
        establishmentTitleTextView.setText(
                establishmentEntity.businessName
        );


        TextView establishmentRatingDateTextView =
                findViewById(R.id.establishmentRatingDateTextView);
        DateTimeFormatter ratingDateFormat =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.UK);
        LocalDate ratingDate = LocalDate.parse(
                establishmentEntity.ratingDate,
                ratingDateFormat
        );
        establishmentRatingDateTextView.setText(ratingDate.toString());


        TextView establishmentAuthorityTextView =
                findViewById(R.id.establishmentAuthorityTextView);
        establishmentAuthorityTextView.setText(
                establishmentEntity.localAuthorityName
        );


        TextView establishmentBusinessTypeTextView =
                findViewById(R.id.establishmentBusinessTypeTextView);
        establishmentBusinessTypeTextView.setText(
                establishmentEntity.businessType
        );


        TextView establishmentAddressTextView =
                findViewById(R.id.establishmentAddressTextView);
        StringBuilder addressLine = new StringBuilder();
        if (getIntent().getStringExtra("addressLine1") != null)
            addressLine
                    .append(getIntent().getStringExtra("addressLine1"))
                    .append(",\n");
        if (getIntent().getStringExtra("addressLine2") != null)
            addressLine
                    .append(getIntent().getStringExtra("addressLine2"))
                    .append(",\n");
        if (getIntent().getStringExtra("addressLine3") != null)
            addressLine
                    .append(getIntent().getStringExtra("addressLine3"))
                    .append(",\n");
        if (getIntent().getStringExtra("addressLine4") != null)
            addressLine
                    .append(getIntent().getStringExtra("addressLine4"))
                    .append(",\n");
        if (getIntent().getStringExtra("postCode") != null)
            addressLine
                    .append(getIntent().getStringExtra("postCode"));
        establishmentAddressTextView.setText(
                addressLine.toString()
        );

        RatingBar establishmentRatingRatingBar =
                findViewById(R.id.establishmentRatingRatingBar);
        establishmentRatingRatingBar.setRating(
                establishmentEntity.ratingValue
        );
    }

    public void goBack(View view) {
        Log.i(TAG, "goBack: Activity finished");
        this.finish();
    }

    public void addEstablishmentToFavourites(View view) {
        final AlertDialog.Builder successAlert = new AlertDialog.Builder(this)
                .setPositiveButton(android.R.string.yes, null);
        if (addToFavouritesMaterialButton.getText().equals(getString(R.string.add_to_favourites_button))) {
            Log.i(TAG, "addEstablishmentToFavourites: Adding to favourites");
            new AddFavouriteEstablishmentAsyncTask(
                    getApplicationContext(),
                    addToFavouritesMaterialButton,
                    Globals.MODES.ADD_MODE,
                    successAlert).execute(
                    establishmentEntity
            );
            Toast.makeText(getApplicationContext(), "Added to favourites", Toast.LENGTH_LONG)
                    .show();
        }
        if (addToFavouritesMaterialButton.getText().equals(getString(R.string.remove_favourite_establishment))) {
            Log.i(TAG, "addEstablishmentToFavourites: Removing from favourites");
            new AddFavouriteEstablishmentAsyncTask(
                    getApplicationContext(),
                    addToFavouritesMaterialButton,
                    Globals.MODES.REMOVE_MODE,
                    successAlert).execute(
                    establishmentEntity
            );
            Toast.makeText(getApplicationContext(), "Removed from favourites", Toast.LENGTH_LONG)
                    .show();
        }
    }
}
