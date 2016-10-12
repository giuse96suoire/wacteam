package com.dev.wacteam.taskmanager.model;


import java.util.ArrayList;
import java.util.Date;

/**
 * Created by giuse96suoire on 10/12/2016.
 */
public abstract class UserAbstract {
    private String mFullName;
    private Date mDob;
    private String mUserId;
    private String mEmail;
    private String mAvatarUrl;
    private ArrayList<Project> mListProject;
    private Setting mSetting;


    public UserAbstract() {

    }


    public void setmSetting(Setting mSetting) {
        this.mSetting = mSetting;
    }

    public Setting getmSetting() {
        return mSetting;
    }

    public ArrayList<Project> getmListProject() {
        return mListProject;
    }

    public void setmListProject(ArrayList<Project> mListProject) {
        this.mListProject = mListProject;
    }

    public void setmFullName(String mFullName) {
        this.mFullName = mFullName;
    }

    public void setmDob(Date mDob) {
        this.mDob = mDob;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public void setmAvatarUrl(String mAvatarUrl) {
        this.mAvatarUrl = mAvatarUrl;
    }

    public String getmFullName() {
        return mFullName;
    }

    public Date getmDob() {
        return mDob;
    }

    public String getmUserId() {
        return mUserId;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmAvatarUrl() {
        return mAvatarUrl;
    }

    abstract ArrayList<Project> mGetAllProject(String userId);

    abstract ArrayList<Project> mGetAProject(String userId, String projectId);
}
