package com.dev.wacteam.taskmanager.database;

/**
 * Created by giuse96suoire on 10/16/2016.
 */

public interface BaseModel {
    public void mCreate(Object o);

    public void mUpdate(Object o);

    public void mDelete(Object o);

    public Object mFind(String key);
}
