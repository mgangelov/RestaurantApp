package com.bham.restaurantapp.background.controller;

import android.util.Log;

import com.bham.restaurantapp.model.fsa.AuthorityResult;
import com.bham.restaurantapp.model.fsa.BusinessTypeResult;
import com.bham.restaurantapp.model.fsa.EstablishmentResult;
import com.bham.restaurantapp.model.fsa.RegionResult;
import com.bham.restaurantapp.rest.FsaApiInterface;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FsaDataController {
    private static final String TAG = "FsaDataController";
    private Retrofit retrofit;
    private final String BASE_URL = "http://api.ratings.food.gov.uk/";
    private final float DEFAULT_MAX_DISTANCE = 2;

    public FsaDataController() {
        this.retrofit = null;
    }


    public EstablishmentResult getEstablishments(int pageNumber, int pageSize) throws IOException {
        return getEstablishments(
                null,
                null,
                0,
                pageNumber,
                pageSize
        );
    }


    public EstablishmentResult getEstablishments(
            String longitude,
            String latitude,
            float maxDistance,
            int pageNumber,
            int pageSize
    ) throws IOException {
        FsaApiInterface fsaAPI = connectToFsaApi();
        Call<EstablishmentResult> call;
        if (longitude == null && latitude == null) {
            Log.i(TAG, "Reached all establishments request");
            call = fsaAPI.getEstablishments(pageNumber, pageSize);
        }
        else if (maxDistance == 0) {
            Log.i(TAG, "Reached max distance check");
            call = fsaAPI.getEstablishments(longitude, latitude, pageNumber, pageSize, DEFAULT_MAX_DISTANCE);
        } else
            Log.i(TAG, "Reached normal distance query");
            call = fsaAPI.getEstablishments(longitude, latitude, pageNumber, pageSize, maxDistance);

        return call.execute().body();
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

    public FsaApiInterface connectToFsaApi() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("x-api-version", "2")
                            .build();
                    return chain.proceed(request);
                }
            });
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .build();
        }

        return retrofit.create(FsaApiInterface.class);
    }

}
