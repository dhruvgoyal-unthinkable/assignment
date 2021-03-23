package com.example.userlocation;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Utils {
    private static boolean checkLocationPermission(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    FusedLocationProviderClient client;

    public void updateLocation(Context context, Activity activity) {
        new Thread(() -> {
            while (true) {
                if (!checkLocationPermission(context))
                    return;
                // Log.d("Location", "Getting location");
                client.getLastLocation().addOnSuccessListener(activity, location -> {
                    if (location != null) {
                        uploadLocation(location.getLatitude(), location.getLongitude());
                    }
                });
            }
        }).start();
    }

    static DatabaseReference ref;

    public void initializeLocationComponent(Context context) {
        client = LocationServices.getFusedLocationProviderClient(context);
        ref = FirebaseDatabase.getInstance().getReference("UserLocation");
    }

    private void uploadLocation(double latitude, double longitude) {
        //Log.d("Location", "updating....");
        ref.child("Latitude").setValue(latitude);
        ref.child("Longitude").setValue(longitude);
    }
}
