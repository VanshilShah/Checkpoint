package com.vanshil.checkpoint;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.vanshil.checkpoint.network.BusinessResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectedActivity extends BaseActivity {
    private  static final String EXTRA_SELECTED_BUSINESS = "extra_selected_business";
    @BindView(R.id.start_run_button)
    Button startRunButton;

    @BindView(R.id.reward_amount_textview)
    TextView rewardAmountTextview;

    @BindView(R.id.running_destination_textview)
    TextView runningDestinationTextview;

    @BindView(R.id.textView2)
    TextView textView2;

    NfcAdapter mNfcAdapter;

    private BusinessResponse.BusinessResult business;
    private double dist;
    private boolean changed=false;

    public static void start(Context context, BusinessResponse.BusinessResult selectedBusiness){
        Intent intent = new Intent(context, SelectedActivity.class);
        intent.putExtra(EXTRA_SELECTED_BUSINESS, selectedBusiness);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected);
        ButterKnife.bind(this);

        changed = false;

        //initialize nfcAdapter if NFC is turned on
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if(getIntent().hasExtra(EXTRA_SELECTED_BUSINESS)){
            business = (BusinessResponse.BusinessResult) getIntent().getSerializableExtra(EXTRA_SELECTED_BUSINESS);
            WriteObjectFile writeObjectFile = new WriteObjectFile(this);
            writeObjectFile.writeObject(business, "selected_business");
        }

        locationListener = new LocationManager.Listener() {

            @Override
            public void onLocationChanged(Location location) {


                double businessLat = business.getLatLng().latitude;
                double businessLon = business.getLatLng().longitude;
                double currentLat = location.getLatitude();
                double currentLon = location.getLongitude();

                double diffA = (businessLat-currentLat)*111;
                double diffB =  (businessLon-currentLon)*111;

                dist = Math.sqrt(diffA*diffA + diffB*diffB);

                setTitle("Checkpoint: "+business.getName());


                if (changed == false){
                    updateRewardAmount(dist);
                    updateRunningDistance(dist);
                }
                updateCurrentDistance(dist, business.getName());

                changed=true;
            }


        };

//        rewardAmountTextview.setText(business.toString());


    }

    public void updateRewardAmount(double distance ){
        double amount = distance*0.05*2 ;
        rewardAmountTextview.setText("$ " + String.format("%.2f", amount));
    }

    public void updateRunningDistance(double distance){
        runningDestinationTextview.setText( String.format("%.3f",distance*2) + " km");
    }

    public void updateCurrentDistance(double distance, String name){
        textView2.setText( String.format("%.3f",distance) + " km");
    }


    public void onStartButtonClick (View view){
        String mode = "w";
        LatLng end = business.getLatLng();
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + end.latitude +"," + end.longitude +"&mode=" + mode);

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        startActivity(mapIntent);
    }


}
