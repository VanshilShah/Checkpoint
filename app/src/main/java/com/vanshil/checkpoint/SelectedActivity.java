package com.vanshil.checkpoint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectedActivity extends AppCompatActivity {

    @BindView(R.id.start_run_button)
    Button startRunButton;

    @BindView(R.id.reward_amount_textview)
    TextView rewardAmountTextview;

    @BindView(R.id.running_destination_textview)
    TextView runningDestinationTextview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected);
        ButterKnife.bind(this);


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
