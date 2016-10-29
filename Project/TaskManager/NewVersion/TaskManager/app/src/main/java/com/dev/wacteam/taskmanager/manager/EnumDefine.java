package com.dev.wacteam.taskmanager.manager;

/**
 * Created by cuonghmse61977 on 10/29/2016.
 */

public class EnumDefine {
    public static final int PICK_IMAGE_CODE = 0;
    public static final int TIME_OUT = 20; //sencond
    public static final int LOW_CONNECTION = 10; //if time login > 10 sencond show alert low connection
    public static final int TRY_RECONNECT = 15;
    public static final int DISCONNECT = 18;
    public enum MODE {
        ONLINE, OFFLINE;
    }

    public enum FIREBASE_CHILD {
        USERS, PROJECTS, STATISTIC
    }
}
