package com.dev.wacteam.taskmanager.database;

import com.dev.wacteam.taskmanager.model.User;

/**
 * Created by giuse96suoire on 10/16/2016.
 */

public class RemoteUser extends RemoteDBManager implements BaseModel {

    private static final String USER_CHILD = "users";
    private static final String USER_LIST_CHILD = USER_CHILD + "/list";
    private static final String USER_STATISTIC_CHILD = USER_CHILD + "/statistic";
    
    public static void mCreateUser(User user) {

    }

    @Override
    public void mCreate(Object o) {
        User user = (User) o;

    }

    @Override
    public void mUpdate(Object o) {
        User user = (User) o;

    }

    @Override
    public void mDelete(Object o) {
        User user = (User) o;

    }

    @Override
    public void mReset(Object o) {
        User user = (User) o;

    }

    @Override
    public Object mFind(String key) {
        return null;
    }
}

