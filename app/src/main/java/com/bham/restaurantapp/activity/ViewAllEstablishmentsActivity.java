package com.bham.restaurantapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bham.restaurantapp.R;
import com.bham.restaurantapp.background.async.EstablishmentsAsyncTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.bham.restaurantapp.Globals.DEFAULT_MIN_RATING;
import static com.bham.restaurantapp.Globals.DEFAULT_PAGE_NUMBER;
import static com.bham.restaurantapp.Globals.DEFAULT_PAGE_SIZE;

public class ViewAllEstablishmentsActivity extends AppCompatActivity {
    private static final String TAG = "ViewAllEstablishmentsActivity";
    private RecyclerView rView;
    private TextView pageNumberTextView;
    private int pageNumber;
    private int pageSize;
    private String sortOptionKey;
    private int ratingKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_establishments_list);
        if (savedInstanceState != null) {
            this.pageNumber = savedInstanceState.getInt("pageNumber");
            this.pageSize = savedInstanceState.getInt("pageSize");
        } else {
            this.pageNumber = DEFAULT_PAGE_NUMBER;
            this.pageSize = DEFAULT_PAGE_SIZE;
        }
        TextView establishmentsTitleTextView = findViewById(R.id.establishmentsTitleTextView);
        establishmentsTitleTextView.setText(getString(R.string.all_establishments_text_view_text));
        establishmentsTitleTextView.setBackgroundColor(
                ContextCompat.getColor(this, android.R.color.holo_blue_bright)
        );
        ratingKey = getIntent().getIntExtra("minRating", DEFAULT_MIN_RATING);
        sortOptionKey = getIntent().getStringExtra("sortOptionKey");
        pageNumberTextView = findViewById(R.id.pageNumberTextView);
        rView = findViewById(R.id.testRecyclerView);
        rView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rView.setLayoutManager(lm);
        new EstablishmentsAsyncTask(
                getApplicationContext(),
                this.rView,
                this.pageNumberTextView
        ).execute(
                String.valueOf(pageNumber),
                String.valueOf(pageSize),
                sortOptionKey
        );
    }

    public void onGoToPreviousPage(View view) {
        this.pageNumber -= 1;
        if (this.pageNumber != 0) {
            getPageResults();
        } else
            this.pageNumber += 1;

    }

    public void onGoToNextPage(View view) {
        this.pageNumber += 1;
        getPageResults();
    }

    public void getPageResults() {
        new EstablishmentsAsyncTask(
                getApplicationContext(),
                this.rView,
                this.pageNumberTextView
        )
                .execute(
                        String.valueOf(this.pageNumber),
                        String.valueOf(this.pageSize),
                        sortOptionKey
                );
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("pageNumber", pageNumber);
        outState.putInt("pageSize", pageSize);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPageResults();
    }
}
