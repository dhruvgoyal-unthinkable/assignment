package com.example.userlocation;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class LocationService extends Service {

    private Utils utils;

    public LocationService() {
        utils = new Utils();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        utils.initializeLocationComponent(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        utils.updateLocation(this, MainActivity.activity);
        return START_STICKY;
    }

}