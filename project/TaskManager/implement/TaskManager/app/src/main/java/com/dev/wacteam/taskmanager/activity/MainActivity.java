package com.dev.wacteam.taskmanager.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.fragment.AboutUsFragment;
import com.dev.wacteam.taskmanager.fragment.CreateProject;
import com.dev.wacteam.taskmanager.fragment.FriendFragment;
import com.dev.wacteam.taskmanager.fragment.HomeFragment;
import com.dev.wacteam.taskmanager.fragment.ProfileFragment;
import com.dev.wacteam.taskmanager.fragment.ProjectDetailFragment;
import com.dev.wacteam.taskmanager.fragment.ProjectFragment;
import com.dev.wacteam.taskmanager.fragment.SettingFragment;
import com.dev.wacteam.taskmanager.fragment.TodayFragment;
import com.dev.wacteam.taskmanager.listener.OnGetDataListener;
import com.dev.wacteam.taskmanager.manager.SettingManager;
import com.dev.wacteam.taskmanager.model.Project;
import com.dev.wacteam.taskmanager.model.User;
import com.dev.wacteam.taskmanager.system.CurrentFriend;
import com.dev.wacteam.taskmanager.system.CurrentProject;
import com.dev.wacteam.taskmanager.system.CurrentUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ProfileFragment.OnFragmentInteractionListener,
        CreateProject.OnFragmentInteractionListener,
        ProjectDetailFragment.OnFragmentInteractionListener,
        FriendFragment.OnFragmentInteractionListener, SettingFragment.OnFragmentInteractionListener,
        HomeFragment.OnFragmentInteractionListener, ProjectFragment.OnFragmentInteractionListener,
        TodayFragment.OnFragmentInteractionListener, AboutUsFragment.OnFragmentInteractionListener {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private TextView mTvUserFullName, mTvUserEmail;
    private MenuItem mMiNotification, mMiSound, mMiMessage;

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAllProject();
        getAllFriend();
        if (!CurrentUser.isLogined(getApplicationContext())) {
            mGoToActivity(LoginActivity.class);
        }
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.content_main) != null) {
            if (savedInstanceState != null) {
                return;
            }
            HomeFragment blankFragment1 = new HomeFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.content_main, blankFragment1).commit();
        }
        init();


    }

    private void getAllProject() {
        CurrentUser.getAllProject(new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                CurrentProject.addProject(data.getValue(Project.class));
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        }, getApplicationContext());
    }

    private void getAllFriend() {
        CurrentUser.getAllFriend(new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                CurrentFriend.addFriend(data.getValue(User.class));
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        }, getApplicationContext());
    }

    private DrawerLayout mDrawer;

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//                mDisplayQuickCreateDialog();
//            }
//        });

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        NavigationView navNotifyView = (NavigationView) findViewById(R.id.nav_notification);
        navNotifyView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();


                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        View header = navigationView.getHeaderView(0);
        mTvUserFullName = (TextView) header.findViewById(R.id.tv_userFullName);
        mTvUserEmail = (TextView) header.findViewById(R.id.tv_userEmail);
        User user = CurrentUser.getUserProfileFromLocal(getApplicationContext());
        mUpdateUserUI(user);
    }

    public void mUpdateUserUI(User user) {
        mTvUserEmail.setText(user.getProfile().getEmail());
        mTvUserFullName.setText(user.getProfile().getDisplayName());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        mMiNotification = menu.findItem(R.id.action_notification);
//        mMiMessage = menu.findItem(R.id.action_message);
        mMiSound = menu.findItem(R.id.action_sound);
        if (SettingManager.isHasNotification(getApplicationContext())) {
            changeNotificationIcon(true);
        }
        if (!SettingManager.isSound(getApplicationContext())) {
            changeSoundIcon(false);
        }
        return true;
    }

    public void changeNotificationIcon(boolean isNotify) {
        if (isNotify) {
            mMiNotification.setIcon(R.drawable.ic_notification_active_24);
            SettingManager.setIsHasNotification(getApplicationContext(), isNotify);

        } else {
            mMiNotification.setIcon(R.drawable.ic_notification_24);
            SettingManager.setIsHasNotification(getApplicationContext(), isNotify);

        }
    }

    public void changeSoundIcon(boolean isSound) {
        if (isSound) {
            mMiSound.setIcon(R.drawable.ic_volume_up_white_24dp);
            SettingManager.setIsSound(getApplicationContext(), isSound);

        } else {
            mMiSound.setIcon(R.drawable.ic_volume_off_white_24dp);
            SettingManager.setIsSound(getApplicationContext(), isSound);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_quick_create) {
            mDisplayQuickCreateDialog();
            return true;
        }
        if (id == R.id.action_notification) {
            changeNotificationIcon(false);
            mDrawer.openDrawer(Gravity.RIGHT);

            return true;
        }
        if (id == R.id.action_sound) {
            if (!SettingManager.isSound(getApplicationContext())) {
                changeSoundIcon(true);
            } else {
                changeSoundIcon(false);
            }

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void callFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content_main, fragment);
        transaction.commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) { // camera fragment
            callFragment(new ProfileFragment());
            setTitle(R.string.title_profile_fragment);
        } else if (id == R.id.nav_friend) {
            callFragment(new FriendFragment());
            setTitle(R.string.title_friend_fragment);

        } else if (id == R.id.nav_setting) {
            callFragment(new SettingFragment());
            setTitle(R.string.title_setting_fragment);

        } else if (id == R.id.nav_allProject) {
            callFragment(new ProjectFragment());
            setTitle(R.string.title_all_project_fragment);

//        } else if (id == R.id.nav_today) {
//            callFragment(new TodayFragment());
//            setTitle(R.string.title_all_today_fragment);

        } else if (id == R.id.nav_home) {
            callFragment(new HomeFragment());
            setTitle(R.string.title_home_fragment);

        } else if (id == R.id.nav_aboutUs) {
            callFragment(new AboutUsFragment());
            setTitle(R.string.title_aboutUs_fragment);
        } else if (id == R.id.nav_signOut) {
            FirebaseAuth.getInstance().signOut();
            CurrentUser.setLogined(false, getApplicationContext());
            mGoToActivity(LoginActivity.class);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void mGoToActivity(Class c) {
        Intent intent = new Intent(getApplicationContext(), c);
        startActivity(intent);
        this.finish();

    }

    private void mDisplayQuickCreateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.quick_create_project, null);
        builder.setTitle(R.string.quick_create)
                .setView(view)
                .setPositiveButton(R.string.create_project, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, R.string.create_project, Toast.LENGTH_LONG).show();

                        EditText project_name = (EditText) view.findViewById(R.id.et_projectName);
                        EditText project_descipt = (EditText) view.findViewById(R.id.et_project_description);
                        Project p = new Project();
                        p.setmTitle(project_name.getText().toString());
                        p.setmDescription(project_descipt.getText().toString());
                        String projectId = CurrentUser.createProject(p, getApplicationContext());
                        Bundle args = new Bundle();
                        args.putString("projectId", projectId);

                        ProjectDetailFragment f = new ProjectDetailFragment();
                        f.setArguments(args);
                        callFragment(f);
                        setTitle(project_name.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel_create_project, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, R.string.cancel_create_project, Toast.LENGTH_LONG).show();

                    }
                });
        AlertDialog dialog = builder.create();

        dialog.show();


    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //
    }
}
