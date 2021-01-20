package com.example.news.api;

import com.example.news.model.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("top-headlines")
    Call<News> getNews(
        @Query("apiKey") String apiKey,
        @Query("country") String country
    );
}
