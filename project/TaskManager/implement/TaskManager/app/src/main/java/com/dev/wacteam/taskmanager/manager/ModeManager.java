package com.dev.wacteam.taskmanager.manager;

/**
 * Created by giuse96suoire on 10/11/2016.
 */
public class ModeManager {
    public static void mSwitchMode(EnumDefine.MODE mode) {
        SettingsManager.INSTANCE.MODE = (mode.equals(EnumDefine.MODE.ONLINE)) ? EnumDefine.MODE.ONLINE.toString() : EnumDefine.MODE.OFFLINE.toString();
    }
}
