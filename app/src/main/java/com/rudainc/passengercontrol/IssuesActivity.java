package com.rudainc.passengercontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IssuesActivity extends AppCompatActivity implements IssuesAdapter.IssueAdapterOnClickHandler{

    private static final String EXTRA_DATA = "data";
    @BindView(R.id.rvIssues)
    RecyclerView rvReviews;

    ArrayList<String> issues = new ArrayList<>();

    @OnClick(R.id.back)
    void back() {
        onBackPressed();
    }

    @OnClick(R.id.forward)
    void forward() {
        Intent intent = new Intent(this, FinalScreenActivity.class);
        intent.putStringArrayListExtra(EXTRA_DATA, issues);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);
        ButterKnife.bind(this);
        rvReviews.setLayoutManager(new LinearLayoutManager(this));
        IssuesAdapter mIssuesAdapter = new IssuesAdapter(this, getResources().getStringArray(R.array.issues),this);
        rvReviews.setAdapter(mIssuesAdapter);
    }

    @Override
    public void onClick(String issue) {
       issues.add(issue);
    }
}
