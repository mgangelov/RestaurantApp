package com.bham.restaurantapp.background.async;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import com.bham.restaurantapp.model.postcodes.Coordinate;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class LocationAsyncTask extends AsyncTask<Void, Void, Coordinate> {
    private LocationManager locManager;
    private LocationListener locListener;
    private double longitude;
    private double latitude;
    private boolean isDone;

    public LocationAsyncTask(Context applicationContext) {
        locManager = (LocationManager)
                applicationContext.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                Log.i(TAG, "onLocationChanged: longi " + longitude);
                Log.i(TAG, "onLocationChanged: latit " + latitude);
                Log.i(TAG, "onLocationChanged: entered");
                isDone = true;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }

    @SuppressLint("MissingPermission")
    @Override
    protected Coordinate doInBackground(Void... voids) {
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener, Looper.getMainLooper());
        while (!isDone) {
        }
        Log.i(TAG, "doInBackground: langfjfjfj" + longitude);
        Log.i(TAG, "doInBackground: latfjfjfj" + latitude);
        locManager.removeUpdates(locListener);
        return null;
    }

    @Override
    protected void onPostExecute(Coordinate aVoid) {
        super.onPostExecute(aVoid);
        Log.i(TAG, "onPostExecute: longg " + longitude);
        Log.i(TAG, "onPostExecute: latii " + latitude);

//        Log.i(TAG, "onPostExecute: longitude" + aVoid.longitude);
//        Log.i(TAG, "onPostExecute: latitude" + aVoid.latitude);
    }

//    private void attachLocManager() {
//        try {
//            Log.i(TAG, "attachLocManager: getting single update");
//        } catch (SecurityException exp) {
//            Log.wtf("exp", "wtf");
//        }
//    }
}
