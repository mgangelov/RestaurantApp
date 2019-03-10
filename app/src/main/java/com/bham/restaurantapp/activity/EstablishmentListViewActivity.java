package com.bham.restaurantapp.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bham.restaurantapp.R;

public class EstablishmentListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment_list_view);
        Intent receiveSearchValueIntent = getIntent();
        TextView testTextView = findViewById(R.id.testTextView);
        testTextView.setText(String.valueOf(receiveSearchValueIntent.getStringExtra("searchValue")));
    }
}
