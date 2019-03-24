package com.bham.restaurantapp.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.bham.restaurantapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class EstablishmentView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment_view);
        String test = getIntent().getStringExtra("id");
        TextView tv = findViewById(R.id.testTextView);
        if (test != null) {
            tv.setText(test);
        } else
            tv.setText("nope");
    }
}
