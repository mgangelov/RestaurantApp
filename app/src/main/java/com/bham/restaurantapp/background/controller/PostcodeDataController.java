package com.bham.restaurantapp.background.controller;

import com.bham.restaurantapp.model.postcodes.Coordinate;
import com.bham.restaurantapp.model.postcodes.PostcodeResult;
import com.bham.restaurantapp.rest.PostcodeIoApiInterface;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostcodeDataController {
    private Retrofit retrofit;
    private final String BASE_URL = "http://api.postcodes.io/";

    public PostcodeDataController() {
        this.retrofit = null;
    }

    public Coordinate convertPostcodeToCoordinates(String postcode) throws IOException {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
        }
        PostcodeIoApiInterface postcodeAPI = retrofit.create(PostcodeIoApiInterface.class);
        Call<PostcodeResult> call = postcodeAPI.getInfoForPostcode(postcode);
        return Objects.requireNonNull(call.execute().body()).getResult().getCoordinate();
    }
}
