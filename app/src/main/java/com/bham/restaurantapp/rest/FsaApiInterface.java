package com.bham.restaurantapp.rest;

import com.bham.restaurantapp.model.EstablishmentResult;
import com.bham.restaurantapp.model.RegionResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FsaApiInterface {
    @GET("regions")
    Call<RegionResult> getRegions();

    @GET("establishments")
    Call<EstablishmentResult> getEstablishments(
            @Query("pagenumber") int pageNumber,
            @Query("pagesize") int pageSize
    );
}
