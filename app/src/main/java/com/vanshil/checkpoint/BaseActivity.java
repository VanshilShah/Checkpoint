package com.vanshil.checkpoint;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Vanshil on 2016-05-15.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public void onResume(){
        super.onResume();
        LocationManager.getInstance(this).onResume();
    }

    @Override
    public void onPause(){
        LocationManager.getInstance(this).onPause();
        super.onPause();
    }
}
