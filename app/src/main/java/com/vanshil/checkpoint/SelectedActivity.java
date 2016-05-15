package com.vanshil.checkpoint;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.nio.charset.Charset;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.nfc.NdefRecord.createMime;

public class SelectedActivity extends BaseActivity {

    @BindView(R.id.start_run_button)
    Button startRunButton;

    @BindView(R.id.reward_amount_textview)
    TextView rewardAmountTextview;

    @BindView(R.id.running_destination_textview)
    TextView runningDestinationTextview;

    @BindView(R.id.arrived_textView)
    TextView arrivedTextview;

    NfcAdapter mNfcAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected);
        ButterKnife.bind(this);

        //initialize nfcAdapter if NFC is turned on
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }


        updateRewardAmount(15.5);
        updateRunningDistance(15.5);

    }

    public void updateRewardAmount(double distance){
        double amount = distance*0.05 ;
        rewardAmountTextview.setText("You will earn: $" + amount);
    }

    public void updateRunningDistance(double distance){
        runningDestinationTextview.setText(distance + " km");
    }


//    public void onStartButtonClick (View view){
//        Intent intent = new Intent(getApplicationContext(), SelectedActivity.class);
//        startActivity(intent);
//    }


}
