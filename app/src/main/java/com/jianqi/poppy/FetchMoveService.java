package com.jianqi.poppy;

import android.util.Log;

import com.jianqi.poppy.model.MovieResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JianQi on 16-Feb-16.
 */
public class FetchMoveService {
    private String BASE_URL = "http://api.themoviedb.org";
    private String api_key = "37f8919f334ece50d52ae0903ecbc709";
    private final String LOG_TAG = this.getClass().getSimpleName();


    public void getLatestMovie(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieInterface apiService = retrofit.create(MovieInterface.class);

        Call<MovieResult> call = apiService.getLatestMovie(api_key);
        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                Log.d(LOG_TAG, response.body().getPage().toString());

            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                Log.d(LOG_TAG, "onFailure: "+t.getLocalizedMessage());
            }
        });
    }
}
