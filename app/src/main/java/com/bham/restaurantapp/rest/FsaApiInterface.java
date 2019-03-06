package com.bham.restaurantapp.rest;

import com.bham.restaurantapp.model.RegionResult;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FsaApiInterface {
    @GET("regions")
    Call<RegionResult> getRegions();
}
