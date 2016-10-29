package com.dev.wacteam.taskmanager.database;

import com.dev.wacteam.taskmanager.listener.OnGetDataListener;

/**
 * Created by Ngoc on 10/29/2016.
 */

public interface BaseModel {
    public void mCreate(Object o);

    public void mUpdate(Object o);

    public void mDelete(Object o);

    public void mFind(String key, OnGetDataListener listener);
}
