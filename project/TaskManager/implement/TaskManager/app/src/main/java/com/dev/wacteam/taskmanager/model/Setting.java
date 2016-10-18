package com.dev.wacteam.taskmanager.model;


/**
 * Created by giuse96suoire on 10/12/2016.
 */
public class Setting{
    public Setting(){

    }
    private boolean mAutoAcceptFriend = false;
    private boolean mAutoAcceptTask = false;
    private boolean mAutoAcceptProject = false;

    public void setmAutoAcceptFriend(boolean mAutoAcceptFriend) {
        this.mAutoAcceptFriend = mAutoAcceptFriend;
    }

    public void setmAutoAcceptTask(boolean mAutoAcceptTask) {
        this.mAutoAcceptTask = mAutoAcceptTask;
    }

    public void setmAutoAcceptProject(boolean mAutoAcceptProject) {
        this.mAutoAcceptProject = mAutoAcceptProject;
    }

    public boolean ismAutoAcceptFriend() {

        return mAutoAcceptFriend;
    }

    public boolean ismAutoAcceptTask() {
        return mAutoAcceptTask;
    }

    public boolean ismAutoAcceptProject() {
        return mAutoAcceptProject;
    }
}
