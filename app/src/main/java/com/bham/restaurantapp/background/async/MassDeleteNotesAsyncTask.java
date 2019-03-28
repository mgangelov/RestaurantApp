package com.bham.restaurantapp.background.async;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.bham.restaurantapp.App;
import com.bham.restaurantapp.model.db.FsaDatabase;


public class MassDeleteNotesAsyncTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "MassDeleteNotesAsyncTask";
    private FsaDatabase db;
    private SharedPreferences sharedPreferences;

    public MassDeleteNotesAsyncTask(Activity noteActivity) {
        this.db = App.getInstance().getDb();
        this.sharedPreferences =
                noteActivity
                        .getApplicationContext()
                        .getSharedPreferences("MyPref", Context.MODE_PRIVATE);
    }

    @Override
    protected Void doInBackground(Void... integers) {
        db.encryptedMessageDAO().deleteAllEncryptedMessageEntities();
        return null;
    }

    @Override
    protected void onPostExecute(Void s) {
        Log.i(TAG, "onPostExecute: Received stirng is: " + s);
        super.onPostExecute(s);
    }
}
