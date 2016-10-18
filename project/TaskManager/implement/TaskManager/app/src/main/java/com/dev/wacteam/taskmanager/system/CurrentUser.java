package com.dev.wacteam.taskmanager.system;

import com.dev.wacteam.taskmanager.model.User;

/**
 * Created by huynh.mh on 10/18/2016.
 */
public class CurrentUser extends User {
    private static CurrentUser ourInstance = new CurrentUser();

    public static CurrentUser getInstance() {
        return ourInstance;
    }
    public static void setInfor(User user){
        CurrentUser.getInstance().setEmail(user.getEmail());
        CurrentUser.getInstance().setDisplayName(user.getDisplayName());
        CurrentUser.getInstance().setEmailVerified(user.isEmailVerified());
        CurrentUser.getInstance().setProviderId(user.getProviderId());
        CurrentUser.getInstance().setUid(user.getUid());
        CurrentUser.getInstance().setPhotoUrl(user.getPhotoUrl());
        CurrentUser.getInstance().setDob(user.getDob());
        CurrentUser.getInstance().setAddress(user.getAddress());
        CurrentUser.getInstance().setListFriend(user.getListFriend());
        CurrentUser.getInstance().setSetting(user.getSetting());
        CurrentUser.getInstance().setListProject(user.getListProject());
        CurrentUser.getInstance().setPhoneNumber(user.getPhoneNumber());
    }
    private CurrentUser() {
    }
}
