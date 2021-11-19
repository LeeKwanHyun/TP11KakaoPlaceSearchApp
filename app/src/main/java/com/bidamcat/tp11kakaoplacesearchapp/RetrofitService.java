package com.bidamcat.tp11kakaoplacesearchapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RetrofitService {

    @Headers("Authorization: KakaoAK d2c71ae7f2b01e0458ef8d7ab33f9275")
    @GET("/v2/local/search/keyword.json")
    Call<String> searchPlaceByString(@Query("query") String query, @Query("x") String longitude, @Query("y") String latitude);

    @Headers("Authorization: KakaoAK d2c71ae7f2b01e0458ef8d7ab33f9275")
    @GET("/v2/local/search/keyword.json")
    Call<SearchLocalApiResponse> searchPlace(@Query("query") String query, @Query("x") String longitude, @Query("y") String latitude);
}
