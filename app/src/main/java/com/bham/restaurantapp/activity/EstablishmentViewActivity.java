package com.bham.restaurantapp.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
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
    private MaterialButton addNoteMaterialButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment_view);
        addToFavouritesMaterialButton = findViewById(R.id.addToFavouritesButton);
        addNoteMaterialButton = findViewById(R.id.addNoteButton);
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
                addNoteMaterialButton,
                Globals.FAVOURITE_MODES.CHECK_MODE
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
                    addNoteMaterialButton,
                    Globals.FAVOURITE_MODES.ADD_MODE,
                    successAlert).execute(
                    establishmentEntity
            );
        }
        if (addToFavouritesMaterialButton.getText().equals(getString(R.string.remove_favourite_establishment))) {
            Log.i(TAG, "addEstablishmentToFavourites: Removing from favourites");
            new AddFavouriteEstablishmentAsyncTask(
                    getApplicationContext(),
                    addToFavouritesMaterialButton,
                    addNoteMaterialButton,
                    Globals.FAVOURITE_MODES.REMOVE_MODE,
                    successAlert).execute(
                    establishmentEntity
            );
        }
    }

    public void addNoteToFavourite(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        if (sharedPreferences.getString("passwordHash", null) != null) {
            Intent i = new Intent(this, ManageNoteActivity.class);
            i.putExtra("establishmentId", establishmentEntity._id);
            Toast.makeText(getApplicationContext(), "Decrypting note", Toast.LENGTH_LONG)
                    .show();
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(), "You need to have password set to use this functionality", Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void showLegend(View view) {
        String a = Html.fromHtml("<font color='#98d4bb'></font>", Build.VERSION.SDK_INT).toString();
        String b = Html.fromHtml("<font color='#FF7F27'></font>", Build.VERSION.SDK_INT).toString();
        AlertDialog.Builder showLegend = new AlertDialog.Builder(this);
        showLegend.setTitle("Establishments Legend");
        showLegend.setMessage(a + "\n" + b);
        showLegend.setMessage(
                TextUtils.concat(
                    Html.fromHtml("<font color='#98d4bb'>Distributors/Transporters</font>", Build.VERSION.SDK_INT),
                    "\n",
                        Html.fromHtml("<font color='#a9a9a9'>Farmers/growers</font>", Build.VERSION.SDK_INT),
                        "\n",
                        Html.fromHtml("<font color='#fffac8'>Hospitals/Childcare/Caring Premises</font>", Build.VERSION.SDK_INT),
                        "\n",
                        Html.fromHtml("<font color='#F5E2E4'>Hotel/bed & breakfast/guest house</font>", Build.VERSION.SDK_INT),
                        "\n",
                        Html.fromHtml("<font color='#EEBAB2'>Importers/Exporters</font>", Build.VERSION.SDK_INT),
                        "\n",
                        Html.fromHtml("<font color='#f58231'>Manufacturers/packers</font>", Build.VERSION.SDK_INT),
                        "\n",
                        Html.fromHtml("<font color='#F5F3E7'>Mobile caterer</font>", Build.VERSION.SDK_INT),
                        "\n",
                        Html.fromHtml("<font color='#42d4f4'>Other catering premises</font>", Build.VERSION.SDK_INT),
                        "\n",
                        Html.fromHtml("<font color='#CCD4BF'>Pub/bar/nightclub</font>", Build.VERSION.SDK_INT),
                        "\n",
                        Html.fromHtml("<font color='#bfef45'>Restaurant/Cafe/Canteen</font>", Build.VERSION.SDK_INT),
                        "\n",
                        Html.fromHtml("<font color='#fabebe'>Retailers - other</font>", Build.VERSION.SDK_INT),
                        "\n",
                        Html.fromHtml("<font color='#ffe119'>Retailers - supermarkets/hypermarkets</font>", Build.VERSION.SDK_INT),
                        "\n",
                        Html.fromHtml("<font color='#e6beff'>School/college/university</font>", Build.VERSION.SDK_INT),
                        "\n",
                        Html.fromHtml("<font color='#e5db9c'>Takeaway/sandwich shop</font>", Build.VERSION.SDK_INT)
                )
        );
        showLegend.setPositiveButton("OK", null);
        showLegend.show();
    }
}
