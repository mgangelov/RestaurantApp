package com.bham.restaurantapp.background.async;

import android.os.AsyncTask;

import com.bham.restaurantapp.background.controller.PostcodeDataController;
import com.bham.restaurantapp.model.postcodes.Coordinate;

import java.io.IOException;

public class PostcodeAsyncTask extends AsyncTask<String, Void, Coordinate> {
    private AsyncResponseInterface<Coordinate> response;

    public PostcodeAsyncTask(
            AsyncResponseInterface<Coordinate> responseHandler
    ) {
        this.response = responseHandler;
    }

    @Override
    protected Coordinate doInBackground(String... strings) {
        PostcodeDataController postcodeDataController = new PostcodeDataController();
        try {
            return postcodeDataController.convertPostcodeToCoordinates(strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // TODO: Add loading if necessary
    }

    @Override
    protected void onPostExecute(Coordinate coordinate) {
        super.onPostExecute(coordinate);
        if (coordinate != null) {
            System.out.printf(
                    "Latitude : %s\nLongitude: %s%n",
                    coordinate.latitude,
                    coordinate.longitude
            );
            response.processResponse(coordinate);
        }
    }
}
