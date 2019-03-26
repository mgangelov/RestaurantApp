package com.bham.restaurantapp.background.async;

import android.os.AsyncTask;
import android.util.Log;

import com.bham.restaurantapp.App;

import androidx.appcompat.app.AlertDialog;

public class DeleteAllFavouritesAsyncTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "DeleteAllFavouritesAsyncTask";
    private AlertDialog.Builder successAlert;

    public DeleteAllFavouritesAsyncTask(
            AlertDialog.Builder successAlert
    ) {
        this.successAlert = successAlert;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        Log.i(TAG, "doInBackground: Deleting all favourites");
        App.getInstance().getDb()
                .establishmentDAO()
                .deleteAllEstablishmentEntities();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        successAlert.show();
    }
}
