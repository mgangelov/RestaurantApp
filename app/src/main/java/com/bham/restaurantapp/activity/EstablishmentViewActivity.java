package com.bham.restaurantapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bham.restaurantapp.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class EstablishmentViewActivity extends AppCompatActivity {
    private static final String TAG = "EstablishmentViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment_view);
        TextView establishmentTitleTextView =
                findViewById(R.id.establishmentTitleTextView);
        establishmentTitleTextView.setBackgroundColor(
                ContextCompat.getColor(
                        getApplicationContext(),
                        getResources().getIdentifier(
                                String.format(
                                        "btypeid%s",
                                        getIntent().getIntExtra(
                                                "businessTypeId",
                                                9999)
                                ),
                                "color",
                                getPackageName()
                        )
                )
        );
        establishmentTitleTextView.setText(
                getIntent().getStringExtra("businessName")
        );


        TextView establishmentRatingDateTextView =
                findViewById(R.id.establishmentRatingDateTextView);
        DateTimeFormatter ratingDateFormat =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.UK);
        LocalDate ratingDate = LocalDate.parse(
                getIntent().getStringExtra("ratingDate"),
                ratingDateFormat
        );
        establishmentRatingDateTextView.setText(ratingDate.toString());


        TextView establishmentAuthorityTextView =
                findViewById(R.id.establishmentAuthorityTextView);
        establishmentAuthorityTextView.setText(
                getIntent().getStringExtra("localAuthorityName")
        );


        TextView establishmentBusinessTypeTextView =
                findViewById(R.id.establishmentBusinessTypeTextView);
        establishmentBusinessTypeTextView.setText(
                getIntent().getStringExtra("businessType")
        );


        TextView establishmentAddressTextView =
                findViewById(R.id.establishmentAddressTextView);
        establishmentAddressTextView.setText(
                getIntent().getStringExtra("addressLine")
        );

        RatingBar establishmentRatingRatingBar =
                findViewById(R.id.establishmentRatingRatingBar);
        establishmentRatingRatingBar.setRating(
                getIntent().getIntExtra("ratingValue", 0)
        );
    }

    public void goBack(View view) {
        Log.i(TAG, "goBack: Activity finished");
        this.finish();
    }
}
