package com.example.wazesupermarket.wazesupermarket;


import android.app.Application;

import com.facebook.appevents.AppEventsLogger;

public class User extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AppEventsLogger.activateApp(this);
    }
}
