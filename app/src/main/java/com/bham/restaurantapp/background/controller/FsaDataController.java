package com.bham.restaurantapp.background.controller;

import android.util.Log;

import com.bham.restaurantapp.App;
import com.bham.restaurantapp.model.db.FsaDatabase;
import com.bham.restaurantapp.model.db.entities.AuthorityEntity;
import com.bham.restaurantapp.model.fsa.AuthorityResult;
import com.bham.restaurantapp.model.fsa.BusinessTypeResult;
import com.bham.restaurantapp.model.fsa.EstablishmentResult;
import com.bham.restaurantapp.model.fsa.RegionResult;
import com.bham.restaurantapp.model.fsa.SortOptionResult;
import com.bham.restaurantapp.rest.FsaApiInterface;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.bham.restaurantapp.Globals.DEFAULT_AUTHORITY_ID;
import static com.bham.restaurantapp.Globals.DEFAULT_BUSINESS_TYPE_ID;
import static com.bham.restaurantapp.Globals.DEFAULT_MAX_DISTANCE_LIMIT;
import static com.bham.restaurantapp.Globals.DEFAULT_REGION_ID;

public class FsaDataController {
    private static final String TAG = "FsaDataController";
    private Retrofit retrofit;
    private FsaDatabase db;

    public FsaDataController() {
        this.retrofit = null;
        this.db = App.getInstance().getDb();
    }


    public EstablishmentResult getEstablishments(
            int pageNumber,
            int pageSize,
            String sortOptionKey
    ) throws IOException {
        return connectToFsaApi()
                .getEstablishments(
                        pageNumber,
                        pageSize,
                        sortOptionKey
                )
                .execute()
                .body();
    }

    public EstablishmentResult getEstablishments(
            String searchValue,
            int businessType,
            int region,
            int authority,
            int pageNumber,
            int pageSize,
            String sortOptionKey,
            int ratingKey
    ) throws IOException {
        Log.i(TAG, "Searching using searchValue \n" +
                "using business type " + businessType + "\n " +
                "and region " + region + "\n" +
                "and authority " + authority + "\n" +
                "and maxDistanceLimit " + DEFAULT_MAX_DISTANCE_LIMIT);

        if (businessType == DEFAULT_BUSINESS_TYPE_ID) { // All business types
            if (region == 99) { // All regions, everywhere essentially
                Log.i(TAG, "getEstablishments: all regions");
                return connectToFsaApi()
                        .getEstablishments(
                                searchValue,
                                pageNumber,
                                pageSize,
                                DEFAULT_MAX_DISTANCE_LIMIT,
                                sortOptionKey,
                                ratingKey
                        ).execute().body();
            } else {
                if (authority < 8999) { // Specific authority selected
                    Log.i(TAG, "getEstablishments: specific authority selected");
                    Log.i(TAG, "getEstablishments: " + authority + " " + ratingKey + " ");
                    return connectToFsaApi()
                            .getEstablishments(
                                    searchValue,
                                    pageNumber,
                                    pageSize,
                                    DEFAULT_MAX_DISTANCE_LIMIT,
                                    authority,
                                    sortOptionKey,
                                    ratingKey
                            ).execute().body();
                } else { // All authorities from region required
                    Log.i(TAG, "getEstablishments: no need to be implemented");
                    // TODO
                    List<AuthorityEntity> authorities = db
                            .authorityDAO()
                            .findAuthorityByRegionId(region);
                    return null;
                }
            }
        } else { // Specific business type selected
            if (region == 99) { // All regions, everywhere
                return connectToFsaApi()
                        .getEstablishmentsWithBusinessType(
                                searchValue,
                                pageNumber,
                                pageSize,
                                DEFAULT_MAX_DISTANCE_LIMIT,
                                businessType,
                                sortOptionKey,
                                ratingKey
                        ).execute().body();
            } else {
                if (authority < 8999) { // Specific authority selected
                    return connectToFsaApi()
                            .getEstablishmentsWithBusinessType(
                                    searchValue,
                                    pageNumber,
                                    pageSize,
                                    DEFAULT_MAX_DISTANCE_LIMIT,
                                    businessType,
                                    authority,
                                    sortOptionKey,
                                    ratingKey
                            ).execute().body();
                } else { // All authorities from region required
                    // TODO
                    List<AuthorityEntity> authorities = db
                            .authorityDAO()
                            .findAuthorityByRegionId(region);
                    return null;
                }
            }
        }
    }


    public EstablishmentResult getEstablishments(
            String longitude,
            String latitude,
            int businessType,
            int region,
            int authority,
            int maxDistanceLimit,
            int pageNumber,
            int pageSize,
            String sortOptionKey,
            int ratingKey
    ) throws IOException {
        Log.i(TAG, "Searching using longitude & latitude \n" +
                "using business type " + businessType + "\n " +
                "and region " + region + "\n" +
                "and authority " + authority + "\n" +
                "and maxDistanceLimit " + maxDistanceLimit + "\n" +
                "and longitude " + longitude + "\n" +
                "and latitude " + latitude + "\n" +
                "and sortOptionKey " + sortOptionKey + "\n" +
                "and ratingKey " + ratingKey + "\n"

        );
        if (businessType == DEFAULT_BUSINESS_TYPE_ID) {
            if (region == DEFAULT_REGION_ID) {
                Log.i(TAG, "getEstablishments: default region id");
                return connectToFsaApi()
                        .getEstablishments(
                                longitude,
                                latitude,
                                pageNumber,
                                pageSize,
                                maxDistanceLimit,
                                sortOptionKey,
                                ratingKey
                        ).execute().body();
            } else {
                if (authority < DEFAULT_AUTHORITY_ID) {
                    Log.i(TAG, "getEstablishments: default authority id");
                   return connectToFsaApi()
                           .getEstablishments(
                                   longitude,
                                   latitude,
                                   pageNumber,
                                   pageSize,
                                   maxDistanceLimit,
                                   authority,
                                   sortOptionKey,
                                   ratingKey
                           ).execute().body();
                } else {
                    // TODO
                    Log.i(TAG, "getEstablishments: todododododo");
                    List<AuthorityEntity> authorities = db
                            .authorityDAO()
                            .findAuthorityByRegionId(region);
                    return null;
                }
            }
        } else {
            if (region == DEFAULT_REGION_ID) {
                return connectToFsaApi()
                        .getEstablishmentsWithBusinessType(
                                longitude,
                                latitude,
                                pageNumber,
                                pageSize,
                                maxDistanceLimit,
                                businessType,
                                sortOptionKey,
                                ratingKey
                        ).execute().body();
            } else {
                if (authority < DEFAULT_AUTHORITY_ID) {
                    return connectToFsaApi()
                            .getEstablishmentsWithBusinessType(
                                    longitude,
                                    latitude,
                                    pageNumber,
                                    pageSize,
                                    maxDistanceLimit,
                                    businessType,
                                    authority,
                                    sortOptionKey,
                                    ratingKey
                            ).execute().body();
                } else {
                    // TODO
                    List<AuthorityEntity> authorities = db
                            .authorityDAO()
                            .findAuthorityByRegionId(region);
                    return null;
                }
            }
        }
    }



    public BusinessTypeResult getBusinessTypes() throws IOException {
        return connectToFsaApi().getBusinessTypes().execute().body();
    }

    public RegionResult getRegions() throws IOException {
        return connectToFsaApi().getRegions().execute().body();
    }

    public AuthorityResult getAuthorities() throws IOException {
        return connectToFsaApi().getAuthorities().execute().body();
    }

    public SortOptionResult getSortOptions() throws IOException {
        return connectToFsaApi().getSortOptions().execute().body();
    }

    private FsaApiInterface connectToFsaApi() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(chain -> {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("x-api-version", "2")
                        .build();
                return chain.proceed(request);
            });
            String BASE_URL = "http://api.ratings.food.gov.uk/";
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .build();
        }

        return retrofit.create(FsaApiInterface.class);
    }

}
