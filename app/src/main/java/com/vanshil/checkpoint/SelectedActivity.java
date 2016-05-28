package com.vanshil.checkpoint;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;
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

    @BindView(R.id.header_image)
    ImageView headerImage;

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
      /*  mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }*/

        if(getIntent().hasExtra(EXTRA_SELECTED_BUSINESS)){
            business = (BusinessResponse.BusinessResult) getIntent().getSerializableExtra(EXTRA_SELECTED_BUSINESS);
            WriteObjectFile writeObjectFile = new WriteObjectFile(this);
            writeObjectFile.writeObject(business, "selected_business");
        }

        Picasso.with(getApplicationContext()).load(business.getUrl()).into(headerImage);


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
        startLocationThread();

//        rewardAmountTextview.setText(business.toString());


    }
    private void startLocationThread(){
        locationManager.pingLocation = true;
        final String logPath = "run-" + System.currentTimeMillis() + "";
        final Context context = this;
        final Thread thread = new Thread(new Runnable()
        {

            final int MIN_INTERVAL = 4 * 1000;
            long lastMessageChecked = System.currentTimeMillis();
            @Override
            public void run(){
                int count = 1;
                while (locationManager.pingLocation){
                    if(System.currentTimeMillis() - lastMessageChecked > MIN_INTERVAL){
                        lastMessageChecked = System.currentTimeMillis();
                        zeusManager.postLocation(logPath, LocationManager.getInstance(context).getLocation());
                        Log.d("Location Posting Thread", "Tried to Post Location");
                    }
                    count++;
                }
                Log.d("MESSAGE CHECKING THREAD", "STOPPED");
            }
        });
        thread.start();
        Log.d("MESSAGE CHECKING THREAD", "STARTED");
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
