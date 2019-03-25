package com.bham.restaurantapp.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.bham.restaurantapp.R;
import com.bham.restaurantapp.background.async.ViewFavouritesAsyncTask;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavouritesListViewActivity extends AppCompatActivity {
    private RecyclerView rView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_establishments_list);
        TextView establishmentsTitleTextView =
                findViewById(R.id.establishmentsTitleTextView);
        establishmentsTitleTextView.setText(
                getString(R.string.view_favourites_title)
        );
        establishmentsTitleTextView.setBackgroundColor(
                ContextCompat.getColor(this, R.color.pinkFavourite)
        );
        rView = findViewById(R.id.testRecyclerView);
        rView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rView.setLayoutManager(lm);

        new ViewFavouritesAsyncTask(
                getApplicationContext(),
                rView
        ).execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new ViewFavouritesAsyncTask(
                getApplicationContext(),
                rView
        ).execute();
    }
}
