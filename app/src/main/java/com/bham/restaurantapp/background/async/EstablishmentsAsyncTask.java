package com.bham.restaurantapp.background.async;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.bham.restaurantapp.App;
import com.bham.restaurantapp.adapter.EstablishmentAdapter;
import com.bham.restaurantapp.background.controller.FsaDataController;
import com.bham.restaurantapp.model.fsa.Establishment;
import com.bham.restaurantapp.model.fsa.EstablishmentResult;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


public class EstablishmentsAsyncTask extends AsyncTask<String, Void, EstablishmentResult> {
    private static final String TAG = "EstablishmentsAsyncTask";
    private WeakReference<RecyclerView> rView;
    private WeakReference<TextView> pageNumberTextView;
    private List<Boolean> isFavourites;
    private AlertDialog.Builder noResultsAlert;
    private WeakReference<Activity> parentActivity;

    public EstablishmentsAsyncTask(
            RecyclerView rView,
            TextView pageNumberTextView
    ) {
        this.rView = new WeakReference<>(rView);
        this.pageNumberTextView = new WeakReference<>(pageNumberTextView);
        this.isFavourites = new ArrayList<>();
    }

    public EstablishmentsAsyncTask(
            RecyclerView rView,
            TextView pageNumberTextView,
            AlertDialog.Builder noResultsAlert,
            Activity parentActivity
    ) {
        this.rView = new WeakReference<>(rView);
        this.pageNumberTextView = new WeakReference<>(pageNumberTextView);
        this.isFavourites = new ArrayList<>();
        this.noResultsAlert = noResultsAlert;
        this.parentActivity = new WeakReference<>(parentActivity);
    }

    @Override
    protected EstablishmentResult doInBackground(String... strings) {
        List<Establishment> establishmentsFromApi;
        FsaDataController fsaAPI = new FsaDataController();
        try {
            EstablishmentResult apiResponse = null;
            if (strings.length == 3) { // ViewAllEstablishments
                apiResponse = fsaAPI.getEstablishments(
                        Integer.valueOf(strings[0]), // pageNumber
                        Integer.valueOf(strings[1]), // pageSize
                        strings[2] // sortOptionKey
                );
            } else if (strings.length == 8) {
                Log.i(TAG, "doInBackground: length 8 string values: " + Arrays.toString(strings));
                apiResponse = fsaAPI.getEstablishments(
                        strings[0], // searchValue
                        Integer.valueOf(strings[1]), // businessType
                        Integer.valueOf(strings[2]), // region
                        Integer.valueOf(strings[3]), // authority
                        Integer.valueOf(strings[4]), // pageNumber
                        Integer.valueOf(strings[5]), // pageSize
                        strings[6], // sortOptionKey
                        Integer.valueOf(strings[7]) // ratingKey
                );
            }
            else if (strings.length == 10) {
                Log.i(TAG, "doInBackground: 10 values: " + Arrays.toString(strings));
                apiResponse = fsaAPI.getEstablishments(
                        strings[0], // Longitude
                        strings[1], // Latitude
                        Integer.valueOf(strings[2]), // businessType
                        Integer.valueOf(strings[3]), // region
                        Integer.valueOf(strings[4]), // authority
                        Integer.valueOf(strings[5]), // maxDistanceLimit
                        Integer.valueOf(strings[6]), // pageNumber
                        Integer.valueOf(strings[7]), // pageSize
                        strings[8], // sortOptionKey
                        Integer.valueOf(strings[9]) // ratingKey
                );

            }
            if (apiResponse != null) {
                establishmentsFromApi = apiResponse.getEstablishments();
                for (Establishment e : establishmentsFromApi) {
                    if (App.getInstance().getDb()
                            .establishmentDAO()
                            .findEstablishmentById(
                                    Integer.parseInt(e.getFhrsId())) != null
                    )
                        isFavourites.add(Boolean.TRUE);
                    else
                        isFavourites.add(Boolean.FALSE);
                }
            }

            return apiResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

//    @Override
//    protected void onProgressUpdate() {
//        super.onProgressUpdate();
//        // TODO: Add loading
//    }


    @Override
    protected void onPostExecute(EstablishmentResult establishments) {
        super.onPostExecute(establishments);
        EstablishmentAdapter establishmentAdapter = new EstablishmentAdapter(
                establishments.getEstablishments(),
                isFavourites
        );
        if (establishments.getMeta().getItemCount() != 0) {
            this.rView.get().setAdapter(establishmentAdapter);

            String pageNumber = String.valueOf(establishments.getMeta().getPageNumber());
            String totalPages = String.valueOf(establishments.getMeta().getTotalPages());
            this.pageNumberTextView.get().setText(String.format("%s/%s", pageNumber, totalPages));

        } else {
            if (noResultsAlert != null) {
                noResultsAlert
                        .setMessage("No results found")
                        .setOnDismissListener(dialog -> parentActivity.get().finish())
                        .show();
            }
        }
    }
}
