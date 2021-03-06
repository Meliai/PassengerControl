package com.rudainc.passengercontrol

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.CustomEvent
import com.google.android.gms.ads.AdRequest
import com.rudainc.passengercontrol.util.Data
import java.util.*


class FinalScreenActivity : BaseActivity() {
    internal fun send() {
        Answers.getInstance().logCustom(CustomEvent("Send feedback"))
        val issues = StringBuilder()
        val list = intent.getIntegerArrayListExtra(EXTRA_DATA)
        Collections.sort(list)
        for (i in list.indices)
            issues.append(resources.getStringArray(R.array.issues_kpt)[intent.getIntegerArrayListExtra(EXTRA_DATA)[i]]).append("\n")

        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        val content = resources.getString(R.string.date) + " " + Data.getFeedbackInfo(this).date +
                "\n" + resources.getString(R.string.time) + " " + Data.getFeedbackInfo(this).time +
                "\n" + resources.getString(R.string.transport_type) + " " + Data.getFeedbackInfo(this).transport +
                "\n" + resources.getString(R.string.route) + " " + Data.getFeedbackInfo(this).route +
                "\n" + resources.getString(R.string.board_number) + " " + Data.getFeedbackInfo(this).boardNumber +
                "\n" + "\n" + resources.getString(R.string.issues) + ":\n" + issues
//                "\n" + resources.getString(R.string.comments) + "\n" + mComments!!.text.toString().trim { it <= ' ' }

//        Log.i("SEND", content)

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.action = Intent.ACTION_SENDTO
        intent.type = "message/rfc822"
        intent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.mail_subject) + Data.getFeedbackInfo(this).boardNumber!!)
        intent.putExtra(Intent.EXTRA_TEXT, content)
        intent.data = Uri.parse("mailto:" + getString(R.string.email))

        try {
            startActivity(Intent.createChooser(intent, "Send mail..."))
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show()
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val adRequest = AdRequest.Builder().build()
//        mAdView!!.loadAd(adRequest)

    }

    companion object {

        private val MAIL_APP_OPEN = 222

        private val EXTRA_DATA = "data"
    }


}
