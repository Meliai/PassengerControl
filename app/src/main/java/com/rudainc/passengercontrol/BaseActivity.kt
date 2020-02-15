package com.rudainc.passengercontrol

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem

import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.CustomEvent
import com.crashlytics.android.answers.ShareEvent
import com.google.android.gms.ads.MobileAds

import io.fabric.sdk.android.Fabric

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fabric
        Fabric.with(this, Crashlytics(), Answers())

        //Ads
        MobileAds.initialize(this, getString(R.string.ads_mobile_id))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.share -> {
                Answers.getInstance().logShare(ShareEvent())
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                val app_link = "https://play.google.com/store/apps/details?id=com.rudainc.passengercontrol"
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text) + app_link)
                startActivity(Intent.createChooser(intent, "Share with"))
            }
            R.id.arrow_forward -> Answers.getInstance().logCustom(CustomEvent(getString(R.string.event_go_to_final_arrow)))
        }
        return true
    }
}
