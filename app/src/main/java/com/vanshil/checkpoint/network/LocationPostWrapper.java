package com.vanshil.checkpoint.network;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vanshil on 2016-05-15.
 */
public class LocationPostWrapper {
    String name;

    String description;

    List<Object> renders;

    List<LocationPost> parses;

    public LocationPostWrapper(String name, LatLng latlng) {
        this.name = name;
        this.description = "my run";
        this.renders = new ArrayList<>();
        this.parses = new ArrayList<>();
        parses.add(new LocationPost(latlng));
    }

    public class LocationPost{
        @SerializedName("geoip.location")
        String location;

        public LocationPost(LatLng latlng) {
            this.location = latlng.latitude + "," + latlng.longitude;
        }

    }


}
