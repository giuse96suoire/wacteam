package com.dev.wacteam.taskmanager.system;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.database.RemoteUser;
import com.dev.wacteam.taskmanager.listener.OnGetDataListener;
import com.dev.wacteam.taskmanager.manager.SettingManager;
import com.dev.wacteam.taskmanager.model.Project;
import com.dev.wacteam.taskmanager.model.Setting;
import com.dev.wacteam.taskmanager.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
    private static final String LIST_PROJECT_REFERENCE = "projects/list";
    private static final String LIST_FRIEND_REFERENCE = "friends";
//    public static boolean isLogined = false;

    public static CurrentUser getInstance() {
        return ourInstance;
    }

    public static void setInfo(User user, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(UID, user.getUid());
        editor.putString(DISPLAY_NAME, user.getDisplayName());
        editor.putString(DOB, user.getDob());
        editor.putString(ADDRESS, user.getAddress());
        editor.putString(PHONE_NUMBER, user.getPhoneNumber());
        editor.putString(EMAIL, user.getEmail());
        editor.commit();
        if (user.getSetting() != null) {
            SettingManager.setIsAutoBackup(context, user.getSetting().ismAutoBackupData());
            SettingManager.setIsAutoAcceptProject(context, user.getSetting().ismAutoAcceptFriend());
            SettingManager.setIsNotify(context, user.getSetting().ismNotification());
            SettingManager.setIsSound(context, user.getSetting().ismSound());
            SettingManager.setIsAutoAcceptFriend(context, user.getSetting().ismAutoAcceptFriend());
        } else {

        }

        Toast.makeText(context, R.string.Update_profile_success, Toast.LENGTH_LONG).show();
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

    public static void createProject(Project project, Context context) {
        DatabaseReference db = FirebaseDatabase.getInstance()
                .getReference(LIST_PROJECT_REFERENCE).push();
        db.setValue(project);
    }

    public static void addFriend(User u, Context context) {
        DatabaseReference db = CurrentUser.getReference(context).child(LIST_FRIEND_REFERENCE);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long id = dataSnapshot.getChildrenCount();
                db.child(id + "").setValue(u.getUid());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void searchFriend(Context context, String emailOrName, OnGetDataListener listener) {
        listener.onStart();
        DatabaseReference db = FirebaseDatabase.getInstance()
                .getReference(RemoteUser.USER_LIST_CHILD);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    User u = data.getValue(User.class);
                    if (u.getEmail() != null) {
                        if (u.getEmail().toLowerCase().contains(emailOrName.toLowerCase()) || u.getDisplayName().toLowerCase().contains(emailOrName.toLowerCase())
                                && !u.getEmail().equals(CurrentUser.getUserInfo(context).getEmail())) {
                            listener.onSuccess(data);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }

    public static void getAllProject(OnGetDataListener listener, Context context) {
        System.out.println("get all project =============>");
        listener.onStart();
        DatabaseReference db = FirebaseDatabase.getInstance()
                .getReference(LIST_PROJECT_REFERENCE);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Project project = data.getValue(Project.class);
                    System.out.println("project: "+project.getmTitle()+" ==============================>");
                    ArrayList<String> listMember = project.getmMembers();
                    for (String s : listMember) {
                        if (s.equals(CurrentUser.getInstance().getUserInfo(context).getUid())) {
                            listener.onSuccess(data);
                            System.out.println("project ok: "+project.getmTitle()+" ==============================>");

                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }

    public static void getAllFriend(OnGetDataListener listener, Context context) {
        listener.onStart();
        DatabaseReference db = CurrentUser.getReference(context).child(LIST_FRIEND_REFERENCE);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listener.onSuccess(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }

    public static void setUserInfoToServer(Context context) {
        User u = CurrentUser.getUserInfo(context);

        CurrentUser.getInstance().getReference(context).setValue(u);
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
        User user = new User();
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        user.setUid(sharedPref.getString(UID, context.getResources().getString(R.string.default_uid)));
        user.setDisplayName(sharedPref.getString(DISPLAY_NAME, context.getResources().getString(R.string.default_full_name)));
        user.setEmail(sharedPref.getString(EMAIL, context.getResources().getString(R.string.default_email)));
        user.setPhoneNumber(sharedPref.getString(PHONE_NUMBER, context.getResources().getString(R.string.default_phone_number)));
        user.setAddress(sharedPref.getString(ADDRESS, context.getResources().getString(R.string.default_address)));
        user.setDob(sharedPref.getString(DOB, context.getResources().getString(R.string.default_dob)));
        Setting setting = new Setting();
        setting.setmAutoBackupData(SettingManager.isAutoBackup(context));
        setting.setmSound(SettingManager.isSound(context));
        setting.setmAutoAcceptFriend(SettingManager.isAutoAcceptFriend(context));
        setting.setmNotification(SettingManager.isNotify(context));
        setting.setmAutoAcceptProject(SettingManager.isAutoAcceptProject(context));
        user.setSetting(setting);
        return user;
    }

    public static DatabaseReference getReference(Context context) {
        return FirebaseDatabase.getInstance()
                .getReference(RemoteUser.USER_LIST_CHILD + "/" + CurrentUser.getInstance().getUserInfo(context).getUid());
    }


    public static void keepSync(boolean isKeep, Context context) { // keep local copy data
        if (isKeep) {
            CurrentUser.getInstance().getReference(context).keepSynced(true);
        } else {
            CurrentUser.getInstance().getReference(context).keepSynced(false);
        }
    }

    private CurrentUser() {
    }
}
