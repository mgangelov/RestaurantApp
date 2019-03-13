package com.bham.restaurantapp.rest;

import com.bham.restaurantapp.model.postcodes.PostcodeResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PostcodeIoApiInterface {
    @GET("postcodes/{postcode}")
    Call<PostcodeResult> getInfoForPostcode(@Path("postcode") String postcode);
}
