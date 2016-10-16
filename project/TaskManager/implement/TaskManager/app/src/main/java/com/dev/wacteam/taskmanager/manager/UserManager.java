package com.dev.wacteam.taskmanager.manager;

import android.content.Context;

import com.dev.wacteam.taskmanager.model.User;

/**
 * Created by giuse96suoire on 10/15/2016.
 */

public class UserManager {
    public static boolean mIsExistLocalUser(Context context) {
        UserPreferences userPreferences = new UserPreferences(context);
        User user = userPreferences.mGetLocalUser();
        return (user == null ? false : true);
    }

    public static void mSetLocalUserIfNotExist(User user, Context context) {
        if (!mIsExistLocalUser(context)) {
            UserPreferences userPreferences = new UserPreferences(context);
            userPreferences.mSetLocalUser(user);
        }
    }
    public static User mGetLocalUser(Context context){
        UserPreferences userPreferences = new UserPreferences(context);
        User user = userPreferences.mGetLocalUser();
        return user;
    }
}
