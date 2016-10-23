package com.dev.wacteam.taskmanager.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.wacteam.taskmanager.R;
import com.dev.wacteam.taskmanager.database.RemoteUser;
import com.dev.wacteam.taskmanager.dialog.DialogAlert;
import com.dev.wacteam.taskmanager.listener.OnGetDataListener;
import com.dev.wacteam.taskmanager.manager.EnumDefine;
import com.dev.wacteam.taskmanager.manager.NetworkManager;
import com.dev.wacteam.taskmanager.manager.SettingsManager;
import com.dev.wacteam.taskmanager.model.User;
import com.dev.wacteam.taskmanager.system.CurrentUser;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Timer;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private CallbackManager mCallbackManager;
    private LoginButton mLoginButton;
    private FirebaseAuth mAuth;
    private Button mSignUp, mSignIn;
    private AutoCompleteTextView mEmail;
    private EditText mPassword;
    private ProgressDialog mDialog;
    private ProgressBar mProgressBar;
    private RelativeLayout mLoginMain;
    private TextView mTvAlertInternet;
    private AlertDialog mAlertDialog;
    private TextView mTvCurrentMode;
    private Button mBtnForgotPass;
    private FrameLayout mFlProgressFrame;
    private final int TIME_OUT = 2000;
    private TextView mTvStatus, mTvLoginLabel, mTvRegisterLabel;
    static final String ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private Timer timer;
    private int current_time = 0;

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ACTION);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (CurrentUser.isLogined(getApplicationContext())) {
            mGoToActivity(MainActivity.class);
        }
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_login);

        mSignUp = (Button)

                findViewById(R.id.btn_signUp);

        mSignIn = (Button)

                findViewById(R.id.btn_signIn);

        mEmail = (AutoCompleteTextView)

                findViewById(R.id.et_email);

        mPassword = (EditText)

                findViewById(R.id.et_password);

        ArrayList<String> mail = mGetSystemEmail();
        mEmail.setAdapter(
                new ArrayAdapter<String>(

                        getBaseContext(),

                        android.R.layout.simple_list_item_1, mail
//                        new String[]{"Giuse96suoire@gmail.com","abc@gmail.com"}
                )
        );
//        mBtnForgotPass = (Button) findViewById(R.id.btn_forgotPassword);
//        mBtnForgotPass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mIsValidEmail(mEmail.getText().toString())) {
//                    mResetPassword(mEmail.getText().toString());
//                } else {
//                    Toast.makeText(LoginActivity.this, "Email invalid!", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//
        mSignUp.setOnClickListener(new View.OnClickListener()

                                   {
                                       @Override
                                       public void onClick(View v) {
                                           if (!mIsUserLogined()) {
                                               mDoSignUp();
                                           } else {
                                               Toast.makeText(LoginActivity.this, "You're logined", Toast.LENGTH_LONG).show();

                                           }
                                       }
                                   }

        );
        mSignIn.setOnClickListener(new View.OnClickListener()

                                   {
                                       @Override
                                       public void onClick(View v) {
                                           mDoSignIn();
                                       }
                                   }

        );

        mTvLoginLabel = (TextView)

                findViewById(R.id.tv_login_label);

        mTvRegisterLabel = (TextView)

                findViewById(R.id.tv_register_label);

        int selectColor = Color.parseColor("#673AB7");
        int noneSelectColor = Color.parseColor("#9575CD");
        LinearLayout loginLayout = (LinearLayout) findViewById(R.id.layout_login);
        LinearLayout registerLayout = (LinearLayout) findViewById(R.id.layout_register);
        mTvLoginLabel.setOnClickListener(new View.OnClickListener()

                                         {
                                             @Override
                                             public void onClick(View v) {
                                                 mTvLoginLabel.setBackgroundColor(selectColor);
                                                 mTvRegisterLabel.setBackgroundColor(noneSelectColor);
                                                 loginLayout.setVisibility(View.VISIBLE);
                                                 registerLayout.setVisibility(View.GONE);
                                             }
                                         }

        );
        mTvRegisterLabel.setOnClickListener(new View.OnClickListener()

                                            {
                                                @Override
                                                public void onClick(View v) {
                                                    mTvLoginLabel.setBackgroundColor(noneSelectColor);
                                                    mTvRegisterLabel.setBackgroundColor(selectColor);
                                                    loginLayout.setVisibility(View.GONE);
                                                    registerLayout.setVisibility(View.VISIBLE);
                                                }
                                            }

        );
    }

    private ArrayList<String> mGetSystemEmail() {
        System.out.println("GET EMAIL =============>");
        ArrayList<String> email = new ArrayList<>();
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(getApplicationContext()).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                email.add(account.name);
                System.out.println(account.name + "Email==============>");
            }
        }
        return email;
    }

    private void mGoToActivity(Class c) {
        mStopCountDown();
        mDismissProgessDialog();
        System.out.println("GO TO MAIN ===================>");
        Intent intent = new Intent(getApplicationContext(), c);
        startActivity(intent);
        this.finish();

    }

    private void mCheckInforInServer(User user) {
        CurrentUser.setLogined(true, getApplicationContext());
        CurrentUser.setInfo(user, getApplicationContext());
        new RemoteUser().mFind(user.getUid(), new OnGetDataListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(DataSnapshot data) {
                User nUser = data.getValue(User.class);
                if (nUser == null) {
                    CurrentUser.setUserInfoToServer(getApplicationContext());
                    mGoToActivity(MainActivity.class);
                } else {
                    mGoToActivity(MainActivity.class);
                }

            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                mGoToActivity(MainActivity.class);
            }
        });

    }

    private CountDownTimer mCountDown;

    private void mStartCountDown() {
        if (mCountDown == null) {
            mCountDown = new CountDownTimer(EnumDefine.TIME_OUT * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    int pass_time = (int) (EnumDefine.TIME_OUT - (millisUntilFinished / 1000));
                    if (pass_time == EnumDefine.LOW_CONNECTION) {
                        mProgressDialog.setTitle("Your network too low, please wait... ");
                    } else if (pass_time == EnumDefine.TRY_RECONNECT) {
                        mProgressDialog.setTitle("Try to reconnect, please wait... ");
                    } else if (pass_time == EnumDefine.DISCONNECT) {
                        mProgressDialog.setTitle("No internet connection. Please check your connection.");
                    }
                }

                @Override
                public void onFinish() {
                    Toast.makeText(LoginActivity.this, "FNISH", Toast.LENGTH_LONG).show();
                    mDismissProgessDialog();
                }
            };
        }
        mCountDown.start();
    }

    private void mStopCountDown() {
        if (mCountDown != null) {
            mCountDown.cancel();
        }
    }

    private void mResetPassword(String email) {
        if (SettingsManager.INSTANCE.MODE.equals(EnumDefine.MODE.ONLINE.toString()) && NetworkManager.mIsConnectToNetwork(LoginActivity.this)) {
            mAuth.sendPasswordResetEmail(email);
            mDisplayNoConnectionAlert();
        } else {

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
                                    User user = new User();
                                    user.setDisplayName(authResult.getUser().getDisplayName());
                                    user.setEmail(authResult.getUser().getEmail());
                                    mCheckInforInServer(user);
                                }
                            });
                } else {
                    mDisplayNoConnectionAlert();
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
//            if (SettingsManager.INSTANCE.MODE.equals(EnumDefine.MODE.ONLINE.toString())) { // if current mode is ONLINE -> check user in firebase
//                if (NetworkManager.mIsConnectToNetwork(LoginActivity.this)) {
            mShowProgessDialog();
            mAuth.signInWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())

                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
//                                    Toast.makeText(getApplicationContext(), "Sign in complete!", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Sign in failed!", Toast.LENGTH_LONG).show();
                            String error_code;
                            try {
                                error_code = ((FirebaseAuthException) e).getErrorCode();
                                mDismissProgessDialog();
                                DialogAlert.mShow(LoginActivity.this, mGetErrorMessage(error_code));
                            } catch (Exception ex) {
                                error_code = ((FirebaseNetworkException) e).getMessage();
                                mDismissProgessDialog();
                                DialogAlert.mShow(LoginActivity.this, error_code);

                            }

                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
//                                    mIsNewUser(authResult.getUser().getUid());
                            Toast.makeText(getApplicationContext(), "Sign in successed!", Toast.LENGTH_LONG).show();
//                                    User user = new User();
//                                    user.setDisplayName(authResult.getUser().getDisplayName());
//                                    user.setUid(authResult.getUser().getUid());
//                                    user.setPhotoUrl(authResult.getUser().getPhotoUrl());
//                                    user.setProviderId(authResult.getUser().getProviderId());
                            User user = new User();
                            user.setUid(authResult.getUser().getUid());
                            user.setDisplayName(authResult.getUser().getDisplayName());
                            user.setEmail(authResult.getUser().getEmail());

                            mCheckInforInServer(user);
                        }
                    });
        } else {
            mDisplayNoConnectionAlert();
        }


//            } else { //offline mode -> check user in local storage
//                if (mEmail.getText().toString().equals("admin")) { //TODO: get "admin" from sqlite
//                    if (mPassword.getText().toString().equals("admin")) { //TODO: get "password" from sqlite
//                        mGoToActivity(MainActivity.class);
//                    } else {
//                        DialogAlert.mShow(LoginActivity.this, mGetErrorMessage("ERROR_WRONG_PASSWORD"));
//                    }
//                } else {
//                    DialogAlert.mShow(LoginActivity.this, mGetErrorMessage("ERROR_INVALID_EMAIL"));
//                }
//            }

//        }
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

    private boolean mIsUserLoginedOnline() {
        return (NetworkManager.mIsConnectToNetwork(LoginActivity.this) && mAuth.getCurrentUser() != null) ? true : false;
    }

    private boolean mIsUserLoginedOffline() {
        return (CurrentUser.getInstance().getDisplayName() == null) ? false : true;
    }

    private boolean mIsUserLogined() {
        return (mIsUserLoginedOnline() || mIsUserLoginedOffline()) ? true : false;
    }

    private ProgressDialog mProgressDialog;

    private void mShowProgessDialog() { // show progess "please wait" when sign in or sign up
        mStartCountDown();

        mProgressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Authenticating...");
        mProgressDialog.show();
    }

    private void mDismissProgessDialog() {// close progess "please wait"
        if (mProgressBar != null)
            mProgressDialog.dismiss();
    }

    private void mDisplayNoConnectionAlert() { //display dialog alert that user not connect to network
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }

        mAlertDialog = new AlertDialog.Builder(this)
                .setTitle("No network connection")
                .setMessage("Please check your internet connection")
                .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(true)
                .show();


    }


}
