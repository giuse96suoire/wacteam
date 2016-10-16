package com.dev.wacteam.taskmanager.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.dialog.DialogAlert;
import com.dev.wacteam.taskmanager.manager.EnumDefine;
import com.dev.wacteam.taskmanager.manager.ModeManager;
import com.dev.wacteam.taskmanager.manager.NetworkManager;
import com.dev.wacteam.taskmanager.manager.SettingsManager;
import com.dev.wacteam.taskmanager.model.User;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {
    private CallbackManager mCallbackManager;
    private LoginButton mLoginButton;
    private FirebaseAuth mAuth;
    private Button mSignUp, mSignIn;
    private EditText mEmail;
    private EditText mPassword;
    private ProgressDialog mDialog;
    private ProgressBar mProgressBar;
    private RelativeLayout mLoginMain;
    private TextView mTvAlertInternet;
    private AlertDialog mAlertDialog;
    private TextView mTvCurrentMode;
    private TextView mTvForgotPass;
    private FrameLayout mFlProgressFrame;
    private final int TIME_OUT = 2000;
    private TextView mTvStatus;
    static final String ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ACTION);
        this.registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(mReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        if (NetworkManager.mIsConnectToNetwork(LoginActivity.this) && mIsCurrentUser()) { // if has network connection
            mGoToActivity(MainActivity.class); // go to main activity
            this.finish();
        } else {
            // check if user is login in offline mode
        }

        setContentView(R.layout.activity_login);
        mLoginMain = (RelativeLayout) findViewById(R.id.login_main);
        mSignUp = (Button) findViewById(R.id.btn_signUp);
        mSignIn = (Button) findViewById(R.id.btn_signIn);
        mFlProgressFrame = (FrameLayout) findViewById(R.id.progressFrame);
        mEmail = (EditText) findViewById(R.id.et_email);
        mPassword = (EditText) findViewById(R.id.et_password);
        mTvStatus = (TextView) findViewById(R.id.tv_status);
        mTvCurrentMode = (TextView) findViewById(R.id.tv_currentMode);
        mTvForgotPass = (TextView) findViewById(R.id.tv_forgotPass);
        mTvCurrentMode.setText(SettingsManager.INSTANCE.MODE + " MODE");
        mTvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsValidEmail(mEmail.getText().toString())) {
                    mResetPassword(mEmail.getText().toString());
                } else {
                    Toast.makeText(LoginActivity.this, "Email invalid!", Toast.LENGTH_LONG).show();
                }
            }
        });
        mTvCurrentMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SettingsManager.INSTANCE.MODE.equals(EnumDefine.MODE.ONLINE.toString())) {
                    ModeManager.mSwitchMode(EnumDefine.MODE.OFFLINE);
                    mTvCurrentMode.setText(SettingsManager.INSTANCE.MODE + " MODE");
                } else {
                    ModeManager.mSwitchMode(EnumDefine.MODE.ONLINE);
                    mTvCurrentMode.setText(SettingsManager.INSTANCE.MODE + " MODE");
                }
            }
        });
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsCurrentUser()) {
                    mDoSignUp();
                } else {
                    Toast.makeText(LoginActivity.this, "You're logined", Toast.LENGTH_LONG).show();

                }
            }
        });
        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDoSignIn();
            }
        });
    }

    static Timer timer;
    int current_time = 0;

    private void mCountTimeOut() {
        System.out.println("START TIMER =================>");
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("RUN TIMER =================>");
                System.out.println("CURRENT TIME : "+current_time);

                if (current_time == TIME_OUT) {
                    mTvStatus.setText("Can't connect to server, please check internet, stop connect to server...");
                    timer.cancel();
                } else if (current_time == 15000) {
                    mTvStatus.setText("Slow connection! Please wait...");

                } else if (current_time == 5000) {
                    mTvStatus.setText("Tips: you can login in offline mode without internet connection.");
                } else if (current_time == 10000) {
                    mTvStatus.setText("Tips: always turn on Syns data to offline setting to use when offline.");
                }
                current_time++;
            }
        }, 1000, 1000);

    }

    private void mGoToActivity(Class c) {
        Intent intent = new Intent(getApplicationContext(), c);
        startActivity(intent);
        this.finish();

    }

    private void mResetPassword(String email) {
        if (SettingsManager.INSTANCE.MODE.equals(EnumDefine.MODE.ONLINE.toString()) && NetworkManager.mIsConnectToNetwork(LoginActivity.this)) {
            mAuth.sendPasswordResetEmail(email);
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("Successed")
                    .setMessage("An email have been sent to your email to reset password! Please check your inbox.")
                    .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ModeManager.mSwitchMode(EnumDefine.MODE.ONLINE); // set current mode to OFFLINE
                            mTvCurrentMode.setText(SettingsManager.INSTANCE.MODE + " MODE");

                        }
                    })
                    .show();
        } else {
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("Error")
                    .setMessage("Can't reset password in Offline mode.")
                    .setPositiveButton("Online mode", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ModeManager.mSwitchMode(EnumDefine.MODE.ONLINE); // set current mode to OFFLINE
                            mTvCurrentMode.setText(SettingsManager.INSTANCE.MODE + " MODE");

                        }
                    })
                    .setNegativeButton("Offline mode", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setCancelable(false)
                    .show();
        }
    }

    private void mDoSignUp() {
        if (mIsValidForm()) {
            if (SettingsManager.INSTANCE.MODE.equals(EnumDefine.MODE.ONLINE.toString())) { // if current mode is ONLINE -> check user in firebase
                if (NetworkManager.mIsConnectToNetwork(LoginActivity.this)) {
                    mShowProgessDialog();
                    mAuth.createUserWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                }
                            })
                            .addOnFailureListener(this, new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    String error_code = ((FirebaseAuthException) e).getErrorCode();
                                    DialogAlert.mShow(LoginActivity.this, mGetErrorMessage(error_code));
                                    mDismissProgessDialog();
                                }
                            })
                            .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(LoginActivity.this, "Sign up successed!", Toast.LENGTH_LONG).show();
                                    mIsNewUser(authResult.getUser().getUid());
//                                    mGoToActivity(MainActivity.class);
                                }
                            });
                } else {
                    mDisplayAlert(false);
                }
            } else { //offline mode -> check user in local storage
                if (mIsValidEmail(mEmail.getText().toString())) { //TODO: check if email is valid format
                    if (mIsValidPassword(mPassword.getText().toString())) { //TODO: check if password is valid
                        mGoToActivity(MainActivity.class);
                    } else {
                        DialogAlert.mShow(LoginActivity.this, mGetErrorMessage("ERROR_WRONG_PASSWORD"));
                    }
                } else {
                    DialogAlert.mShow(LoginActivity.this, mGetErrorMessage("ERROR_INVALID_EMAIL"));
                }
            }
        }
    }

    private boolean mIsValidEmail(String email) { // check if email is valid format (only use when sign up in offline mode)
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    private boolean mIsValidPassword(String password) {  // check if password is valid format (only use when sign up in offline mode)
        return false;
    }

    private boolean mIsValidForm() { //check if email and password is oke
        if (TextUtils.isEmpty(mEmail.getText().toString())) {
            mEmail.setError("PLease input your email!");
            return false;
        }
        if (TextUtils.isEmpty(mPassword.getText().toString())) {
            mPassword.setError("PLease input your password!");
            return false;
        }
        return true;
    }

    private void mDoSignIn() {
        if (mIsValidForm()) {
            if (SettingsManager.INSTANCE.MODE.equals(EnumDefine.MODE.ONLINE.toString())) { // if current mode is ONLINE -> check user in firebase
                if (NetworkManager.mIsConnectToNetwork(LoginActivity.this)) {
                    mShowProgessDialog();
                    mAuth.signInWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())

                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Sign in failed!", Toast.LENGTH_LONG).show();
                                    String error_code = ((FirebaseAuthException) e).getErrorCode();
                                    mDismissProgessDialog();
                                    DialogAlert.mShow(LoginActivity.this, mGetErrorMessage(error_code));

                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    mIsNewUser(authResult.getUser().getUid());
                                    Toast.makeText(getApplicationContext(), "Sign in successed!", Toast.LENGTH_LONG).show();
//                                    mGoToActivity(MainActivity.class);

                                }
                            });
                } else {
                    mDisplayAlert(false);
                }


            } else { //offline mode -> check user in local storage
                if (mEmail.getText().toString().equals("admin")) { //TODO: get "admin" from sqlite
                    if (mPassword.getText().toString().equals("admin")) { //TODO: get "password" from sqlite
                        mGoToActivity(MainActivity.class);
                    } else {
                        DialogAlert.mShow(LoginActivity.this, mGetErrorMessage("ERROR_WRONG_PASSWORD"));
                    }
                } else {
                    DialogAlert.mShow(LoginActivity.this, mGetErrorMessage("ERROR_INVALID_EMAIL"));
                }
            }

        }
    }

    private String mGetErrorMessage(String errorCode) {//get error message when sign in or sign up
        switch (errorCode) {
            case "ERROR_INVALID_CUSTOM_TOKEN":
                return getResources().getString(R.string.ERROR_INVALID_CUSTOM_TOKEN);
            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                return getResources().getString(R.string.ERROR_CUSTOM_TOKEN_MISMATCH);
            case "ERROR_INVALID_CREDENTIAL":
                return getResources().getString(R.string.ERROR_INVALID_CREDENTIAL);
            case "ERROR_INVALID_EMAIL":
                return getResources().getString(R.string.ERROR_INVALID_EMAIL);
            case "ERROR_WRONG_PASSWORD":
                return getResources().getString(R.string.ERROR_WRONG_PASSWORD);
            case "ERROR_USER_MISMATCH":
                return getResources().getString(R.string.ERROR_USER_MISMATCH);
            case "ERROR_REQUIRES_RECENT_LOGIN":
                return getResources().getString(R.string.ERROR_REQUIRES_RECENT_LOGIN);
            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                return getResources().getString(R.string.ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL);
            case "ERROR_EMAIL_ALREADY_IN_USE":
                return getResources().getString(R.string.ERROR_EMAIL_ALREADY_IN_USE);
            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                return getResources().getString(R.string.ERROR_CREDENTIAL_ALREADY_IN_USE);
            case "ERROR_USER_DISABLED":
                return getResources().getString(R.string.ERROR_USER_DISABLED);
            case "ERROR_USER_TOKEN_EXPIRED":
                return getResources().getString(R.string.ERROR_USER_TOKEN_EXPIRED);
            case "ERROR_USER_NOT_FOUND":
                return getResources().getString(R.string.ERROR_USER_NOT_FOUND);
            case "ERROR_INVALID_USER_TOKEN":
                return getResources().getString(R.string.ERROR_INVALID_USER_TOKEN);
            case "ERROR_OPERATION_NOT_ALLOWED":
                return getResources().getString(R.string.ERROR_OPERATION_NOT_ALLOWED);
            case "ERROR_WEAK_PASSWORD":
                return getResources().getString(R.string.ERROR_WEAK_PASSWORD);

        }
        return null;
    }

    private boolean mIsCurrentUser() {
        return (mAuth.getCurrentUser() == null) ? false : true;
    }

    private void mShowProgessDialog() { // show progess "please wait" when sign in or sign up
        mCountTimeOut();
        mFlProgressFrame.setVisibility(View.VISIBLE);
        mLoginMain.setVisibility(View.INVISIBLE);
    }

    private void mDismissProgessDialog() {// close progess "please wait"
        timer.cancel();
        if (mFlProgressFrame != null)
            mFlProgressFrame.setVisibility(View.INVISIBLE);
        mLoginMain.setVisibility(View.VISIBLE);

    }

    private void mDisplayAlert(boolean isHasConnection) { //display dialog alert that user not connect to network
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
        if (isHasConnection) {
            mAlertDialog = new AlertDialog.Builder(this)
                    .setTitle("You have connect to network")
                    .setMessage("Connected! Do you want switch to Online mode?")
                    .setPositiveButton("Online mode", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ModeManager.mSwitchMode(EnumDefine.MODE.ONLINE); // set current mode to OFFLINE
                            mTvCurrentMode.setText(SettingsManager.INSTANCE.MODE + " MODE");

                        }
                    })
                    .setNegativeButton("Offline mode", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setCancelable(false)
                    .show();
        } else {
            mAlertDialog = new AlertDialog.Builder(this)
                    .setTitle("No network connection")
                    .setMessage("Please connect to internet or switch to offline mode.")
                    .setPositiveButton("Offline mode", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ModeManager.mSwitchMode(EnumDefine.MODE.OFFLINE); // set current mode to OFFLINE
                            mTvCurrentMode.setText(SettingsManager.INSTANCE.MODE + " MODE");
                        }
                    })
                    .setNegativeButton("Close app", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            android.os.Process.killProcess(Process.myPid());
                            System.exit(1);
                        }
                    }).setCancelable(false)
                    .show();
        }

    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION.equals(action)) {
                if (!NetworkManager.mIsConnectToNetwork(context) && SettingsManager.INSTANCE.MODE.equals(EnumDefine.MODE.ONLINE.toString())) {
                    mDisplayAlert(false);
                } else {
                    System.out.println(SettingsManager.INSTANCE.MODE + " ================================================ " + EnumDefine.MODE.OFFLINE);
                    if (SettingsManager.INSTANCE.MODE.equals(EnumDefine.MODE.OFFLINE.toString()) && NetworkManager.mIsConnectToNetwork(context)) {
                        mDisplayAlert(true);
                    } else {
                        System.out.println("NOT EQUAL =================================================>");
                    }
                }
            }
        }
    };

    private void mIsNewUser(String userId) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(EnumDefine.FIREBASE_CHILD.USERS.toString() + "/" + userId);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user == null || user.getDisplayName() == null || user.getDob() == null) {
                    mGoToActivity(FirstSetting.class);
                } else {
                    mGoToActivity(MainActivity.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "ERROR WHEN CHECK", Toast.LENGTH_LONG).show();
            }
        });


    }
}
