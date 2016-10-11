package com.dev.wacteam.taskmanager.manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by giuse96suoire on 10/11/2016.
 */
public class NetworkManager {
    public static boolean mIsConnectToNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null) {
            return true;
        }
        return false;
    }
}
