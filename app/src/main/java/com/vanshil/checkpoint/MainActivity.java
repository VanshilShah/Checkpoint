package com.vanshil.checkpoint;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vanshil.checkpoint.network.BusinessResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    public static final String TAG = "MainActivity";

    @BindView(R.id.seekbar)
    SeekBar seekBar;

    @BindView(R.id.textView)
    TextView runText;

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
        seekBar.setMax(10000);
        locationListener = new LocationManager.Listener() {
            @Override
            public void onLocationChanged(Location location) {
                if(map != null){
                    if(firstLocation){
                        latlng = new LatLng(location.getLatitude(), location.getLongitude());
                        map.addMarker(new MarkerOptions().position(latlng));
                        range = map.addCircle(new CircleOptions()
                                .center(latlng)
                                .radius(2000)
                                .strokeColor(getResources().getColor(R.color.colorAccent))
                                .fillColor(getResources().getColor(R.color.yelllow_alpha)));
                        seekBar.setProgress(2000);
                        runText.setText(String.format("2.00 km"));
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 12));
                        firstLocation = false;
                    }
                }
            }
        };
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                range.setRadius(progress);
                runText.setText((int)(progress/500) + " km run");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        zeusListener = new ZeusManager.Listener() {
            @Override
            public void notifyBusinessLoaded(List<BusinessResponse.BusinessResult> businesses) {
                if(map != null){
                    for(BusinessResponse.BusinessResult business : businesses){
                        map.addMarker(new MarkerOptions()
                        .position(business.getLatLng()));
                    }
                }
            }
        };
        zeusManager.loadBusinesses();
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

    //SEND INFO WITH THIS INTENT FOR THE SELECTED ACTIVITY
    public void onPointerClicked (View view){
        Intent intent = new Intent(getApplicationContext(), SelectedActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_tag_discovered, menu);


        return true;//return true so that the menu pop up is opened

    }

}
