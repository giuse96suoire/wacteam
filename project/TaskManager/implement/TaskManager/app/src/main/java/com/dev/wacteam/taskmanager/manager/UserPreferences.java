package com.dev.wacteam.taskmanager.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.dev.wacteam.taskmanager.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by giuse96suoire on 10/15/2016.
 */


public class UserPreferences {
    Context context;
    private final String USER_PREFERENCES_KEY = "user_preferences";
    private final String DISPLAY_NAME = "display_name";
    private final String PHOTO_URL = "photo_url";
    private final String DOB = "dob";
    private final String EMAIL = "email";
    private final String EMAIL_VERIFIED = "email_verified";
    private final String PROVIDER_ID = "provider_id";
    private final String UID = "uid";
    private final String PHONE_NUMBER = "phone_number";
    private final String ADDRESS = "address";
    private final String DEFAULT_AVATAR = "";
    SharedPreferences sharedPreferences;

    public UserPreferences(Context context) {
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences(USER_PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    public User mGetLocalUser() {
        User user = new User();
        user.setDisplayName(sharedPreferences.getString(DISPLAY_NAME, "false"));
        user.setEmail(sharedPreferences.getString(EMAIL, "false"));
        user.setEmailVerified(Boolean.parseBoolean(sharedPreferences.getString(EMAIL_VERIFIED, "false")));
        user.setPhotoUrl(Uri.parse(sharedPreferences.getString(PHOTO_URL, "false")));
        user.setProviderId(sharedPreferences.getString(PROVIDER_ID, "false"));
        user.setUid(sharedPreferences.getString(UID, "false"));
        user.setPhoneNumber(sharedPreferences.getString(PHONE_NUMBER, "false"));
        user.setAddress(sharedPreferences.getString(ADDRESS, "flase"));
        try {
            user.setDob(new SimpleDateFormat("dd/MM/yyyy").parse(sharedPreferences.getString(DOB, "02/09/1975")));
        } catch (ParseException e) {
            user.setDob(new Date());
        }
        if (user.getDisplayName().equals("false") || user.getEmail().equals("false")) {
            return null;
        }
        return user;
    }

    public void mSetLocalUser(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DISPLAY_NAME, user.getDisplayName());
        editor.putString(EMAIL, user.getEmail());
        editor.putString(PHOTO_URL, user.getPhotoUrl().toString());
        editor.putBoolean(EMAIL_VERIFIED, user.isEmailVerified());
        editor.putString(DOB, user.getDob().toString());
        editor.putString(PROVIDER_ID, user.getProviderId());
        editor.putString(UID, user.getUid());
        editor.putString(PHONE_NUMBER,user.getPhoneNumber());
        editor.putString(ADDRESS, user.getAddress());
    }
}

