package com.bham.restaurantapp.background.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.bham.restaurantapp.background.controller.FsaDataController;
import com.bham.restaurantapp.model.db.FsaDatabase;
import com.bham.restaurantapp.model.db.entities.BusinessTypeEntity;
import com.bham.restaurantapp.model.fsa.BusinessType;
import com.bham.restaurantapp.model.fsa.BusinessTypeResult;

import java.io.IOException;
import java.lang.ref.WeakReference;

import androidx.appcompat.app.AlertDialog;
import androidx.room.Room;

public class RefreshDbAsyncTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "RefreshDbAsyncTask";
    private WeakReference<Context> applicationContext;
    private AlertDialog.Builder successAlert;

    public RefreshDbAsyncTask(Context applicationContext, AlertDialog.Builder successAlert) {
        this.applicationContext = new WeakReference<>(applicationContext);
        this.successAlert = successAlert;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Log.i(TAG, "Deleting all DB entries");
            FsaDatabase db = Room.databaseBuilder(
                    applicationContext.get(),
                    FsaDatabase.class,
                    "database")
                    .build();
            db.businessTypeDAO().deleteAllBusinessTypeEntities();
            Log.i(TAG, "Adding businessTypes to DB");
            BusinessTypeResult bts = new FsaDataController().getBusinessTypes();
            for (BusinessType bt : bts.getBusinessTypes()) {
                BusinessTypeEntity bte = new BusinessTypeEntity();
                bte.btId = bt.getbTypeId();
                bte.btName = bt.getbTypeName();
                db.businessTypeDAO().insertBusinessTypeEntity(bte);
                Log.i(TAG, String.format("Inserted %s", bt.getbTypeName()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        successAlert.show();
    }
}
