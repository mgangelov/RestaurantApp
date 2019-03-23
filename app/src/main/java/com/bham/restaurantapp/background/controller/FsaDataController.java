package com.bham.restaurantapp.background.controller;

import android.content.Context;
import android.util.Log;

import com.bham.restaurantapp.model.db.FsaDatabase;
import com.bham.restaurantapp.model.db.entities.AuthorityEntity;
import com.bham.restaurantapp.model.fsa.AuthorityResult;
import com.bham.restaurantapp.model.fsa.BusinessTypeResult;
import com.bham.restaurantapp.model.fsa.EstablishmentResult;
import com.bham.restaurantapp.model.fsa.RegionResult;
import com.bham.restaurantapp.rest.FsaApiInterface;

import java.io.IOException;
import java.util.List;

import androidx.room.Room;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FsaDataController {
    private static final String TAG = "FsaDataController";
    private Retrofit retrofit;
    private FsaDatabase db;

    public FsaDataController(Context applicationContext) {
        this.retrofit = null;
        db = Room.databaseBuilder(
                applicationContext,
                FsaDatabase.class,
                "database")
                .build();
    }


    public EstablishmentResult getEstablishments(int pageNumber, int pageSize) throws IOException {
        return connectToFsaApi()
                .getEstablishments(pageNumber, pageSize)
                .execute()
                .body();
    }

    public EstablishmentResult getEstablishments(
            String searchValue,
            int businessType,
            int region,
            int authority,
            int pageNumber,
            int pageSize
    ) throws IOException {
        float DEFAULT_MAX_DISTANCE = 3;
        Log.i(TAG, "Searching using searchValue \n" +
                "using business type " + businessType + "\n " +
                "and region " + region + "\n" +
                "and authority " + authority + "\n" +
                "and maxDistanceLimit " + DEFAULT_MAX_DISTANCE);
        if (businessType == -1) {
            return connectToFsaApi()
                    .getEstablishments(
                            searchValue,
                            pageNumber,
                            pageSize,
                            DEFAULT_MAX_DISTANCE
                    )
                    .execute()
                    .body();
        }
        if (authority < 8999) { // Unique authority selected
            return connectToFsaApi()
                    .getEstablishments(
                            searchValue,
                            pageNumber,
                            pageSize,
                            DEFAULT_MAX_DISTANCE,
                            businessType,
                            authority
                    )
                    .execute()
                    .body();
        } else if (region == 99) {
            return connectToFsaApi()
                    .getEstablishments(
                            searchValue,
                            pageNumber,
                            pageSize,
                            DEFAULT_MAX_DISTANCE,
                            businessType
                    )
                    .execute()
                    .body();
        } else { // All authorities from region selected
            // TODO
            List<AuthorityEntity> authorities = db
                    .authorityDAO()
                    .findAuthorityByRegionId(region);
            return null;
        }
    }


    public EstablishmentResult getEstablishments(
            String longitude,
            String latitude,
            int businessType,
            int region,
            int authority,
            float maxDistanceLimit,
            int pageNumber,
            int pageSize
    ) throws IOException {
        Log.i(TAG, "Searching using longitude & latitude \n" +
                "using business type " + businessType + "\n " +
                "and region " + region + "\n" +
                "and authority " + authority + "\n" +
                "and maxDistanceLimit " + maxDistanceLimit);
        if (businessType == -1) {
            return connectToFsaApi()
                    .getEstablishments(
                            longitude,
                            latitude,
                            pageNumber,
                            pageSize,
                            maxDistanceLimit
                    )
                    .execute()
                    .body();
        }
        else if (authority < 8999) {
            return connectToFsaApi()
                    .getEstablishments(
                            longitude,
                            latitude,
                            pageNumber,
                            pageSize,
                            maxDistanceLimit,
                            businessType,
                            authority
                    )
                    .execute()
                    .body();
        } else if (region == 99) {
            return connectToFsaApi()
                    .getEstablishments(
                            longitude,
                            latitude,
                            pageNumber,
                            pageSize,
                            maxDistanceLimit,
                            businessType
                    )
                    .execute()
                    .body();
        } else { // All authorities from region selected
            // TODO
            List<AuthorityEntity> authorities = db
                    .authorityDAO()
                    .findAuthorityByRegionId(region);
            return null;
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
