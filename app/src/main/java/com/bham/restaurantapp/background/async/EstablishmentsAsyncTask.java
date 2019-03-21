package com.bham.restaurantapp.background.async;

import android.os.AsyncTask;
import android.widget.TextView;

import com.bham.restaurantapp.adapter.EstablishmentAdapter;
import com.bham.restaurantapp.background.controller.FsaDataController;
import com.bham.restaurantapp.model.fsa.EstablishmentResult;

import java.io.IOException;
import java.lang.ref.WeakReference;

import androidx.recyclerview.widget.RecyclerView;


public class EstablishmentsAsyncTask extends AsyncTask<String, Void, EstablishmentResult> {
    private WeakReference<RecyclerView> rView;
    private WeakReference<TextView> pageNumberTextView;

    public EstablishmentsAsyncTask(RecyclerView rView, TextView pageNumberTextView) {
        this.rView = new WeakReference<>(rView);
        this.pageNumberTextView = new WeakReference<>(pageNumberTextView);
    }

    @Override
    protected EstablishmentResult doInBackground(String... strings) {
        FsaDataController fsaAPI = new FsaDataController();
        try {
            if (strings.length == 2)
                return fsaAPI.getEstablishments(
                        Integer.valueOf(strings[0]), // pageNumber
                        Integer.valueOf(strings[1]) // pageSize
                );
            else if (strings.length == 5)
                return fsaAPI.getEstablishments(
                        strings[0], // Longitude
                        strings[1], // Latitude
                        Float.valueOf(strings[2]), // maxDistance
                        Integer.valueOf(strings[3]), // pageNumber
                        Integer.valueOf(strings[4]) // pageSize
                );
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
        this.rView.get().setAdapter(new EstablishmentAdapter(establishments.getEstablishments()));
        String pageNumber = String.valueOf(establishments.getMeta().getPageNumber());
        String totalPages = String.valueOf(establishments.getMeta().getTotalPages());
        this.pageNumberTextView.get().setText(String.format("%s/%s", pageNumber, totalPages));
        // TODO: This is when I return the results
    }
}
