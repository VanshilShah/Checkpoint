package com.vanshil.checkpoint;

import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

    NfcAdapter mNfcAdapter;

    private BusinessResponse.BusinessResult business;

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
