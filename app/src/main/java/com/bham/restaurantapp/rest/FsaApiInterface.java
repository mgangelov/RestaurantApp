package com.bham.restaurantapp.rest;

import com.bham.restaurantapp.model.fsa.AuthorityResult;
import com.bham.restaurantapp.model.fsa.BusinessTypeResult;
import com.bham.restaurantapp.model.fsa.EstablishmentResult;
import com.bham.restaurantapp.model.fsa.RegionResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FsaApiInterface {
    @GET("regions")
    Call<RegionResult> getRegions();

    @GET("establishments?countryId=1")
    Call<EstablishmentResult> getEstablishments(
            @Query("pagenumber") int pageNumber,
            @Query("pagesize") int pageSize
    );

    @GET("establishments?countryId=1")
    Call<EstablishmentResult> getEstablishments(
            @Query("address") String address,
            @Query("pagenumber") int pageNumber,
            @Query("pagesize") int pageSize
    );


    @GET("establishments/?countryid=1")
    Call<EstablishmentResult> getEstablishments(
            @Query("longitude") String longitude,
            @Query("latitude") String latitude,
            @Query("pagenumber") int pageNumber,
            @Query("pagesize") int pageSize,
            @Query("maxdistancelimit") float maxDistanceLimit,
            @Query("businesstypeid") int businessTypeId,
            @Query("localauthorityid") int localAuthorityId
    );

    @GET("establishments/?countryid=1")
    Call<EstablishmentResult> getEstablishments(
            @Query("longitude") String longitude,
            @Query("latitude") String latitude,
            @Query("pagenumber") int pageNumber,
            @Query("pagesize") int pageSize,
            @Query("maxdistancelimit") float maxDistanceLimit,
            @Query("businesstypeid") int businessTypeId
    );

    @GET("establishments/?countryid=1")
    Call<EstablishmentResult> getEstablishments(
            @Query("longitude") String longitude,
            @Query("latitude") String latitude,
            @Query("pagenumber") int pageNumber,
            @Query("pagesize") int pageSize,
            @Query("maxdistancelimit") float maxDistanceLimit
    );

    @GET("establishments/?countryid=1")
    Call<EstablishmentResult> getEstablishments(
            @Query("address") String address,
            @Query("pagenumber") int pageNumber,
            @Query("pagesize") int pageSize,
            @Query("maxdistancelimit") float maxDistanceLimit,
            @Query("businesstypeid") int businessTypeId,
            @Query("localauthorityid") int localAuthorityId
    );

    @GET("establishments/?countryid=1")
    Call<EstablishmentResult> getEstablishments(
            @Query("address") String address,
            @Query("pagenumber") int pageNumber,
            @Query("pagesize") int pageSize,
            @Query("maxdistancelimit") float maxDistanceLimit,
            @Query("businesstypeid") int businessTypeId
    );

    @GET("establishments/?countryid=1")
    Call<EstablishmentResult> getEstablishments(
            @Query("address") String address,
            @Query("pagenumber") int pageNumber,
            @Query("pagesize") int pageSize,
            @Query("maxdistancelimit") float maxDistanceLimit
    );

    @GET("businesstypes/basic")
    Call<BusinessTypeResult> getBusinessTypes();

    @GET("authorities")
    Call<AuthorityResult> getAuthorities();
}
