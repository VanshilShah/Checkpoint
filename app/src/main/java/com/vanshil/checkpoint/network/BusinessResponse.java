package com.vanshil.checkpoint.network;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Vanshil on 2016-05-15.
 */
public class BusinessResponse {
    @Expose
    int total;

    @Expose
    List<BusinessResult> result;

    public int getTotal() {
        return total;
    }

    public List<BusinessResult> getResult() {
        return result;
    }
    public class BusinessResult{
        @Expose
        String timestamp;

        @Expose
        @SerializedName("Name")
        String name;

        @Expose
        @SerializedName("geoip.location")
        String location;

        LatLng latlng;

        public String getTimestamp() {
            return timestamp;
        }

        public String getName() {
            return name;
        }

        public String getLocation() {
            return location;
        }

        public LatLng getLatLng(){
            if(latlng == null){
                int commaIndex = location.indexOf(",");
                double lat = Double.parseDouble(location.substring(0, commaIndex));
                double lon = Double.parseDouble(location.substring(commaIndex + 1));
                System.out.println("Latitude: " + lat + " Longitude: " + lon);
                latlng = new LatLng(lat, lon);
            }
            return latlng;
        }
    }
}
