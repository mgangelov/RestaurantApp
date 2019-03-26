package com.bham.restaurantapp.background.async;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.bham.restaurantapp.App;
import com.bham.restaurantapp.model.db.FsaDatabase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER;

public class PopulateFiltersAsyncTask extends AsyncTask<Void, Void, List<Cursor>> {
    private static final String TAG = "PopulateFiltersAsyncTask";
    private WeakReference<Context> applicationContext;
    private WeakReference<Spinner> businessTypesSpinner;
    private WeakReference<Spinner> regionSpinner;
    private WeakReference<Spinner> authoritySpinner;
    private FsaDatabase db;
    private int existingBusinessTypePosition;
    private int existingRegionPosition;
    private int existingAuthorityPosition;

    public PopulateFiltersAsyncTask(
            Context applicationContext,
            Spinner businessTypesSpinner,
            Spinner regionSpinner,
            Spinner authoritySpinner,
            int existingBusinessTypePosition,
            int existingRegionPosition,
            int existingAuthorityPosition
    ) {
        this.applicationContext = new WeakReference<>(applicationContext);
        this.businessTypesSpinner = new WeakReference<>(businessTypesSpinner);
        this.regionSpinner = new WeakReference<>(regionSpinner);
        this.authoritySpinner = new WeakReference<>(authoritySpinner);
        this.existingBusinessTypePosition = existingBusinessTypePosition;
        this.existingRegionPosition = existingRegionPosition;
        this.existingAuthorityPosition = existingAuthorityPosition;
        this.db = App.getInstance().getDb();
    }

    @Override
    protected List<Cursor> doInBackground(Void... voids) {
        List<Cursor> cursors = new ArrayList<>();
        cursors.add(db.businessTypeDAO().getAllCursor());
        cursors.add(db.regionDAO().getAllCursor());
        cursors.add(db.authorityDAO().getAllFilteredCursor());
        return cursors;
    }

    @Override
    protected void onPostExecute(List<Cursor> cursor) {
        super.onPostExecute(cursor);

        SimpleCursorAdapter authoritiesAdapter = new SimpleCursorAdapter(
                applicationContext.get(),
                android.R.layout.simple_spinner_dropdown_item,
                cursor.get(2),
                new String[]{"authority_name"},
                new int[]{android.R.id.text1},
                FLAG_REGISTER_CONTENT_OBSERVER
        );
        authoritiesAdapter.setFilterQueryProvider(filterQueryProvider);
        authoritySpinner.get().setAdapter(authoritiesAdapter);


        businessTypesSpinner.get().setAdapter(new SimpleCursorAdapter(
                applicationContext.get(),
                android.R.layout.simple_spinner_dropdown_item,
                cursor.get(0),
                new String[]{"business_type_name"},
                new int[]{android.R.id.text1},
                FLAG_REGISTER_CONTENT_OBSERVER
        ));
        regionSpinner.get().setAdapter(new SimpleCursorAdapter(
                applicationContext.get(),
                android.R.layout.simple_spinner_dropdown_item,
                cursor.get(1),
                new String[]{"region_name"},
                new int[]{android.R.id.text1},
                FLAG_REGISTER_CONTENT_OBSERVER
        ));
        regionSpinner.get().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SimpleCursorAdapter authoritySpinnerAdapter = (SimpleCursorAdapter) authoritySpinner.get().getAdapter();
                Cursor selectedRegion = (Cursor) parent.getAdapter().getItem(position);
                int selectedRegionId = selectedRegion.getInt(selectedRegion.getColumnIndex("_id"));
                Log.i(TAG, String.format("Filtering authorities for region %s", selectedRegionId));
                if (selectedRegionId == 99) {
                    authoritySpinner.get().setEnabled(false);
                } else {
                    if (existingAuthorityPosition != 0)
                        authoritySpinner.get().setSelection(existingAuthorityPosition);
                    else {
                        authoritySpinner.get().setSelection(0);
                    }
                    authoritySpinnerAdapter.getFilter().filter(Integer.toString(selectedRegionId));
                    authoritySpinner.get().setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                authoritySpinner.get().setEnabled(false);

            }
        });

        businessTypesSpinner.get().setSelection(existingBusinessTypePosition);
        regionSpinner.get().setSelection(existingRegionPosition);
        authoritySpinner.get().setSelection(existingAuthorityPosition);
        authoritySpinner.get().setEnabled(false);
    }

    private FilterQueryProvider filterQueryProvider = constraint -> {
        if (Integer.parseInt(constraint.toString()) == 99) {
            return db.authorityDAO().getAllFilteredCursor();
        } else return db.authorityDAO().findAuthorityByRegionIdCursor(
                Integer.parseInt(constraint.toString()));
    };
}
