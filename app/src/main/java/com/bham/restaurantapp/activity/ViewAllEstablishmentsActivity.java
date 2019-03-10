package com.bham.restaurantapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bham.restaurantapp.R;
import com.bham.restaurantapp.adapter.EstablishmentAdapter;
import com.bham.restaurantapp.model.Establishment;
import com.bham.restaurantapp.model.EstablishmentResult;
import com.bham.restaurantapp.rest.FsaApiInterface;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewAllEstablishmentsActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private RecyclerView rView;
    private TextView pageNumberTextView;
    private final String BASE_URL = "http://api.ratings.food.gov.uk/";
    private int pageNumber;
    private int pageSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_establishments);
        this.pageNumber = 1;
        this.pageSize = 10;
        pageNumberTextView = findViewById(R.id.pageNumberTextView);
        rView = findViewById(R.id.testRecyclerView);
        rView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rView.setLayoutManager(lm);
        // TODO: Implement RecyclerView to show all establishments
        this.pageNumber = 1;
        this.pageSize = 10;
        connectAndGetApiData(this.pageNumber, this.pageSize);
    }

    public void connectAndGetApiData(int pageNumber, int pageSize) {
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
        Call<EstablishmentResult> call = fsaAPI.getEstablishments(pageNumber, pageSize);
        call.enqueue(new Callback<EstablishmentResult>() {
            @Override
            public void onResponse(Call<EstablishmentResult> call, retrofit2.Response<EstablishmentResult> response) {
//                Log.i("establishment_result", "Business name: " + response.body().getEstablishments().get(0).getBusinessName());
                rView.setAdapter(new EstablishmentAdapter(response.body().getEstablishments()));
                pageNumberTextView.setText(String.format(Locale.ENGLISH, "%d/%d", response.body().getMeta().getPageNumber(), response.body().getMeta().getTotalPages()));
            }

            @Override
            public void onFailure(Call<EstablishmentResult> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void onGoToPreviousPage(View view) {
        this.pageNumber -= 1;
        if (this.pageNumber != 0)
            connectAndGetApiData(this.pageNumber, this.pageSize);
        else
            this.pageNumber += 1;

    }

    public void onGoToNextPage(View view) {
        this.pageNumber += 1;
        connectAndGetApiData(this.pageNumber, this.pageSize);
    }
}
