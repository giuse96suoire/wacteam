package com.dev.wacteam.taskmanager.database;

import com.dev.wacteam.taskmanager.listener.OnGetDataListener;
import com.dev.wacteam.taskmanager.model.User;

public class RemoteUser extends RemoteDatabase implements BaseModel {

    public RemoteUser() {

    }

    public static final String USER_CHILD = "users";
    public static final String USER_LIST_CHILD = USER_CHILD + "/list";
    public static final String USER_STATISTIC_CHILD = USER_CHILD + "/statistic";


    @Override
    public void mCreate(Object o) {
        this.mWriteIfNotExist(USER_LIST_CHILD + "/" + ((User) o).getProfile().getUid(), o);
    }

    @Override
    public void mUpdate(Object o) {

        this.mWrite(USER_LIST_CHILD, o);

    }

    @Override
    public void mDelete(Object o) {
        this.mRemove(USER_LIST_CHILD + "/" + ((User) o).getProfile().getUid());
    }

    @Override
    public void mFind(String key, OnGetDataListener listener) {
        User u = new User();
        this.mReadDataOnce(USER_LIST_CHILD + "/" + key, listener);
    }


}

