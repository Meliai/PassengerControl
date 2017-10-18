package com.rudainc.passengercontrol;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;


public class IssuesAdapter extends RecyclerView.Adapter<IssuesAdapter.IssuesAdapterViewHolder> {

    private final Context context;
    private String[] mIssuesList;
    private final IssueAdapterOnClickHandler mClickHandler;

    public IssuesAdapter(Context context, String[] list, IssueAdapterOnClickHandler clickHandler) {
        this.context = context;
        this.mIssuesList = list;
        mClickHandler = clickHandler;
    }


    class IssuesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mIssue;
        private CheckBox mCb;

        IssuesAdapterViewHolder(View view) {
            super(view);
            mIssue = (TextView) view.findViewById(R.id.tv_issue);
            mCb = (CheckBox)view.findViewById(R.id.cb_item);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            if(!mCb.isChecked()) {
                mClickHandler.onClick(mIssuesList[adapterPosition], true);
                mCb.setChecked(true);
            }else{
                mClickHandler.onClick(mIssuesList[adapterPosition], false);
                mCb.setChecked(false);
            }

        }
    }

    @Override
    public IssuesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_issue;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
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

    public interface IssueAdapterOnClickHandler {
        void onClick(String position, boolean add );
    }
}
