package com.bham.restaurantapp.background;

import android.widget.TextView;

import com.bham.restaurantapp.adapter.EstablishmentAdapter;
import com.bham.restaurantapp.model.fsa.Establishment;
import com.bham.restaurantapp.model.fsa.EstablishmentResult;
import com.bham.restaurantapp.rest.FsaApiInterface;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FsaDataController {
    private Retrofit retrofit;
    private final String BASE_URL = "http://api.ratings.food.gov.uk/";

    public FsaDataController() {
        this.retrofit = null;
    }


    public EstablishmentResult connectAndGetApiData(int pageNumber, int pageSize) throws IOException {
        return connectAndGetApiData(null, null, pageNumber, pageSize);
    }


    public EstablishmentResult connectAndGetApiData(
            String longitude,
            String latitude,
            int pageNumber,
            int pageSize
    ) throws IOException {
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

        FsaApiInterface fsaAPI = retrofit.create(FsaApiInterface.class);
        Call<EstablishmentResult> call = null;
        if (longitude == null && latitude == null)
            call = fsaAPI.getEstablishments(pageNumber, pageSize);
        else
            call = fsaAPI.getEstablishments(longitude, latitude, pageNumber, pageSize);

        return call.execute().body();
    }

}
