package com.bham.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewAllEstablishmentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_establishments);
        ListView allEstablishmentsListView = findViewById(R.id.allEstablishmentsListView);
        ArrayList<String> testing = new ArrayList<>();
        testing.add("Haha");
        testing.add("This");
        testing.add("Actually");
        testing.add("Works");
        allEstablishmentsListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item, testing));
    }
}
