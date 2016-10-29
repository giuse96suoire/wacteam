package com.dev.wacteam.taskmanager.model;

import java.util.ArrayList;


public class Task {
    public Task() {

    }

    private String mTaskId;
    private String mTitle;
    private String mDescription;
    private String mDeadline;
    private ArrayList<String> mExecutors;
    private int mDayOfWeek;

    public void setmDayOfWeek(int mDayOfWeek) {
        this.mDayOfWeek = mDayOfWeek;
    }

    public int getmDayOfWeek() {
        return mDayOfWeek;
    }

    public void setmTaskId(String mTaskId) {
        this.mTaskId = mTaskId;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setmDeadline(String mDeadline) {
        this.mDeadline = mDeadline;
    }

    public void setmExecutors(ArrayList<String> mExecutors) {
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

    public String getmDeadline() {
        return mDeadline;
    }

    public ArrayList<String> getmExecutors() {
        return mExecutors;
    }
}
