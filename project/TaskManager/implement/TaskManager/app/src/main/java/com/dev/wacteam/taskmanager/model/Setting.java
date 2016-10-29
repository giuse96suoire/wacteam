package com.dev.wacteam.taskmanager.model;



public class Setting{
    public Setting(){

    }
    private boolean mAutoAcceptFriend = false;
    private boolean mAutoBackupData = false;
    private boolean mAutoAcceptProject = true;
    private boolean mNotification = true;
    private boolean mSound = true;

    public void setmNotification(boolean mNotification) {
        this.mNotification = mNotification;
    }

    public void setmSound(boolean mSound) {
        this.mSound = mSound;
    }

    public boolean ismNotification() {
        return mNotification;
    }

    public boolean ismSound() {
        return mSound;
    }

    public void setmAutoAcceptFriend(boolean mAutoAcceptFriend) {
        this.mAutoAcceptFriend = mAutoAcceptFriend;
    }

    public void setmAutoBackupData(boolean mAutoBackupData) {
        this.mAutoBackupData = mAutoBackupData;
    }

    public void setmAutoAcceptProject(boolean mAutoAcceptProject) {
        this.mAutoAcceptProject = mAutoAcceptProject;
    }

    public boolean ismAutoAcceptFriend() {

        return mAutoAcceptFriend;
    }

    public boolean ismAutoBackupData() {
        return mAutoBackupData;
    }

    public boolean ismAutoAcceptProject() {
        return mAutoAcceptProject;
    }
}
