package com.dev.wacteam.taskmanager.activity;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import com.dev.wacteam.taskmanager.dialog.DialogAlert;
import com.dev.wacteam.taskmanager.manager.EnumDefine;
import com.dev.wacteam.taskmanager.manager.NetworkManager;
import com.dev.wacteam.taskmanager.model.Profile;
import com.dev.wacteam.taskmanager.model.Setting;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
                                               Toast.makeText(LoginActivity.this, R.string.already_login, Toast.LENGTH_LONG).show();

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

        int selectColor = Color.parseColor("#2E5674");
        int noneSelectColor = Color.parseColor("#32455F");

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
        ArrayList<String> email = new ArrayList<>();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            System.out.println(getString(R.string.get_mail));
            Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
            Account[] accounts = AccountManager.get(getApplicationContext()).getAccounts();
            for (Account account : accounts) {
                if (emailPattern.matcher(account.name).matches()) {
                    email.add(account.name);
                    System.out.println(account.name + getString(R.string.Email));
                }
            }
        }
        return email;

    }

    private void mGoToActivity(Class c) {
        mStopCountDown();
        mDismissProgessDialog();
        System.out.println(getString(R.string.go_to_main));
        Intent intent = new Intent(getApplicationContext(), c);
        startActivity(intent);
        this.finish();

    }

    private void mCheckInforInServer(User user) {
        System.out.println(getString(R.string.check_user_in_server));
        CurrentUser.setLogined(true, getApplicationContext());
        FirebaseDatabase.getInstance()
                .getReference("users/list/" + user.getProfile().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User nUser = dataSnapshot.getValue(User.class);
                        if (nUser == null) {
                            Setting setting = new Setting();
                            setting.setmAutoAcceptFriend(false);
                            setting.setmAutoAcceptProject(false);
                            setting.setmAutoBackupData(true);
                            setting.setmNotification(true);
                            setting.setmSound(true);
                            user.setSetting(setting);
                            ArrayList<String> listFriend = new ArrayList<String>();
                            listFriend.add("TVj2RdWN4BPiXYQnLSW9Q4JDQ333");
                            listFriend.add("bOP8bGJVcYgskEGMfl643H1db3B3");
                            listFriend.add("sqYvanpvJwOax8Oo9DGxABdx0Ev1");
                            user.setFriends(listFriend);
                            CurrentUser.setUserProfileAndSettingToLocal(user, getApplicationContext());
                            CurrentUser.setUserProfileToServer(getApplicationContext(), user);
                            mGoToActivity(MainActivity.class);
                        } else {
                            CurrentUser.setUserProfileAndSettingToLocal(nUser, getApplicationContext());
                            mGoToActivity(MainActivity.class);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

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
                        mProgressDialog.setTitle(getString(R.string.low_connection));
                    } else if (pass_time == EnumDefine.TRY_RECONNECT) {
                        mProgressDialog.setTitle(getString(R.string.try_reconnect));
                    } else if (pass_time == EnumDefine.DISCONNECT) {
                        mProgressDialog.setTitle(getString(R.string.disconnect));
                    }
                }

                @Override
                public void onFinish() {
                    Toast.makeText(LoginActivity.this, R.string.finish, Toast.LENGTH_LONG).show();
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
        if (NetworkManager.mIsConnectToNetwork(LoginActivity.this)) {
            mAuth.sendPasswordResetEmail(email);
            mDisplayNoConnectionAlert();
        } else {

        }
    }

    private void mDoSignUp() {
        if (mIsValidForm()) {
            if (NetworkManager.mIsConnectToNetwork(LoginActivity.this)) {
                mShowProgessDialog();
                mAuth.createUserWithEmailAndPassword(mEmail.getText().toString().trim(), mPassword.getText().toString().trim())
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
                                Toast.makeText(LoginActivity.this, R.string.sign_up_success, Toast.LENGTH_LONG).show();
                                User user = new User();
                                Profile profile = new Profile();
                                user.setProfile(profile);
                                user.getProfile().setUid(authResult.getUser().getUid());
                                String name = authResult.getUser().getDisplayName();
                                String email = authResult.getUser().getEmail();

                                user.getProfile().setDisplayName(name == null ? email : name);

                                user.getProfile().setEmail(authResult.getUser().getEmail());
                                mCheckInforInServer(user);
                            }
                        });
            } else {
                mDisplayNoConnectionAlert();
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
        if (TextUtils.isEmpty(mEmail.getText().toString().trim())) {
            mEmail.setError(getString(R.string.error_valid_login_email));
            return false;
        }
        if (TextUtils.isEmpty(mPassword.getText().toString().trim())) {
            mPassword.setError(getString(R.string.err_valid_login_password));
            return false;
        }
        return true;
    }

    private void mDoSignIn() {
        if (mIsValidForm()) {
            mShowProgessDialog();
            mAuth.signInWithEmailAndPassword(mEmail.getText().toString().trim(), mPassword.getText().toString().trim())

                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
//                                    Toast.makeText(getApplicationContext(), "Sign in complete!", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), R.string.sign_in_failed, Toast.LENGTH_LONG).show();
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
                            Toast.makeText(getApplicationContext(), R.string.sign_in_success, Toast.LENGTH_LONG).show();
                            User user = new User();
                            Profile profile = new Profile();
                            user.setProfile(profile);
                            user.getProfile().setUid(authResult.getUser().getUid());
                            String name = authResult.getUser().getDisplayName();
                            String email = authResult.getUser().getEmail();
                            if (name == null) {
                                name = email.split("@")[0];
                            }
                            user.getProfile().setDisplayName(name);

                            user.getProfile().setEmail(authResult.getUser().getEmail());
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
        return (CurrentUser.getInstance().getProfile().getDisplayName() == null) ? false : true;
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
        mProgressDialog.setMessage(getString(R.string.Authenticating));
        mProgressDialog.show();
    }

    private void mDismissProgessDialog() {// close progess "please wait"
        if (mProgressBar != null)
            mProgressDialog.dismiss();
    }

    private void mDisplayNoConnectionAlert() { //display dialog alert that user not connect to network
        mDismissProgessDialog();
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }

        mAlertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.no_network_connection)
                .setMessage(R.string.check_internect_connection)
                .setPositiveButton(R.string.Dismiss, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(true)
                .show();


    }


}
