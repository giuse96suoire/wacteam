package com.dev.wacteam.taskmanager.manager;

import android.content.Context;
import android.content.Intent;

import com.dev.wacteam.taskmanager.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by giuse96suoire on 10/11/2016.
 */
public class ModeManager {
    public static void mSwitchMode(EnumDefine.MODE mode) {
        SettingsManager.INSTANCE.MODE = (mode.equals(EnumDefine.MODE.ONLINE)) ? EnumDefine.MODE.ONLINE.toString() : EnumDefine.MODE.OFFLINE.toString();
    }
}
