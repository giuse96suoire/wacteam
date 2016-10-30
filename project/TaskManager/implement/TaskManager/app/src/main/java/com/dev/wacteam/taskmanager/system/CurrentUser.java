package com.dev.wacteam.taskmanager.system;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.database.RemoteUser;
import com.dev.wacteam.taskmanager.listener.OnChildEventListener;
import com.dev.wacteam.taskmanager.listener.OnGetDataListener;
import com.dev.wacteam.taskmanager.manager.SettingManager;
import com.dev.wacteam.taskmanager.model.Profile;
import com.dev.wacteam.taskmanager.model.Project;
import com.dev.wacteam.taskmanager.model.Setting;
import com.dev.wacteam.taskmanager.model.Task;
import com.dev.wacteam.taskmanager.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


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
    private CurrentUser() {
    }

    public static CurrentUser getInstance() {
        return ourInstance;
    }

    public static void setUserProfileAndSettingToLocal(User user, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if (user.getProfile() != null) {
            editor.putString(UID, user.getProfile().getUid());
            editor.putString(DISPLAY_NAME, user.getProfile().getDisplayName());
            editor.putString(DOB, user.getProfile().getDob());
            editor.putString(ADDRESS, user.getProfile().getAddress());
            editor.putString(PHONE_NUMBER, user.getProfile().getPhoneNumber());
            editor.putString(EMAIL, user.getProfile().getEmail());
            editor.commit();
        } else {
            System.out.println("PROFILE NULL ===============================>");
        }
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


    public static String createProject(Project project, Context context) {
        DatabaseReference db = FirebaseDatabase.getInstance()
                .getReference(LIST_PROJECT_REFERENCE).push();
        project.setmProjectId(db.getKey());
        project.setmLeaderId(CurrentUser.getUserProfileFromLocal(context).getProfile().getUid());
        db.setValue(project);
        return db.getKey();
    }

    public static void addFriend(User u, Context context) {
        int index = CurrentFriend.getFriendCount();
        CurrentUser.getReference(context).child(LIST_FRIEND_REFERENCE).child(index + "").setValue(u.getProfile().getUid());

    }

    public static void deleteProjectById(String id, Context context) {
        DatabaseReference db = FirebaseDatabase.getInstance()
                .getReference(LIST_PROJECT_REFERENCE)
                .child(id);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Project project = dataSnapshot.getValue(Project.class);
                String uid = CurrentUser.getUserProfileFromLocal(context).getProfile().getUid();
                if (project.getmLeaderId() != null && project.getmLeaderId().equals(uid)) {
                    db.setValue(null);///remove project
                } else {
                    db.child("mMembers").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                if (data.getValue(String.class).equals(uid)) {
                                    data.getRef().setValue(null); //remove user
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
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
                    if (u.getProfile().getEmail() != null) {
                        if (u.getProfile().getEmail().toLowerCase().contains(emailOrName.toLowerCase()) || u.getProfile().getDisplayName().toLowerCase().contains(emailOrName.toLowerCase())
                                && !u.getProfile().getEmail().equals(CurrentUser.getUserProfileFromLocal(context).getProfile().getEmail())) {
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


    public static void getAllProject(OnChildEventListener listener, Context context) {
        System.out.println("get all project =============>");
        DatabaseReference db = FirebaseDatabase.getInstance()
                .getReference(LIST_PROJECT_REFERENCE);

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Project project = dataSnapshot.getValue(Project.class);
                ArrayList<String> listMember = project.getmMembers();
                String leaderId = project.getmLeaderId();
                if (leaderId != null && leaderId.equals(CurrentUser.getUserProfileFromLocal(context).getProfile().getUid())) {
                    listener.onChildAdded(dataSnapshot, s);
                } else {
                    if (listMember != null) {
                        for (String memId : listMember) {
                            if (memId.equals(CurrentUser.getInstance().getUserProfileFromLocal(context).getProfile().getUid())) {
                                listener.onChildAdded(dataSnapshot, s);
                            }
                        }
                    }
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Project project = dataSnapshot.getValue(Project.class);
                boolean isYours = false;
                ArrayList<String> listMember = project.getmMembers();
                if (listMember != null) {
                    for (String memId : listMember) {
                        if (memId.equals(CurrentUser.getInstance().getUserProfileFromLocal(context).getProfile().getUid())) {
                            isYours = true;
                            listener.onChildChanged(dataSnapshot, s);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Project project = dataSnapshot.getValue(Project.class);
                ArrayList<String> listMember = project.getmMembers();
                if (listMember != null) {
                    for (String memId : listMember) {
                        if (memId.equals(CurrentUser.getInstance().getUserProfileFromLocal(context).getProfile().getUid())) {
                            listener.onChildRemoved(dataSnapshot);
                        }
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Project project = dataSnapshot.getValue(Project.class);
                ArrayList<String> listMember = project.getmMembers();
                if (listMember != null) {
                    for (String memId : listMember) {
                        if (memId.equals(CurrentUser.getInstance().getUserProfileFromLocal(context).getProfile().getUid())) {
                            listener.onChildMoved(dataSnapshot, s);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
                    System.out.println("project: " + project.getmTitle() + " ==============================>");
                    ArrayList<String> listMember = project.getmMembers();
                    if (listMember != null) {
                        for (String s : listMember) {
                            if (s.equals(CurrentUser.getInstance().getUserProfileFromLocal(context).getProfile().getUid())) {
                                listener.onSuccess(data);
                                System.out.println("project ok: " + project.getmTitle() + " ==============================>");

                            }
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

    static ArrayList<String> listFriend;

    public static void getAllFriend(OnGetDataListener listener, Context context) {
        listener.onStart();
        DatabaseReference db = CurrentUser.getReference(context).child(LIST_FRIEND_REFERENCE);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    listFriend = new ArrayList<String>();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        String friendId = data.getValue(String.class);
                        listFriend.add(friendId);
                    }
                }
                FirebaseDatabase.getInstance()
                        .getReference(RemoteUser.USER_LIST_CHILD)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    System.out.println("==================================================");
                                    System.out.println(data);
                                    System.out.println("==================================================");

//                                    User u = data.getValue(User.class);
//                                    if (listFriend.contains(u.getProfile().getUid())) {
//                                        listener.onSuccess(data);
//                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                listener.onFailed(databaseError);
                            }
                        });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }

    public static void setUserProfileToServer(Context context, User u) {
        CurrentUser.getReference(context).child("profile").setValue(u.getProfile());
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

    public static User getUserProfileFromLocal(Context context) {
        User user = new User();
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Profile profile = new Profile();
        user.setProfile(profile);
        user.getProfile().setUid(sharedPref.getString(UID, context.getResources().getString(R.string.default_uid)));
        user.getProfile().setDisplayName(sharedPref.getString(DISPLAY_NAME, context.getResources().getString(R.string.default_full_name)));
        user.getProfile().setEmail(sharedPref.getString(EMAIL, context.getResources().getString(R.string.default_email)));
        user.getProfile().setPhoneNumber(sharedPref.getString(PHONE_NUMBER, context.getResources().getString(R.string.default_phone_number)));
        user.getProfile().setAddress(sharedPref.getString(ADDRESS, context.getResources().getString(R.string.default_address)));
        user.getProfile().setDob(sharedPref.getString(DOB, context.getResources().getString(R.string.default_dob)));
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
                .getReference(RemoteUser.USER_LIST_CHILD + "/" + CurrentUser.getUserProfileFromLocal(context).getProfile().getUid());
    }


    public static void keepSync(boolean isKeep, Context context) { // keep local copy data
        if (isKeep) {
            CurrentUser.getInstance().getReference(context).keepSynced(true);
        } else {
            CurrentUser.getInstance().getReference(context).keepSynced(false);
        }
    }

    private static final String PROJECTS_REFERENCE = "projects";

    public static void updateTask(String projectId, ArrayList<Task> listTask) {
        FirebaseDatabase.getInstance()
                .getReference(LIST_PROJECT_REFERENCE)
                .child(projectId)
                .child("mTasks")
                .setValue(listTask);
    }

    public static void getProjectById(String id, OnChildEventListener listener, Context context) {
        System.out.println("GET PROJECT " + id);
        FirebaseDatabase.getInstance()
                .getReference(LIST_PROJECT_REFERENCE)
                .child(id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        listener.onChildChanged(dataSnapshot, "");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
//                .addChildEventListener(new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                        System.out.println(dataSnapshot.getRef().toString());
//                        listener.onChildAdded(dataSnapshot, s);
//                    }
//
//                    @Override
//                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                        listener.onChildChanged(dataSnapshot, s);
//
//                    }
//
//                    @Override
//                    public void onChildRemoved(DataSnapshot dataSnapshot) {
//                        listener.onChildRemoved(dataSnapshot);
//                    }
//
//                    @Override
//                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//                        listener.onChildMoved(dataSnapshot, s);
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
    }

    private static final String PROJECT_LOCAL = "project";

    private static void setProjectListToLocal(String id, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String project_arr_string = sharedPref.getString(PROJECT_LOCAL, "");
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(PROJECT_LOCAL, (project_arr_string.length() > 0) ? (project_arr_string + "," + id) : id);
        editor.commit();
    }

    private static String[] getProjectListFromLocal(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String project_arr_string = sharedPref.getString(PROJECT_LOCAL, "");
        if (project_arr_string.length() > 0) {
            return project_arr_string.split(",");
        }
        return null;
    }


}
