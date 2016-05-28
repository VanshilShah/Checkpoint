package com.vanshil.checkpoint;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vanshil.checkpoint.network.BusinessResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TagDiscoveredActivity extends AppCompatActivity {

    BusinessResponse.BusinessResult businessResult;

    @BindView(R.id.arrived_textView)
    TextView arrivedTextView;

    @BindView(R.id.arrived_image)
    ImageView arrivedImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_discovered);
        ButterKnife.bind(this);

        businessResult = (BusinessResponse.BusinessResult) new WriteObjectFile(this).readObject("selected_business");
        LocationManager.getInstance(this).pingLocation = false;


        setTitle("Checkpoint: "+ businessResult.getName());
        Picasso.with(getApplicationContext()).load(businessResult.getUrl()).into(arrivedImage);
        arrivedTextView.setText("You've made it to " + businessResult.getName());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tag_discovered, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
