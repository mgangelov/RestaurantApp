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
// 3
    @GET("establishments?schemetypekey=FHRS&ratingOperatorKey=GreaterThanOrEqual")
    Call<EstablishmentResult> getEstablishments(
            @Query("pagenumber") int pageNumber,
            @Query("pagesize") int pageSize,
            @Query("sortoptionkey") String sortOptionKey
    );

    // Specific authority
    @GET("establishments?schemetypekey=FHRS&ratingOperatorKey=GreaterThanOrEqual")
    Call<EstablishmentResult> getEstablishmentsWithBusinessType(
            @Query("longitude") String longitude,
            @Query("latitude") String latitude,
            @Query("pagenumber") int pageNumber,
            @Query("pagesize") int pageSize,
            @Query("maxdistancelimit") int maxDistanceLimit,
            @Query("businesstypeid") int businessTypeId,
            @Query("localauthorityid") int localAuthorityId,
            @Query("sortoptionkey") String sortOptionKey,
            @Query("ratingkey") int ratingKey
    );

    // specific authority
    @GET("establishments?schemetypekey=FHRS&ratingOperatorKey=GreaterThanOrEqual")
    Call<EstablishmentResult> getEstablishments(
            @Query("longitude") String longitude,
            @Query("latitude") String latitude,
            @Query("pagenumber") int pageNumber,
            @Query("pagesize") int pageSize,
            @Query("maxdistancelimit") int maxDistanceLimit,
            @Query("localauthorityid") int localAuthorityId,
            @Query("sortoptionkey") String sortOptionKey,
            @Query("ratingkey") int ratingKey
    );

    // All regions
    @GET("establishments?schemetypekey=FHRS&ratingOperatorKey=GreaterThanOrEqual")
    Call<EstablishmentResult> getEstablishmentsWithBusinessType(
            @Query("longitude") String longitude,
            @Query("latitude") String latitude,
            @Query("pagenumber") int pageNumber,
            @Query("pagesize") int pageSize,
            @Query("maxdistancelimit") int maxDistanceLimit,
            @Query("businesstypeid") int businessTypeId,
            @Query("sortoptionkey") String sortOptionKey,
            @Query("ratingkey") int ratingKey
    );

    // All regions
    @GET("establishments?schemetypekey=FHRS&ratingOperatorKey=GreaterThanOrEqual")
    Call<EstablishmentResult> getEstablishments(
            @Query("longitude") String longitude,
            @Query("latitude") String latitude,
            @Query("pagenumber") int pageNumber,
            @Query("pagesize") int pageSize,
            @Query("maxdistancelimit") int maxDistanceLimit,
            @Query("sortoptionkey") String sortOptionKey,
            @Query("ratingkey") int ratingKey
    );


    // specific authority
    @GET("establishments?schemetypekey=FHRS&ratingOperatorKey=GreaterThanOrEqual")
    Call<EstablishmentResult> getEstablishments(
            @Query("address") String address,
            @Query("pagenumber") int pageNumber,
            @Query("pagesize") int pageSize,
            @Query("maxdistancelimit") int maxDistanceLimit,
            @Query("localauthorityid") int localAuthorityId,
            @Query("sortoptionkey") String sortOptionKey,
            @Query("ratingkey") int ratingKey
    );

    // All regions
    @GET("establishments?schemetypekey=FHRS&ratingOperatorKey=GreaterThanOrEqual")
    Call<EstablishmentResult> getEstablishments(
            @Query("address") String address,
            @Query("pagenumber") int pageNumber,
            @Query("pagesize") int pageSize,
            @Query("maxdistancelimit") int maxDistanceLimit,
            @Query("sortoptionkey") String sortOptionKey,
            @Query("ratingkey") int ratingKey
    );

    // all regions
    @GET("establishments?schemetypekey=FHRS&ratingOperatorKey=GreaterThanOrEqual")
    Call<EstablishmentResult> getEstablishmentsWithBusinessType(
            @Query("address") String address,
            @Query("pagenumber") int pageNumber,
            @Query("pagesize") int pageSize,
            @Query("maxdistancelimit") int maxDistanceLimit,
            @Query("businesstypeid") int businessTypeId,
            @Query("sortoptionkey") String sortOptionKey,
            @Query("ratingkey") int ratingKey
    );

    // specific authority
    @GET("establishments?schemetypekey=FHRS&ratingOperatorKey=GreaterThanOrEqual")
    Call<EstablishmentResult> getEstablishmentsWithBusinessType(
            @Query("address") String address,
            @Query("pagenumber") int pageNumber,
            @Query("pagesize") int pageSize,
            @Query("maxdistancelimit") int maxDistanceLimit,
            @Query("businesstypeid") int businessTypeId,
            @Query("localauthorityid") int localAuthorityId,
            @Query("sortoptionkey") String sortOptionKey,
            @Query("ratingkey") int ratingKey
    );


    @GET("businesstypes/basic")
    Call<BusinessTypeResult> getBusinessTypes();

    @GET("authorities")
    Call<AuthorityResult> getAuthorities();

    @GET("sortoptions")
    Call<SortOptionResult> getSortOptions();
}
