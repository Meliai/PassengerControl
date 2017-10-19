package com.rudainc.passengercontrol;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.crashlytics.android.answers.ShareEvent;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.rudainc.passengercontrol.util.Data;

import java.util.ArrayList;
import java.util.Collections;

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

    private static final String EXTRA_DATA = "data";

    @OnClick(R.id.send)
    void send() {
        Answers.getInstance().logCustom(new CustomEvent("Send feedback"));
        StringBuilder issues = new StringBuilder();
        ArrayList<Integer> list = getIntent().getIntegerArrayListExtra(EXTRA_DATA);
        Collections.sort(list);
        for (int i = 0; i < list.size(); i++)
            issues.append(getResources().getStringArray(R.array.issues)[getIntent().getIntegerArrayListExtra(EXTRA_DATA).get(i)]).append("\n");

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

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
        intent.setData(Uri.parse("mailto:"+getString(R.string.email)));

        try {
            startActivity(Intent.createChooser(intent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        Log.i("SEND", requestCode + " " + resultCode);
//        if (requestCode == MAIL_APP_OPEN) {
//            switch (resultCode) {
//                case RESULT_OK:
//                    mSend.setVisibility(View.GONE);
//                    mLlComments.setVisibility(View.GONE);
//                    tvThankYou.setVisibility(View.VISIBLE);
//                    break;
//                case RESULT_CANCELED:
//                    Log.i("SEND", "dissmissed");
//                    break;
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//
//    }

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
