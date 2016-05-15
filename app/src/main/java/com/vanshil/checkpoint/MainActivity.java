package com.vanshil.checkpoint;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    public static final String TAG = "MainActivity";

    @BindView(R.id.seekbar)
    SeekBar seekBar;

    MapFragment mapFragment;
    GoogleMap map;
    Circle range;
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
        final Activity context = this;
        seekBar.setMax(100);
        locationListener = new LocationManager.Listener() {
            @Override
            public void onLocationChanged(Location location) {
                if(map != null){
                    if(firstLocation){
                        latlng = new LatLng(location.getLatitude(), location.getLongitude());
                        map.addMarker(new MarkerOptions().position(latlng));
                        range = map.addCircle(new CircleOptions()
                                .center(latlng)
                                .radius(500)
                                .strokeColor(getResources().getColor(R.color.colorAccent))
                                .fillColor(getResources().getColor(R.color.yelllow_alpha)));
                        seekBar.setProgress(4);
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
                        firstLocation = false;
                    }
                }
            }
        };
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                range.setRadius(progress*125);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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
