package com.dev.wacteam.taskmanager.model;

import java.util.ArrayList;
import java.util.Date;


/**
 * Created by giuse96suoire on 10/12/2016.
 */
public class Project {
    private String mProjectId;
    private String mTitle;
    private String mDescription;
    private double mComplete;
    private User mLeader;
    private ArrayList<String> mMembers;
    private ArrayList<Task> mTasks;
    private String mDeadline;
    private String mCreateDate;

    public void setmProjectId(String mProjectId) {
        this.mProjectId = mProjectId;
    }

    public void setmCreateDate(String mCreateDate) {
        this.mCreateDate = mCreateDate;
    }

    public String getmCreateDate() {
        return mCreateDate;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmComplete(double mComplete) {
        this.mComplete = mComplete;
    }

    public double getmComplete() {
        return mComplete;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setmLeader(User mLeader) {
        this.mLeader = mLeader;
    }

    public void setmMembers(ArrayList<String> mMembers) {
        this.mMembers = mMembers;
    }

    public void setmTasks(ArrayList<Task> mTasks) {
        this.mTasks = mTasks;
    }

    public void setmDeadline(String mDeadline) {
        this.mDeadline = mDeadline;
    }

    public String getmProjectId() {

        return mProjectId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public User getmLeader() {
        return mLeader;
    }

    public ArrayList<String> getmMembers() {
        return mMembers;
    }

    public ArrayList<Task> getmTasks() {
        return mTasks;
    }

    public String getmDeadline() {
        return mDeadline;
    }
}
