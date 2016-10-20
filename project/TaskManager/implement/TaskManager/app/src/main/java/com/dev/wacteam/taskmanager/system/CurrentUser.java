package com.dev.wacteam.taskmanager.system;

import com.dev.wacteam.taskmanager.model.User;

/**
 * Created by huynh.mh on 10/18/2016.
 */
public class CurrentUser extends User {
    private static CurrentUser ourInstance = new CurrentUser();
    public static boolean isNotNull = false;


    public static CurrentUser getInstance() {
        return ourInstance;
    }

    public static void setInfo(User user) {
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
        CurrentUser.getInstance().setGender(user.getGender());
        isNotNull = true;
    }

    public static void resetInfo() {
        CurrentUser.getInstance().setEmail(null);
        CurrentUser.getInstance().setDisplayName(null);
        CurrentUser.getInstance().setProviderId(null);
        CurrentUser.getInstance().setUid(null);
        CurrentUser.getInstance().setPhotoUrl(null);
        CurrentUser.getInstance().setDob(null);
        CurrentUser.getInstance().setAddress(null);
        CurrentUser.getInstance().setListFriend(null);
        CurrentUser.getInstance().setSetting(null);
        CurrentUser.getInstance().setListProject(null);
        CurrentUser.getInstance().setPhoneNumber(null);
        isNotNull = false;
    }

    private CurrentUser() {
    }
}
