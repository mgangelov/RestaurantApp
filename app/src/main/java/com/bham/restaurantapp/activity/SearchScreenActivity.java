package com.bham.restaurantapp.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bham.restaurantapp.R;

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
}
