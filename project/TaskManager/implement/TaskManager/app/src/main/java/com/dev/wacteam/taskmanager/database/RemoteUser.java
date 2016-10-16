package com.dev.wacteam.taskmanager.database;

import com.dev.wacteam.taskmanager.model.User;

/**
 * Created by giuse96suoire on 10/16/2016.
 */

public class RemoteUser extends RemoteDatabase implements BaseModel {

    public RemoteUser() {

    }

    private static final String USER_CHILD = "users";
    private static final String USER_LIST_CHILD = USER_CHILD + "/list";
    private static final String USER_STATISTIC_CHILD = USER_CHILD + "/statistic";


    @Override
    public void mCreate(Object o) {
        this.mWriteIfNotExist(USER_LIST_CHILD, o);
    }

    @Override
    public void mUpdate(Object o) {

        this.mWriteIfNotExist(USER_LIST_CHILD, o);

    }

    @Override
    public void mDelete(Object o) {
        this.mRemove(((User) o).getUid());
    }

    @Override
    public Object mFind(String key) {
//        User u = new User();
        return null;
    }
}

