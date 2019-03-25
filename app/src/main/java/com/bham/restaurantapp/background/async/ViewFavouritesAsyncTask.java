package com.bham.restaurantapp.background.async;

import android.content.Context;
import android.os.AsyncTask;

import com.bham.restaurantapp.adapter.EstablishmentAdapter;
import com.bham.restaurantapp.model.db.FsaDatabase;
import com.bham.restaurantapp.model.fsa.Establishment;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.stream.Collectors;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

public class ViewFavouritesAsyncTask extends AsyncTask<Void, Void, List<Establishment>> {
    private WeakReference<Context> applicationContext;
    private WeakReference<RecyclerView> rView;
    private FsaDatabase db;

    public ViewFavouritesAsyncTask(Context applicationContext, RecyclerView rView) {
        this.applicationContext = new WeakReference<>(applicationContext);
        this.rView = new WeakReference<>(rView);
        this.db = Room.databaseBuilder(
                applicationContext,
                FsaDatabase.class,
                "database")
                .build();
    }

    @Override
    protected List<Establishment> doInBackground(Void... voids) {
        return db.establishmentDAO().getAll()
                .stream()
                .map(Establishment::fromEstablishmentEntity)
                .collect(Collectors.toList());
    }

    @Override
    protected void onPostExecute(List<Establishment> establishments) {
        super.onPostExecute(establishments);
        rView.get().setAdapter(new EstablishmentAdapter(establishments));
    }
}
