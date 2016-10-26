package com.dev.wacteam.taskmanager.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.model.User;
import com.dev.wacteam.taskmanager.system.CurrentUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import layout.AboutUsFragment;
import layout.CreateProject;
import layout.FriendFragment;
import layout.HomeFragment;
import layout.ProfileFragment;
import layout.ProjectFragment;
import layout.SettingFragment;
import layout.TodayFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ProfileFragment.OnFragmentInteractionListener,
        CreateProject.OnFragmentInteractionListener,
        FriendFragment.OnFragmentInteractionListener, SettingFragment.OnFragmentInteractionListener,
        HomeFragment.OnFragmentInteractionListener, ProjectFragment.OnFragmentInteractionListener,
        TodayFragment.OnFragmentInteractionListener, AboutUsFragment.OnFragmentInteractionListener {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private TextView mTvUserFullName, mTvUserEmail;

    @Override
    protected void onPause() {
        super.onPause();
//        NotificationsManager.mNotify(this); //notify example
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!CurrentUser.isLogined(getApplicationContext())) {
            mGoToActivity(LoginActivity.class);
        }
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true   ); //set firebase can write data in offline
//        CurrentUser.getUserInfo(new OnGetDataListener() {
//            @Override
//            public void onStart() {
//                //do nothing
//            }
//
//            @Override
//            public void onSuccess(DataSnapshot data) {
//                if (data != null) {
//                    System.out.println("DATA NOT NULL IN MAIN");
//
//                    User user = data.getValue(User.class);
////                    if (user == null || user.getUid() == null || user.getDisplayName() == null || user.getEmail() == null) {
////                        System.out.println("USER NULL IN MAIN");
////
////                        mGoToActivity(LoginActivity.class);
////                    } else {
//////                        mTvUserFullName.setText(user.getDisplayName());
//////                        mTvUserEmail.setText(user.getEmail());
////                        Toast.makeText(getApplicationContext(), "Logined", Toast.LENGTH_LONG).show();
////                        //TODO: update some UI
////                    }
//                } else {
//                    System.out.println("DATA NULL IN MAIN");
//                    mGoToActivity(LoginActivity.class);
//                }
//            }

//            @Override
//            public void onFailed(DatabaseError databaseError) {
//                mGoToActivity(LoginActivity.class);
//
//            }
//        });
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



    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                mDisplayQuickCreateDialog();
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
        View header = navigationView.getHeaderView(0);
        mTvUserFullName = (TextView) header.findViewById(R.id.tv_userFullName);
        mTvUserEmail = (TextView) header.findViewById(R.id.tv_userEmail);
        User user = CurrentUser.getUserInfo(getApplicationContext());
        mUpdateUserUI(user);
    }

    public void mUpdateUserUI(User user) {
        mTvUserEmail.setText(user.getEmail());
        mTvUserFullName.setText(user.getDisplayName());
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

        } else if (id == R.id.nav_today) {
            callFragment(new TodayFragment());
            setTitle(R.string.title_all_today_fragment);

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
        builder.setTitle(R.string.ask_user_create)
                .setView(view)
                .setPositiveButton(R.string.create_project, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, R.string.create_project, Toast.LENGTH_LONG).show();
                        Bundle args = new Bundle();
                        EditText project_name = (EditText) view.findViewById(R.id.et_projectName);
                        args.putString(getString(R.string.project_name_label), project_name.getText().toString());
                        CreateProject createProjectFragment = new CreateProject();
                        createProjectFragment.setArguments(args);
                        callFragment(createProjectFragment);
                        setTitle(R.string.title_create_project_fragment);
                    }
                })
                .setNegativeButton(R.string.cancel_create_project, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, R.string.cancel_create_project, Toast.LENGTH_LONG).show();

                    }
                });
        AlertDialog dialog = builder.create();
        Spinner chooseProject = (Spinner) view.findViewById(R.id.sp_chooseProject);
        EditText projectName = (EditText) view.findViewById(R.id.et_projectName);
        ImageView project = (ImageView) view.findViewById(R.id.iv_project);
        ImageView task = (ImageView) view.findViewById(R.id.iv_task);
        project.setBackgroundColor(Color.rgb(120, 133, 224));
        String[] data = new String[]{"Project 1", "Project 2", "Project 3"};
        chooseProject.setAdapter(new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, data));
        project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectName.setVisibility(View.VISIBLE);
                chooseProject.setVisibility(View.GONE);
                project.setBackgroundColor(Color.rgb(120, 133, 224));
                task.setBackgroundColor(Color.WHITE);

            }
        });
        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseProject.setVisibility(View.VISIBLE);
                projectName.setVisibility(View.GONE);
                task.setBackgroundColor(Color.rgb(120, 133, 224));
                project.setBackgroundColor(Color.WHITE);

            }
        });
        dialog.show();


    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //
    }
}
