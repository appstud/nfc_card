package com.appstud.parking;


import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

public class CardApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

}
