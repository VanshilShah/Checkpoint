package com.vanshil.checkpoint;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

/**
 * Created by Vanshil on 2016-05-15.
 */
public class BaseActivity extends AppCompatActivity {
    LocationManager.Listener locationListener;
    LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = locationManager.getInstance(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        locationManager.onResume();
        if(locationListener != null){
            locationManager.register(locationListener);
        }
    }

    @Override
    public void onPause(){
        LocationManager.getInstance(this).onPause();
        if(locationListener != null){
            locationManager.unregister(locationListener);
        }
        super.onPause();
    }


}
