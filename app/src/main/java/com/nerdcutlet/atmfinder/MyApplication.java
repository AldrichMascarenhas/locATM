package com.nerdcutlet.atmfinder;

import android.app.Application;

import com.karumi.dexter.Dexter;

import jonathanfinerty.once.Once;

/**
 * Created by Aldrich on 13-11-2016.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Dexter.initialize(this);
        Once.initialise(this);


    }
}
