package com.dev.wacteam.taskmanager.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.system.CurrentUser;

/**
 * Created by giuse96suoire on 10/24/2016.
 */

public class SettingManager {
    private static SharedPreferences sharedPref;
    private static final String SOUND = "sound";
    private static final String NOTIFICATION = "notification";
    private static final String AUTO_ACCEPT_FRIEND = "auto_friend";
    private static final String AUTO_ACCEPT_PROJECT = "auto_project";
    private static final String AUTO_BACKUP_DATA = "auto_backup";

    ///get
    public static boolean isSound(Context context) {
        sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getBoolean(SOUND, true);
    }

    public static boolean isAutoBackup(Context context) {
        sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getBoolean(AUTO_BACKUP_DATA, true);
    }

    public static boolean isNotify(Context context) {
        sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getBoolean(NOTIFICATION, true);
    }

    public static boolean isAutoAcceptFriend(Context context) {
        sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getBoolean(AUTO_ACCEPT_FRIEND, false);
    }

    public static boolean isAutoAcceptProject(Context context) {
        sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getBoolean(AUTO_ACCEPT_PROJECT, false);

    }

    ///set
    private static SharedPreferences.Editor editor;

    public static void setIsSound(Context context, boolean status) {
        sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putBoolean(SOUND, status);
        editor.commit();
        CurrentUser.setUserInfoToServer(context);
    }

    public static void setIsAutoBackup(Context context, boolean status) {
        sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putBoolean(AUTO_BACKUP_DATA, status);
        editor.commit();
        CurrentUser.setUserInfoToServer(context);

    }

    public static void setIsNotify(Context context, boolean status) {
        sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putBoolean(NOTIFICATION, status);
        editor.commit();
        CurrentUser.setUserInfoToServer(context);

    }

    public static void setIsAutoAcceptFriend(Context context, boolean status) {
        sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putBoolean(AUTO_ACCEPT_FRIEND, status);
        editor.commit();
        CurrentUser.setUserInfoToServer(context);

    }

    public static void setIsAutoAcceptProject(Context context, boolean status) {
        sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putBoolean(AUTO_ACCEPT_PROJECT, status);
        editor.commit();
        CurrentUser.setUserInfoToServer(context);

    }


}
