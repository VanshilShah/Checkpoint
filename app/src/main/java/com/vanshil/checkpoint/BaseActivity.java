package com.vanshil.checkpoint;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Vanshil on 2016-05-15.
 */
public class BaseActivity extends AppCompatActivity {
    LocationManager.Listener locationListener;
    LocationManager locationManager;
    ZeusManager.Listener zeusListener;
    ZeusManager zeusManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = locationManager.getInstance(this);
        zeusManager = ZeusManager.getInstance();
    }

    @Override
    public void onResume(){
        super.onResume();
        locationManager.onResume();
        if(locationListener != null){
            locationManager.register(locationListener);
        }
        if(zeusListener != null){
            zeusManager.register(zeusListener);
        }

    }

    @Override
    public void onPause(){
        LocationManager.getInstance(this).onPause();
        if(locationListener != null){
            locationManager.unregister(locationListener);
        }
        if(zeusListener != null){
            zeusManager.unregister(zeusListener);
        }
        super.onPause();
    }
}
