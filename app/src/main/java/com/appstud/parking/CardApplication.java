package com.appstud.parking;


import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class CardApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("Avenir-Book.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

}
