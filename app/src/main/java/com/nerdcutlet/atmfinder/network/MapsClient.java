package com.nerdcutlet.atmfinder.network;

import com.nerdcutlet.atmfinder.model.MapsDataModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Aldrich on 13-11-2016.
 */

public interface MapsClient {

    @GET("json?types=atm&sensor=false&key=AIzaSyC0faAJN1JzNqdolnFtRUU6p8J19HTNOVg")
    Call<MapsDataModel> getData(@Query("location") String location, @Query("radius") String radius,@Query("pagetoken") String pagetoken);


}

