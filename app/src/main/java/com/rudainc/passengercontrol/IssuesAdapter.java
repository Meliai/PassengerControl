package com.rudainc.passengercontrol;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;


public class IssuesAdapter extends RecyclerView.Adapter<IssuesAdapter.IssuesAdapterViewHolder> {

    private final Context context;
    private String[] mIssuesList;

    public IssuesAdapter(Context context, String[] list) {
        this.context = context;
        this.mIssuesList = list;
    }


    class IssuesAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView mIssue;

        IssuesAdapterViewHolder(View view) {
            super(view);
            mIssue = (TextView) view.findViewById(R.id.tv_issue);

        }
    }

    @Override
    public IssuesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_issue;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new IssuesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IssuesAdapterViewHolder issuesAdapterViewHolder, int position) {

        issuesAdapterViewHolder.mIssue.setText(mIssuesList[position]);

    }

    @Override
    public int getItemCount() {
        if (null == mIssuesList) return 0;
        return mIssuesList.length;
    }
}
