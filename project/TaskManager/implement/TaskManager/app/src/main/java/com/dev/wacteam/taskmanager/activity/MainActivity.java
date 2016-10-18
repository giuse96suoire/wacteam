package com.dev.wacteam.taskmanager.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.database.RemoteUser;
import com.dev.wacteam.taskmanager.manager.EnumDefine;
import com.dev.wacteam.taskmanager.manager.NotificationsManager;
import com.dev.wacteam.taskmanager.manager.SettingsManager;
import com.dev.wacteam.taskmanager.model.User;
import com.dev.wacteam.taskmanager.system.CurrentUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import layout.FriendFragment;
import layout.HomeFragment;
import layout.ProfileFragment;
import layout.ProjectFragment;
import layout.SettingFragment;
import layout.TodayFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ProfileFragment.OnFragmentInteractionListener,
        FriendFragment.OnFragmentInteractionListener, SettingFragment.OnFragmentInteractionListener,
        HomeFragment.OnFragmentInteractionListener, ProjectFragment.OnFragmentInteractionListener, TodayFragment.OnFragmentInteractionListener {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private TextView mTvUserFullName, mTvUserEmail;

    @Override
    protected void onPause() {
        super.onPause();
        NotificationsManager.mNotify(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.content_main) != null) {
            if (savedInstanceState != null) {
                return;
            }
            HomeFragment blankFragment1 = new HomeFragment();

            getSupportFragmentManager().beginTransaction().add(R.id.content_main, blankFragment1).commit();
        }
        init();
        if (SettingsManager.INSTANCE.MODE.equals(EnumDefine.MODE.ONLINE.toString())) {
            mSwitchToOnlineMode();
            System.out.println("IS ONLINE MODE");
        } else {
            System.out.println("IS OFFLINE MODE");
        }




    }


    private void mSwitchToOnlineMode() {
        System.out.println("SWICH TO ONLINE MODE ========>");
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAuth.getCurrentUser() == null) {
                    System.out.println("USER NULL==========================>");
                    mUser = null;
                    mGoToActivity(LoginActivity.class);
                    mAuth.removeAuthStateListener(this);
                } else {
                    mUser = mAuth.getCurrentUser();
//                    mUpdateUserUI();
                }
            }
        });
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);


    }

    private void mUpdateUserUI() {
        mTvUserFullName = (TextView) findViewById(R.id.tv_userFullName);

        mTvUserEmail = (TextView) findViewById(R.id.tv_userEmail);

        if (mUser.getDisplayName() == null) {
            mTvUserFullName.setText("Your name here");
        } else {
            mTvUserFullName.setText(mUser.getDisplayName());
        }
        if (mUser.getEmail() == null) {
            mTvUserEmail.setText("Your email here");

        } else {
            mTvUserEmail.setText(mUser.getEmail());

        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
        } else if (id == R.id.nav_friend) {
            callFragment(new FriendFragment());
        } else if (id == R.id.nav_setting) {
            callFragment(new SettingFragment());
        } else if (id == R.id.nav_allProject) {
            callFragment(new ProjectFragment());
        } else if (id == R.id.nav_today) {
            callFragment(new TodayFragment());
        } else if (id == R.id.nav_home) {
            callFragment(new HomeFragment());
        } else if (id == R.id.nav_signOut) {
            if (SettingsManager.INSTANCE.MODE.equals(EnumDefine.MODE.ONLINE.toString())) {
                mAuth.signOut();
            } else {
                mGoToActivity(LoginActivity.class);
            }
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

    @Override
    public void onFragmentInteraction(Uri uri) {
        //
    }
}
