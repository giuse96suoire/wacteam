package com.dev.wacteam.taskmanager.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.dev.wacteam.taskmanager.R;



public class SettingManager {
    private static SharedPreferences sharedPref;
    private static final String SOUND = "sound";
    private static final String NOTIFICATION = "notification";
    private static final String AUTO_ACCEPT_FRIEND = "auto_friend";
    private static final String AUTO_ACCEPT_PROJECT = "auto_project";
    private static final String AUTO_BACKUP_DATA = "auto_backup";
    private static final String IS_HAS_NOTIFICATION = "has_notification";

    ///get
    public static boolean isSound(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        System.out.println(sharedPref.getBoolean(SOUND, true));
        return sharedPref.getBoolean(SOUND, true);
    }

    ///get
    public static boolean isHasNotification(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getBoolean(IS_HAS_NOTIFICATION, false);
    }

    public static boolean isAutoBackup(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getBoolean(AUTO_BACKUP_DATA, true);
    }

    public static boolean isNotify(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getBoolean(NOTIFICATION, true);
    }

    public static boolean isAutoAcceptFriend(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getBoolean(AUTO_ACCEPT_FRIEND, false);
    }

    public static boolean isAutoAcceptProject(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getBoolean(AUTO_ACCEPT_PROJECT, false);

    }

    ///set
    private static SharedPreferences.Editor editor;

    public static void setIsSound(Context context, boolean status) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putBoolean(SOUND, status);
        editor.commit();

//        CurrentUser.setUserProfileToServer(context,null);
    }

    public static void setIsHasNotification(Context context, boolean status) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putBoolean(IS_HAS_NOTIFICATION, status);
        editor.commit();
    }

    public static void setIsAutoBackup(Context context, boolean status) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putBoolean(AUTO_BACKUP_DATA, status);
        editor.commit();
//        CurrentUser.setUserProfileToServer(context,null);

    }

    public static void setIsNotify(Context context, boolean status) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putBoolean(NOTIFICATION, status);
        editor.commit();
//        CurrentUser.setUserProfileToServer(context,null);

    }

    public static void setIsAutoAcceptFriend(Context context, boolean status) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putBoolean(AUTO_ACCEPT_FRIEND, status);
        editor.commit();
//        CurrentUser.setUserProfileToServer(context,null);


    }

    public static void setIsAutoAcceptProject(Context context, boolean status) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putBoolean(AUTO_ACCEPT_PROJECT, status);
        editor.commit();
//        CurrentUser.setUserProfileToServer(context,null);


    }


}
