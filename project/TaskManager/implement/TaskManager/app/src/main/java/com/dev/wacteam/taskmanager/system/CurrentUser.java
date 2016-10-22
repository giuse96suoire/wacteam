package com.dev.wacteam.taskmanager.system;

import android.content.Context;
import android.content.SharedPreferences;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.database.RemoteUser;
import com.dev.wacteam.taskmanager.listener.OnGetDataListener;
import com.dev.wacteam.taskmanager.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by huynh.mh on 10/18/2016.
 */
public class CurrentUser extends User {
    private static CurrentUser ourInstance = new CurrentUser();
    public static boolean isNotNull = false;
    private static final String DISPLAY_NAME = "display_name";
    private static final String EMAIL = "email";
    private static final String EMAIL_VERIFIED = "email_verified";
    private static final String UID = "uid";
    private static final String PHOTO_URL = "photo_url";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String ADDRESS = "address";
    private static final String DOB = "dob";
    private static final String GENDER = "gender";
    private static final String PROVIDER_ID = "provider_id";
//    public static boolean isLogined = false;

    public static CurrentUser getInstance() {
        return ourInstance;
    }

    public static void setInfo(User user, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(DISPLAY_NAME, user.getDisplayName());
        editor.putString(EMAIL, user.getEmail());
        editor.commit();
//        CurrentUser.getInstance().setEmail(user.getEmail());
//        CurrentUser.getInstance().setDisplayName(user.getDisplayName());
//        CurrentUser.getInstance().setEmailVerified(user.isEmailVerified());
//        CurrentUser.getInstance().setProviderId(user.getProviderId());
//        CurrentUser.getInstance().setUid(user.getUid());
//        CurrentUser.getInstance().setPhotoUrl(user.getPhotoUrl());
//        CurrentUser.getInstance().setDob(user.getDob());
//        CurrentUser.getInstance().setAddress(user.getAddress());
//        CurrentUser.getInstance().setListFriend(user.getListFriend());
//        CurrentUser.getInstance().setSetting(user.getSetting());
//        CurrentUser.getInstance().setListProject(user.getListProject());
//        CurrentUser.getInstance().setPhoneNumber(user.getPhoneNumber());
//        CurrentUser.getInstance().setGender(user.getGender());
//        isNotNull = true;

    }

    private void writeToLocal() {

    }

    public static void resetInfo() {
        CurrentUser.getInstance().setEmail(null);
        CurrentUser.getInstance().setDisplayName(null);
        CurrentUser.getInstance().setProviderId(null);
        CurrentUser.getInstance().setUid(null);
        CurrentUser.getInstance().setPhotoUrl(null);
        CurrentUser.getInstance().setDob(null);
        CurrentUser.getInstance().setAddress(null);
        CurrentUser.getInstance().setListFriend(null);
        CurrentUser.getInstance().setSetting(null);
        CurrentUser.getInstance().setListProject(null);
        CurrentUser.getInstance().setPhoneNumber(null);
        isNotNull = false;
    }

    public static void setUserInfoToServer() {
        User u = new User();
        u.setEmail(CurrentUser.getInstance().getEmail());
        u.setDisplayName(CurrentUser.getInstance().getDisplayName());
        CurrentUser.getInstance().getReference().setValue(u);
    }

    private static final String IS_LOGINED = "is_logined";

    public static void setLogined(boolean isLogined, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(IS_LOGINED, isLogined);
        editor.commit();
    }

    public static boolean isLogined(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getBoolean(IS_LOGINED, false);

    }

    public static User getUserInfo(Context context) {
//        CurrentUser.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                listener.onSuccess(dataSnapshot);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                listener.onFailed(databaseError);
//            }
//        });
        User user = new User();
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        user.setDisplayName(sharedPref.getString(DISPLAY_NAME, context.getResources().getString(R.string.default_full_name)));
        user.setEmail(sharedPref.getString(EMAIL,  context.getResources().getString(R.string.default_email)));
        user.setPhoneNumber(sharedPref.getString(PHONE_NUMBER,  context.getResources().getString(R.string.default_phone_number)));
        user.setAddress(sharedPref.getString(ADDRESS,  context.getResources().getString(R.string.default_address)));
        user.setDob(sharedPref.getString(DOB,  context.getResources().getString(R.string.default_dob)));
//        user.setDisplayName(sharedPref.getString(DISPLAY_NAME, "Your name"));
//        user.setDisplayName(sharedPref.getString(DISPLAY_NAME, "Your name"));
//        user.setDisplayName(sharedPref.getString(DISPLAY_NAME, "Your name"));
//        user.setDisplayName(sharedPref.getString(DISPLAY_NAME, "Your name"));
//        user.setDisplayName(sharedPref.getString(DISPLAY_NAME, "Your name"));
        return user;
    }

    public static DatabaseReference getReference() {
        return FirebaseDatabase.getInstance()
                .getReference(RemoteUser.USER_LIST_CHILD + "/" + CurrentUser.getInstance().getUid());
    }

    public static void keepSync(boolean isKeep) { // keep local copy data
        if (isKeep) {
            CurrentUser.getInstance().getReference().keepSynced(true);
        } else {
            CurrentUser.getInstance().getReference().keepSynced(false);
        }
    }

    private CurrentUser() {
    }
}
