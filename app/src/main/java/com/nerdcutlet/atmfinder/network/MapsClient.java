package com.nerdcutlet.atmfinder.network;

import com.nerdcutlet.atmfinder.BuildConfig;
import com.nerdcutlet.atmfinder.model.MapsDataModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Aldrich on 13-11-2016.
 */

public interface MapsClient {


    @GET("json?types=atm&key=AIzaSyDFLe4gK_CZAPBkwWbvDru-eQj4vJd1cG8")
    Call<MapsDataModel> getData(@Query("location") String location, @Query("radius") String radius,@Query("pagetoken") String pagetoken);


}

