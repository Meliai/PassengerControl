package com.rudainc.passengercontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ShareEvent;
import com.google.android.gms.ads.MobileAds;

import io.fabric.sdk.android.Fabric;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fabric
        Fabric.with(this, new Crashlytics(),new Answers());

        //Ads
        MobileAds.initialize(this, getString(R.string.ads_mobile_id));
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.share:
                Answers.getInstance().logShare(new ShareEvent());
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String app_link = "https://play.google.com/store/apps/details?id=com.rudainc.popularmovies&hl=en";
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text) + app_link);
                startActivity(Intent.createChooser(intent, "Share with"));
                break;
        }
        return true;
    }
}
