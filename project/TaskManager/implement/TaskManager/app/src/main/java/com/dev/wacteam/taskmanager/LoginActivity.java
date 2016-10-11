package com.dev.wacteam.taskmanager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.wacteam.taskmanager.dialog.DialogAlert;
import com.dev.wacteam.taskmanager.manager.EnumDefine;
import com.dev.wacteam.taskmanager.manager.ModeManager;
import com.dev.wacteam.taskmanager.manager.NetworkManager;
import com.dev.wacteam.taskmanager.manager.SettingsManager;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import org.w3c.dom.Text;

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
    static final String ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter(ACTION);
        this.registerReceiver(mReceiver, filter);
        mAuth = FirebaseAuth.getInstance();
        if (NetworkManager.mIsConnectToNetwork(LoginActivity.this)) {
            if (mIsCurrentUser()) {
                mGoToActivity(MainActivity.class);
            }
        } else {
            // check if user is login in offline mode
        }

        setContentView(R.layout.activity_login);
        mLoginMain = (RelativeLayout) findViewById(R.id.login_main);
        mSignUp = (Button) findViewById(R.id.btn_signUp);
        mSignIn = (Button) findViewById(R.id.btn_signIn);
        mEmail = (EditText) findViewById(R.id.et_email);
        mPassword = (EditText) findViewById(R.id.et_password);
        mTvCurrentMode = (TextView) findViewById(R.id.tv_currentMode);
        mTvCurrentMode.setText(SettingsManager.INSTANCE.MODE +" MODE (Touch to switch)");
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

    private void mGoToActivity(Class c) {
        Intent intent = new Intent(getApplicationContext(), c);
        startActivity(intent);
        this.finish();

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
                                    mGoToActivity(MainActivity.class);
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
        return false;
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
                                    Toast.makeText(getApplicationContext(), "Sign in successed!", Toast.LENGTH_LONG).show();
                                    mGoToActivity(MainActivity.class);

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
                return "Error! Login failed! Please try again!";
            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                return "Error! Login failed! Please try again!";
            case "ERROR_INVALID_CREDENTIAL":
                return "Error! Login failed! Please try again!";
            case "ERROR_INVALID_EMAIL":
                return "Email is invalid! Please check email value!";
            case "ERROR_WRONG_PASSWORD":
                return "Wrong password!";
            case "ERROR_USER_MISMATCH":
                return "Error! Login failed! Please try again!";
            case "ERROR_REQUIRES_RECENT_LOGIN":
                return "Error! Login failed! Please try again!";
            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                return "You have logined in other device! Please check!";
            case "ERROR_EMAIL_ALREADY_IN_USE":
                return "Email is existed! Please login or use other email!";
            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                return "Other account is in use, please logout!";
            case "ERROR_USER_DISABLED":
                return "User has been banned by admin! Please contact admin!";
            case "ERROR_USER_TOKEN_EXPIRED":
                return "Error! Login failed! Please try again!";
            case "ERROR_USER_NOT_FOUND":
                return "Your account is not exist!";
            case "ERROR_INVALID_USER_TOKEN":
                return "Error! Login failed! Please try again!";
            case "ERROR_OPERATION_NOT_ALLOWED":
                return "Error! Login failed! Please try again!";
            case "ERROR_WEAK_PASSWORD":
                return "Wrong password!";
        }
        return null;
    }

    private boolean mIsCurrentUser() {
        return (mAuth.getCurrentUser() == null) ? false : true;
    }

    private void mShowProgessDialog() { // show progess "please wait" when sign in or sign up
        if (mProgressBar == null)
            mProgressBar = (ProgressBar) findViewById(R.id.pb_wait);
        mProgressBar.setVisibility(View.VISIBLE);
        mLoginMain.setVisibility(View.INVISIBLE);
    }

    private void mDismissProgessDialog() {// close progess "please wait"
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.INVISIBLE);
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
                            mTvCurrentMode.setText(SettingsManager.INSTANCE.MODE +" MODE (Touch to switch)");

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
                            mTvCurrentMode.setText(SettingsManager.INSTANCE.MODE +" MODE (Touch to switch)");
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

}
