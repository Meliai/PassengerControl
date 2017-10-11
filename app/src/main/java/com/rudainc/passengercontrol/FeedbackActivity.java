package com.rudainc.passengercontrol;

import android.app.Activity;
import android.os.Bundle;

import butterknife.OnClick;


public class FeedbackActivity extends Activity {

    @OnClick
    void back() {
//        MainInfoActivity_.intent(this).start();
    }

    @OnClick(R.id.forward)
    void forward() {
//        FinalScreenActivity_.intent(this).start();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_feedback);
    }
}