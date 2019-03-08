package com.bham.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewAllEstablishmentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_establishments);
//        ListView allEstablishmentsListView = findViewById(R.id.allEstablishmentsListView);
        RecyclerView rView = findViewById(R.id.testRecyclerView);
        rView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rView.setLayoutManager(lm);
        // TODO: Implement RecyclerView to show all establishments
    }
}
