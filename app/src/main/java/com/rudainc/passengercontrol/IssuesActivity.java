package com.rudainc.passengercontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IssuesActivity extends BaseActivity implements IssuesAdapter.IssueAdapterOnClickHandler {

    private static final String EXTRA_DATA = "data";
    @BindView(R.id.rvIssues)
    RecyclerView rvReviews;

    ArrayList<String> issues = new ArrayList<>();


    @OnClick(R.id.forward)
    void forward() {
        Log.i("ISSUES", issues.size()+"");
        if(!issues.isEmpty()) {
            Intent intent = new Intent(this, FinalScreenActivity.class);
            intent.putStringArrayListExtra(EXTRA_DATA, issues);
            startActivity(intent);
        }else
            Toast.makeText(this,R.string.choose_one,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rvReviews.setLayoutManager(new LinearLayoutManager(this));
        IssuesAdapter mIssuesAdapter = new IssuesAdapter(this, getResources().getStringArray(R.array.issues), this);
        rvReviews.setAdapter(mIssuesAdapter);
    }

    @Override
    public void onClick(String issue, boolean add) {
        if (add)
            issues.add(issue);
        else
            issues.remove(issues.indexOf(issue));

    }
}
