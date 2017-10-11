package com.rudainc.passengercontrol;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.rudainc.passengercontrol.util.Data;

import butterknife.OnClick;


public class FinalScreenActivity extends Activity  {

    @OnClick
    void send(){
        Log.i("SEND", "date "+ Data.getFeedbackInfo(this).date +
                "\ntime "+Data.getFeedbackInfo(this).time +
                "\ntransport "+Data.getFeedbackInfo(this).transport +
                "\nroute "+Data.getFeedbackInfo(this).route +
                "\nboard "+Data.getFeedbackInfo(this).board_number);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_final);
    }
}
