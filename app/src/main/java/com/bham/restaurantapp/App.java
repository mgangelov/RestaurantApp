package com.bham.restaurantapp;

import android.app.Application;

import com.bham.restaurantapp.model.db.FsaDatabase;

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
    }

    public FsaDatabase getDb() {
        return db;
    }

    public static App getInstance() {
        return INSTANCE;
    }
}
