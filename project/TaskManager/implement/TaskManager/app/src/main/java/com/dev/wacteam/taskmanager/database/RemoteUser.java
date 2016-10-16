package com.dev.wacteam.taskmanager.database;

import com.dev.wacteam.taskmanager.model.User;

/**
 * Created by giuse96suoire on 10/16/2016.
 */

public class RemoteUser extends RemoteDBManager implements ModelInterface {

    private static final String USER_CHILD = "users";
    private static final String USER_LIST_CHILD = USER_CHILD + "/list";
    private static final String USER_STATISTIC_CHILD = USER_CHILD + "/statistic";

    public static void mCreateUser(User user) {

    }

    public void mUpdateUser(User user) {
        mCreateUser(user);
    }

    public static void mDeleteUser(User user) {
        mRemove(USER_LIST_CHILD);
    }

    public static void mResetUser() {

    }


    @Override
    public void create(Object o) {
        User user = (User) o;


    }

    @Override
    public void update(Object o) {
        User user = (User) o;

    }

    @Override
    public void delete(Object o) {
        User user = (User) o;

    }

    @Override
    public void reset(Object o) {
        User user = (User) o;

    }
}

