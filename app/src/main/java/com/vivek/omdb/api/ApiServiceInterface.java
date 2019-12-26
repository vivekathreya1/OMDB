package com.vivek.omdb.api;

import com.google.gson.JsonElement;
import com.vivek.omdb.model.MovieDetailRepo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServiceInterface {

    @GET("?")
    Call<MovieDetailRepo> getMovieDetails(@Query("i") String imdbId, @Query("plot") String plotLength);

    @GET("?")
    Call<JsonElement> getMovies(@Query("s") String title, @Query("page") String pageNo);


}
