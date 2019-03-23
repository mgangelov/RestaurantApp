package com.bham.restaurantapp.rest;

import com.bham.restaurantapp.model.fsa.AuthorityResult;
import com.bham.restaurantapp.model.fsa.BusinessTypeResult;
import com.bham.restaurantapp.model.fsa.EstablishmentResult;
import com.bham.restaurantapp.model.fsa.RegionResult;
import com.bham.restaurantapp.model.fsa.SortOptionResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FsaApiInterface {
    @GET("regions")
    Call<RegionResult> getRegions();

    @GET("establishments?countryId=1")
    Call<EstablishmentResult> getEstablishments(
            @Query("pagenumber") int pageNumber,
            @Query("pagesize") int pageSize,
            @Query("sortoptionkey") String sortOptionKey
    );

    @GET("establishments?schemetypeid=1")
    Call<EstablishmentResult> getEstablishments(
            @Query("address") String address,
            @Query("pagenumber") int pageNumber,
            @Query("pagesize") int pageSize,
            @Query("sortoptionkey") String sortOptionKey
    );


    @GET("establishments/?schemetypeid=1")
    Call<EstablishmentResult> getEstablishments(
            @Query("longitude") String longitude,
            @Query("latitude") String latitude,
            @Query("pagenumber") int pageNumber,
            @Query("pagesize") int pageSize,
            @Query("maxdistancelimit") float maxDistanceLimit,
            @Query("businesstypeid") int businessTypeId,
            @Query("localauthorityid") int localAuthorityId,
            @Query("sortoptionkey") String sortOptionKey
    );

    @GET("establishments/?schemetypeid=1")
    Call<EstablishmentResult> getEstablishments(
            @Query("longitude") String longitude,
            @Query("latitude") String latitude,
            @Query("pagenumber") int pageNumber,
            @Query("pagesize") int pageSize,
            @Query("maxdistancelimit") float maxDistanceLimit,
            @Query("businesstypeid") int businessTypeId,
            @Query("sortoptionkey") String sortOptionKey
    );

    @GET("establishments/?schemetypeid=1")
    Call<EstablishmentResult> getEstablishments(
            @Query("longitude") String longitude,
            @Query("latitude") String latitude,
            @Query("pagenumber") int pageNumber,
            @Query("pagesize") int pageSize,
            @Query("maxdistancelimit") float maxDistanceLimit,
            @Query("sortoptionkey") String sortOptionKey
    );

    @GET("establishments/?schemetypeid=1")
    Call<EstablishmentResult> getEstablishments(
            @Query("address") String address,
            @Query("pagenumber") int pageNumber,
            @Query("pagesize") int pageSize,
            @Query("maxdistancelimit") float maxDistanceLimit,
            @Query("businesstypeid") int businessTypeId,
            @Query("localauthorityid") int localAuthorityId,
            @Query("sortoptionkey") String sortOptionKey
    );

    @GET("establishments/?schemetypeid=1")
    Call<EstablishmentResult> getEstablishments(
            @Query("address") String address,
            @Query("pagenumber") int pageNumber,
            @Query("pagesize") int pageSize,
            @Query("maxdistancelimit") float maxDistanceLimit,
            @Query("businesstypeid") int businessTypeId,
            @Query("sortoptionkey") String sortOptionKey
    );

    @GET("establishments/?schemetypeid=1")
    Call<EstablishmentResult> getEstablishments(
            @Query("address") String address,
            @Query("pagenumber") int pageNumber,
            @Query("pagesize") int pageSize,
            @Query("maxdistancelimit") float maxDistanceLimit,
            @Query("sortoptionkey") String sortOptionKey
    );

    @GET("businesstypes/basic")
    Call<BusinessTypeResult> getBusinessTypes();

    @GET("authorities")
    Call<AuthorityResult> getAuthorities();

    @GET("sortoptions")
    Call<SortOptionResult> getSortOptions();
}
