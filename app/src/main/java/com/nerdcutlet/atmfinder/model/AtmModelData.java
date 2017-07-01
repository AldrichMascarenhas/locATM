package com.nerdcutlet.atmfinder.model;

import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Aldrich on 13-11-2016.
 */
@IgnoreExtraProperties

public class AtmModelData {



    String total;
    String n100;
    String n2000;
    String datetime;


    public AtmModelData() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public AtmModelData(String total, String n100, String n2000, String  datetime) {
        this.total = total;
        this.n100 = n100;
        this.n2000 = n2000;
        this.datetime = datetime;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getN100() {
        return n100;
    }

    public void setN100(String n100) {
        this.n100 = n100;
    }

    public String getN2000() {
        return n2000;
    }

    public void setN2000(String n2000) {
        this.n2000 = n2000;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
