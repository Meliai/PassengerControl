package com.rudainc.passengercontrol;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.rudainc.passengercontrol.util.Data;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;


public class FinalScreenActivity extends BaseActivity {

    private static final int MAIL_APP_OPEN = 222;
    @BindView(R.id.send)
    CardView mSend;

    @BindView(R.id.tvThankYou)
    TextView tvThankYou;

    @BindView(R.id.etComments)
    EditText mComments;

    @BindView(R.id.ll_comments)
    LinearLayout mLlComments;

    @BindView(R.id.ad_container)
    AdView mAdView;

    @OnClick(R.id.send)
    void send() {
        String issues = "";
        for (int i = 0; i < getIntent().getStringArrayListExtra("data").size(); i++)
            issues = issues + getIntent().getStringArrayListExtra("data").get(i) + "\n";


        String content = getResources().getString(R.string.date) + " " + Data.getFeedbackInfo(this).date +
                "\n" + getResources().getString(R.string.time) + " " + Data.getFeedbackInfo(this).time +
                "\n" + getResources().getString(R.string.transport_type) + " " + Data.getFeedbackInfo(this).transport +
                "\n" + getResources().getString(R.string.route) + " " + Data.getFeedbackInfo(this).route +
                "\n" + getResources().getString(R.string.board_number) + " " + Data.getFeedbackInfo(this).board_number +
                "\n"+"\n" + getResources().getString(R.string.issues) + ":\n" + issues +
                "\n" + getResources().getString(R.string.comments) + "\n" + mComments.getText().toString().trim();

        Log.i("SEND", content);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setAction(Intent.ACTION_SENDTO);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.mail_subject) + Data.getFeedbackInfo(this).board_number);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setData(Uri.parse("mailto:y23helen@gmail.com"));

        try {
            startActivityForResult(Intent.createChooser(intent, "Send mail..."), MAIL_APP_OPEN);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("SEND", requestCode + " " + resultCode);
        if (requestCode == MAIL_APP_OPEN) {
            switch (resultCode) {
                case RESULT_OK:
                    mSend.setVisibility(View.GONE);
                    mLlComments.setVisibility(View.GONE);
                    tvThankYou.setVisibility(View.VISIBLE);
                    break;
                case RESULT_CANCELED:
                    Log.i("SEND", "dissmissed");
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }


}
