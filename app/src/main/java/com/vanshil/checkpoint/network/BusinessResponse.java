package com.vanshil.checkpoint.network;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vanshil on 2016-05-15.
 */
public class BusinessResponse implements Serializable {
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
    public class BusinessResult implements Serializable{
        @Expose
        String timestamp;

        @Expose
        String name;

        @Expose
        String latitude;

        @Expose
        String longitude;

        public String getTimestamp() {
            return timestamp;
        }

        public String getName() {
            return name;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public LatLng getLatLng(){
            double lat = Double.parseDouble(latitude);
            double lon = Double.parseDouble(longitude);
            return new LatLng(lat, lon);

        }
    }
}
