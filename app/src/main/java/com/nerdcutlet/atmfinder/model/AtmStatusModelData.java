package com.nerdcutlet.atmfinder.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Aldrich on 13-11-2016.
 */


public class AtmStatusModelData {


    String status;
    String datetime;

    public AtmStatusModelData() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public AtmStatusModelData(String status, String datetime) {
        this.datetime = datetime;

        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
