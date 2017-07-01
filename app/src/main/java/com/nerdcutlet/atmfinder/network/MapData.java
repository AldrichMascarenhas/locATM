package com.nerdcutlet.atmfinder.network;

/**
 * Created by Aldrich on 13-11-2016.
 */

public class MapData {
    String name;
    String place_id;
    String vicinity;
    double lat;
    double lon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public MapData(String name, String place_id, String vicinity, double lat, double lon) {
        this.name = name;
        this.place_id = place_id;
        this.vicinity = vicinity;
        this.lat = lat;
        this.lon = lon;
    }
}
