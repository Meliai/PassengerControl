package com.rudainc.passengercontrol;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.rudainc.passengercontrol.util.Data;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class FinalScreenActivity extends Activity  {

    @OnClick(R.id.send)
    void send(){
String content = "date "+ Data.getFeedbackInfo(this).date +
        "\ntime "+Data.getFeedbackInfo(this).time +
        "\ntransport "+Data.getFeedbackInfo(this).transport +
        "\nroute "+Data.getFeedbackInfo(this).route +
        "\nboard "+Data.getFeedbackInfo(this).board_number;
        Log.i("SEND", content);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("message/rfc822");
//        intent.putExtra(Intent.EXTRA_EMAIL, "y23helen@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setData(Uri.parse("mailto:y23helen@gmail.com"));

        try {
            startActivity(Intent.createChooser(intent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        ButterKnife.bind(this);
    }
}
