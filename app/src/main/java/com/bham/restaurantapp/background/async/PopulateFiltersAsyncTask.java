package com.bham.restaurantapp.background.async;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.bham.restaurantapp.model.db.FsaDatabase;

import java.lang.ref.WeakReference;

import androidx.room.Room;

import static android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER;

public class PopulateFiltersAsyncTask extends AsyncTask<Void, Void, Cursor> {
    private WeakReference<Context> applicationContext;
    private WeakReference<Spinner> businessTypesSpinner;

    public PopulateFiltersAsyncTask(
            Context applicationContext, Spinner businessTypesSpinner
    ) {
        this.applicationContext = new WeakReference<>(applicationContext);
        this.businessTypesSpinner = new WeakReference<>(businessTypesSpinner);
    }

    @Override
    protected Cursor doInBackground(Void... voids) {
        FsaDatabase db = Room.databaseBuilder(
                applicationContext.get(),
                FsaDatabase.class,
                "database")
                .build();
        return db.businessTypeDAO().getAllCursor();
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                applicationContext.get(),
                android.R.layout.simple_spinner_dropdown_item,
                cursor,
                new String[] { "business_type_name" },
                new int[] { android.R.id.text1 },
                FLAG_REGISTER_CONTENT_OBSERVER
        );
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );
        businessTypesSpinner.get().setAdapter(adapter);
    }
}
