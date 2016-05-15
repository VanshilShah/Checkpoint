package com.vanshil.checkpoint;

import android.content.Context;

import com.google.android.gms.location.LocationListener;

/**
 * Created by Vanshil on 2016-05-14.
 */
public class LocationManager {
    private static LocationManager instance = new LocationManager();

    public static LocationManager getInstance() {
        return instance;
    }

    private LocationManager() {
    }

    public interface Listener{
        void onLocationChanged(double latitude, double longitude);
    }
}
