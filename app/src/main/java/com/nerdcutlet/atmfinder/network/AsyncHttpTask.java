package com.nerdcutlet.atmfinder.network;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.nerdcutlet.atmfinder.model.MapsDataModel;
import com.nerdcutlet.atmfinder.model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Aldrich on 13-11-2016.
 */

public class AsyncHttpTask extends AsyncTask<Object, Integer, Void> {
    public AsyncResponse delegate = null;

    GoogleMap googleMap;
    public static final String LOG_TAG = "AsyncHttpTask";

    MapsDataModel result;

    @Override
    protected Void doInBackground(Object... objects) {

        googleMap = (GoogleMap) objects[0];
        final double lat = (Double) objects[1];
        final double lon = (Double) objects[2];
        final String rad = (String) objects[3];
        final String nextpagetoken = (String) objects[4];


        String latString = String.valueOf(lat);
        String lonString = String.valueOf(lon);
        final String location = latString + "," + lonString;


        final MapsClient client = MapsAPIServiceGenerator.createService(MapsClient.class);

        Call<MapsDataModel> call;

        call = client.getData(location, rad, nextpagetoken);

        call.enqueue(new Callback<MapsDataModel>() {
                         @Override
                         public void onResponse(Call<MapsDataModel> call, Response<MapsDataModel> response) {
                             Log.d(LOG_TAG, "Status Code = " + response.code());
                             Log.d(LOG_TAG, "API Call = " + call.request().url().toString());

                             if (response.isSuccessful()) {
                                 // request successful (status code 200, 201)


                                 result = response.body();

                                 //Uncomment to Log Respone + API Call
                                 Log.d(LOG_TAG, "response = " + new Gson().toJson(result));

                                 if (result.getNextPageToken() != null || result.getNextPageToken() != "") {
                                     Log.d(LOG_TAG, "getNextPageToken = " + result.getNextPageToken());

                                 }
                                 passData(result, lat, lon, rad);

                             } else

                             {
                                 //request not successful (like 400,401,403 etc)
                                 //Handle errors
                                 Log.e(LOG_TAG, "mess" + response.message());
                                 //Log.e(LOG_TAG, "fail" + response.body().toString());
                                 Log.e(LOG_TAG, "code" + response.code());

                             }
                         }

                         @Override
                         public void onFailure(Call<MapsDataModel> call, Throwable t) {
                             Log.e(LOG_TAG, "fail");

                         }
                     }

        );

        return null;


    }


    void passData(MapsDataModel m, double lat, double lon, String rad) {

        Marker myMarker;

        Log.e(LOG_TAG, "SIZE : " + m.getResults().size());

        for (int i = 0; i < m.getResults().size(); i++) {


            LatLng latLng = new LatLng(m.getResults().get(i).getGeometry().getLocation().getLat(), m.getResults().get(i).getGeometry().getLocation().getLng());

            myMarker = googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(m.getResults().get(i).getName())
                    .snippet(m.getResults().get(i).getVicinity())
                    .anchor(0.5f, 0.5f));

            //https://developers.google.com/maps/documentation/android-api/marker
            MapData mapdata = new MapData(m.getResults().get(i).getName(),m.getResults().get(i).getPlaceId(), m.getResults().get(i).getVicinity(), m.getResults().get(i).getGeometry().getLocation().getLat(), m.getResults().get(i).getGeometry().getLocation().getLng());

            myMarker.setTag(mapdata);

        }

        delegate.processFinish(m.getNextPageToken(), lat, lon, rad);


    }


}
