package com.nerdcutlet.atmfinder.network;

/**
 * Created by Aldrich on 13-11-2016.
 */

public interface AsyncResponse {
    void processFinish(String output, double lat, double lon, String rad);

}
