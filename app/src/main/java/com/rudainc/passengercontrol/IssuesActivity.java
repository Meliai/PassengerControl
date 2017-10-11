package com.rudainc.passengercontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;

public class IssuesActivity extends AppCompatActivity {

    @BindView(R.id.rvIssues)
    RecyclerView rvReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);

        rvReviews.setLayoutManager(new LinearLayoutManager(this));
        IssuesAdapter mIssuesAdapter = new IssuesAdapter(this, getResources().getStringArray(R.array.issues));
        rvReviews.setAdapter(mIssuesAdapter);
    }
}
