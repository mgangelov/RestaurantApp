package com.bham.restaurantapp.background.async;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.bham.restaurantapp.Globals;
import com.bham.restaurantapp.R;
import com.bham.restaurantapp.model.db.FsaDatabase;
import com.bham.restaurantapp.model.db.entities.EstablishmentEntity;
import com.google.android.material.button.MaterialButton;

import java.lang.ref.WeakReference;

import androidx.room.Room;

public class AddFavouriteEstablishmentAsyncTask extends AsyncTask<EstablishmentEntity, Void, Void> {
    private static final String TAG = "AddFavouriteEstablishmentAsyncTask";
    private WeakReference<Context> applicationContext;
    private WeakReference<MaterialButton> addToFavouritesMaterialButton;
    private FsaDatabase db;
    private Globals.MODES mode;
    private AlertDialog.Builder successAlert;
    private boolean isFavourite = false;

    public AddFavouriteEstablishmentAsyncTask(
            Context applicationContext,
            MaterialButton addToFavouritesMaterialButton,
            Globals.MODES mode,
            AlertDialog.Builder successAlert) {
        this.applicationContext = new WeakReference<>(applicationContext);
        this.db = Room.databaseBuilder(
                applicationContext,
                FsaDatabase.class,
                "database")
                .build();
        this.addToFavouritesMaterialButton = new WeakReference<>(addToFavouritesMaterialButton);
        this.mode = mode;
        this.successAlert = successAlert;
    }

    public AddFavouriteEstablishmentAsyncTask(
            Context applicationContext,
            MaterialButton addToFavouritesMaterialButton,
            Globals.MODES mode) {
        this.applicationContext = new WeakReference<>(applicationContext);
        this.db = Room.databaseBuilder(
                applicationContext,
                FsaDatabase.class,
                "database")
                .build();
        this.addToFavouritesMaterialButton = new WeakReference<>(addToFavouritesMaterialButton);
        this.mode = mode;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        // TODO: Add loading
    }

    @Override
    protected Void doInBackground(EstablishmentEntity... establishmentEntities) {
        if (mode == Globals.MODES.ADD_MODE)
            db.establishmentDAO().insertEstablishmentEntity(establishmentEntities[0]);
        if (mode == Globals.MODES.REMOVE_MODE)
            db.establishmentDAO().deleteEstablishmentEntity(establishmentEntities[0]);
        if (mode == Globals.MODES.CHECK_MODE)
            isFavourite = db.establishmentDAO().findEstablishmentById(establishmentEntities[0]._id)
                    != null;
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (mode == Globals.MODES.ADD_MODE && successAlert != null) {
            successAlert
                    .setMessage("Establishment added to favourites.")
                    .show();
            addToFavouritesMaterialButton.get().setText(
                    applicationContext.get().getString(R.string.remove_favourite_establishment)
            );
        }
        if (mode == Globals.MODES.REMOVE_MODE && successAlert != null) {
            successAlert
                    .setMessage("Establishment removed from favourites.")
                    .show();
            addToFavouritesMaterialButton.get().setText(
                    applicationContext.get().getString(R.string.add_to_favourites_button)
            );
        }
        if (mode == Globals.MODES.CHECK_MODE) {
            if (isFavourite) {
                Log.i(TAG, "doInBackground: Establishment is favourite");
                addToFavouritesMaterialButton.get().setText(
                        applicationContext.get().
                                getString(R.string.remove_favourite_establishment)
                );
            } else
                addToFavouritesMaterialButton.get().setText(
                        applicationContext.get()
                                .getString(R.string.add_to_favourites_button)
                );
        }
    }
}
