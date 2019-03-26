package com.bham.restaurantapp.background.async;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.bham.restaurantapp.App;
import com.bham.restaurantapp.model.db.FsaDatabase;

import java.lang.ref.WeakReference;

import static android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER;


public class SearchScreenAsyncTask extends AsyncTask<Void, Void, Cursor> {
    private WeakReference<Context> applicationContext;
    private WeakReference<Spinner> sortBySpinner;
    private boolean isMaxDistanceLimitSet = false;

    public SearchScreenAsyncTask(
            Context applicationContext,
            Spinner sortBySpinner,
            float maxDistanceLimit
    ) {
        this.applicationContext = new WeakReference<>(applicationContext);
        this.sortBySpinner = new WeakReference<>(sortBySpinner);
        if (maxDistanceLimit != 3)
            isMaxDistanceLimitSet = true;
    }


    @Override
    protected Cursor doInBackground(Void... voids) {
        FsaDatabase db = App.getInstance().getDb();

        return db.sortOptionsDAO().getAllCursor();
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        sortBySpinner.get().setAdapter(new SimpleCursorAdapter(
                applicationContext.get(),
                android.R.layout.simple_spinner_dropdown_item,
                cursor,
                new String[]{"sort_option_name"},
                new int[]{android.R.id.text1},
                FLAG_REGISTER_CONTENT_OBSERVER
        ));
        if (!isMaxDistanceLimitSet)
            sortBySpinner.get().setSelection(3);
    }
}
