package com.rudainc.passengercontrol.model;

public class IssuesModel {
    private int index;

    private String issue;

    public IssuesModel(int index, String issue) {
        this.index = index;
        this.issue = issue;
    }

    public int getIndex() {
        return index;
    }

    public String getIssue() {
        return issue;
    }
}
