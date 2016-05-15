package com.vanshil.checkpoint;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    public static final String TAG = "MainActivity";

    MapFragment mapFragment;
    GoogleMap map;

    private boolean firstLocation = true;
    LatLng latlng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        map = null;
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationListener = new LocationManager.Listener() {
            @Override
            public void onLocationChanged(Location location) {
                if(map != null){
                    if(firstLocation){
                        latlng = new LatLng(location.getLatitude(), location.getLongitude());
                        map.addMarker(new MarkerOptions().position(latlng));
                        Circle circle = map.addCircle(new CircleOptions()
                                .center(latlng)
                                .radius(1000)
                                .strokeColor(Color.RED)
                                .fillColor(Color.BLUE));
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
                        firstLocation = false;
                    }
                }
            }
        };
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMarkerClickListener(this);
    }

    private void addMarkers(){

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    public void onTempButtonClick (View view){
        Intent intent = new Intent(getApplicationContext(), SelectedActivity.class);
        startActivity(intent);
    }


}
