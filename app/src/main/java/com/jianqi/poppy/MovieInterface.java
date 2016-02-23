package com.jianqi.poppy;

import com.jianqi.poppy.model.MovieResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieInterface{
    @GET("3/movie/popular")
    Call<MovieResult> getLatestMovie(@Query("api_key") String api_key);
}