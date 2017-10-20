package com.rudainc.passengercontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IssuesActivity extends BaseActivity implements IssuesAdapter.IssueAdapterOnClickHandler {

    private static final String EXTRA_DATA = "data";
    @BindView(R.id.rvIssues)
    RecyclerView rvReviews;

    ArrayList<Integer> issues = new ArrayList<>();


    @OnClick(R.id.forward)
    void forward() {
        doNext();
        Answers.getInstance().logCustom(new CustomEvent(getString(R.string.event_go_to_final)));
    }

    public void doNext() {
        if (!issues.isEmpty()) {
            Intent intent = new Intent(this, FinalScreenActivity.class);
            intent.putIntegerArrayListExtra(EXTRA_DATA, issues);
            startActivity(intent);
        } else
            Toast.makeText(this, R.string.choose_one, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_choose);
        rvReviews.setLayoutManager(new LinearLayoutManager(this));
        IssuesAdapter mIssuesAdapter = new IssuesAdapter(this, getResources().getStringArray(R.array.issues), this);
        rvReviews.setAdapter(mIssuesAdapter);
    }

    @Override
    public void onClick(int adapterPosition, boolean add) {
        if (add)
            issues.add(adapterPosition);
        else
            issues.remove(issues.indexOf(adapterPosition));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        menu.getItem(0).setVisible(false);
        menu.getItem(1).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.arrow_forward) doNext();

        return super.onOptionsItemSelected(item);
    }
}
