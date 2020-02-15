package com.rudainc.passengercontrol

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast

import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.CustomEvent

import java.util.ArrayList

class IssuesActivity : BaseActivity(), IssuesAdapter.IssueAdapterOnClickHandler {

    internal var issues = ArrayList<Int>()


    internal fun forward() {
        doNext()
        Answers.getInstance().logCustom(CustomEvent(getString(R.string.event_go_to_final)))
    }

    fun doNext() {
        if (!issues.isEmpty()) {
            val intent = Intent(this, FinalScreenActivity::class.java)
            intent.putIntegerArrayListExtra(EXTRA_DATA, issues)
            startActivity(intent)
        } else
            Toast.makeText(this, R.string.choose_one, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issues)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(R.string.title_choose)
//        rvReviews!!.layoutManager = LinearLayoutManager(this)
        val mIssuesAdapter = IssuesAdapter(this, resources.getStringArray(R.array.issues), this)
//        rvReviews!!.adapter = mIssuesAdapter
    }

    override fun onClick(adapterPosition: Int, add: Boolean) {
        if (add)
            issues.add(adapterPosition)
        else
            issues.removeAt(issues.indexOf(adapterPosition))

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        menu.getItem(0).isVisible = false
        menu.getItem(1).isVisible = true
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.arrow_forward) doNext()

        return super.onOptionsItemSelected(item)
    }

    companion object {

        private val EXTRA_DATA = "data"
    }
}
