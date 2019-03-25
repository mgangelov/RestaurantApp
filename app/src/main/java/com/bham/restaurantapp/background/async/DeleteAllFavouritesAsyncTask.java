package com.bham.restaurantapp.background.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.bham.restaurantapp.model.db.FsaDatabase;

import java.lang.ref.WeakReference;

import androidx.appcompat.app.AlertDialog;
import androidx.room.Room;

public class DeleteAllFavouritesAsyncTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "DeleteAllFavouritesAsyncTask";
    private WeakReference<Context> applicationContext;
    private AlertDialog.Builder successAlert;

    public DeleteAllFavouritesAsyncTask(
            Context applicationContext,
            AlertDialog.Builder successAlert
    ) {
        this.applicationContext = new WeakReference<>(applicationContext);
        this.successAlert = successAlert;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        Log.i(TAG, "doInBackground: Deleting all favourites");
        Room.databaseBuilder(
                applicationContext.get(),
                FsaDatabase.class,
                "database"
        ).build().establishmentDAO().deleteAllEstablishmentEntities();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        successAlert.show();
    }
}
