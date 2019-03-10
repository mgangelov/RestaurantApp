package com.bham.restaurantapp.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.bham.restaurantapp.R;
import com.bham.restaurantapp.adapter.EstablishmentArrayAdapter;
import com.bham.restaurantapp.model.Establishment;
import com.bham.restaurantapp.model.EstablishmentResult;
import com.bham.restaurantapp.rest.FsaApiInterface;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public final String BASE_URL = "http://api.ratings.food.gov.uk/";
    private ListView listView;
    private static Retrofit retrofit = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.listView = findViewById(R.id.regionsListView);
        connectAndGetApiData();
    }

    public void connectAndGetApiData() {
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
        Call<EstablishmentResult> call = fsaAPI.getEstablishments("B15 2TT", 3);
        call.enqueue(new Callback<EstablishmentResult>() {
            @Override
            public void onResponse(Call<EstablishmentResult> call, retrofit2.Response<EstablishmentResult> response) {
                Log.i("establishment_result", "Business name: " + response.body().getEstablishments().get(0).getBusinessName());
                Log.i("establishment_result", "Rating: " + response.body().getEstablishments().get(0).getRatingValue());
                List<Establishment> establishments = response.body().getEstablishments();
                listView.setAdapter(new EstablishmentArrayAdapter(getApplicationContext(), R.layout.activity_main, establishments));
            }

            @Override
            public void onFailure(Call<EstablishmentResult> call, Throwable t) {
                t.printStackTrace();
            }
        });
//        Call<RegionResult> call = fsaAPI.getRegions();
//        call.enqueue(new Callback<RegionResult>() {
//            @Override
//            public void onResponse(Call<RegionResult> call, retrofit2.Response<RegionResult> response) {
//                System.out.println("HAHA: " + response.body().getRegions().get(0).getName());
//                List<Region> regions = response.body().getRegions();
//                listView.setAdapter(new RegionAdapter(getApplicationContext(), R.layout.activity_main, regions));
//            }
//
//            @Override
//            public void onFailure(Call<RegionResult> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });

    }
}
