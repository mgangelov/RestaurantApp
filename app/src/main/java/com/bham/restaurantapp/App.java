package com.bham.restaurantapp;

import android.app.Application;

import com.bham.restaurantapp.background.async.RefreshDbAsyncTask;
import com.bham.restaurantapp.model.db.FsaDatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.room.Room;

public class App extends Application {
    public static App INSTANCE;
    private FsaDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        this.db = Room.databaseBuilder(
                getApplicationContext(),
                FsaDatabase.class,
                "database")
                .build();
        INSTANCE = this;
        final AlertDialog.Builder successAlert = new AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage("DB refreshed")
                .setPositiveButton(android.R.string.yes, null);
        new RefreshDbAsyncTask(successAlert)
                .execute();
    }

    public FsaDatabase getDb() {
        return db;
    }

    public static App getInstance() {
        return INSTANCE;
    }
}
