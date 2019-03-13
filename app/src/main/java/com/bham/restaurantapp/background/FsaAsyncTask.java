package com.bham.restaurantapp.background;

import android.os.AsyncTask;
import android.widget.TextView;

import com.bham.restaurantapp.adapter.EstablishmentAdapter;
import com.bham.restaurantapp.model.fsa.EstablishmentResult;

import java.io.IOException;
import java.lang.ref.WeakReference;

import androidx.recyclerview.widget.RecyclerView;


public class FsaAsyncTask extends AsyncTask<String, Void, EstablishmentResult> {
    private WeakReference<RecyclerView> rView;
    private WeakReference<TextView> pageNumberTextView;

    public FsaAsyncTask(WeakReference<RecyclerView> rView, WeakReference<TextView> pageNumberTextView) {
        this.rView = rView;
        this.pageNumberTextView = pageNumberTextView;
    }

    @Override
    protected EstablishmentResult doInBackground(String... strings) {
        FsaDataController fsaAPI = new FsaDataController();
        try {
            return fsaAPI.connectAndGetApiData(Integer.valueOf(strings[0]), Integer.valueOf(strings[1]));
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
        this.pageNumberTextView.get().setText(String.valueOf(establishments.getMeta().getPageNumber()));
        // TODO: This is when I return the results
    }
}
