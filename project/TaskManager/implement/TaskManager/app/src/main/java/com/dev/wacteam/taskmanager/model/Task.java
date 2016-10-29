package com.dev.wacteam.taskmanager.model;

import java.util.ArrayList;
import java.util.Date;


public class Task {
    public Task() {

    }

    private String mTaskId;
    private String mTitle;
    private String mDescription;
    private Date mDeadline;
    private ArrayList<User> mExecutors;

    public void setmTaskId(String mTaskId) {
        this.mTaskId = mTaskId;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setmDeadline(Date mDeadline) {
        this.mDeadline = mDeadline;
    }

    public void setmExecutors(ArrayList<User> mExecutors) {
        this.mExecutors = mExecutors;
    }

    public String getmTaskId() {

        return mTaskId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public Date getmDeadline() {
        return mDeadline;
    }

    public ArrayList<User> getmExecutors() {
        return mExecutors;
    }
}
