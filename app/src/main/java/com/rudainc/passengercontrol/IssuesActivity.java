package com.rudainc.passengercontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IssuesActivity extends AppCompatActivity {

    @BindView(R.id.rvIssues)
    RecyclerView rvReviews;


    @OnClick(R.id.back)
    void back() {
        onBackPressed();
    }

    @OnClick(R.id.forward)
    void forward() {
        startActivity(new Intent(this, FinalScreenActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);
        ButterKnife.bind(this);
        rvReviews.setLayoutManager(new LinearLayoutManager(this));
        IssuesAdapter mIssuesAdapter = new IssuesAdapter(this, getResources().getStringArray(R.array.issues));
        rvReviews.setAdapter(mIssuesAdapter);
    }
}
