package com.dev.wacteam.taskmanager.database;

import com.dev.wacteam.taskmanager.listener.OnGetDataListener;


public interface BaseModel {
    public void mCreate(Object o);

    public void mUpdate(Object o);

    public void mDelete(Object o);

    public void mFind(String key, OnGetDataListener listener);
}
