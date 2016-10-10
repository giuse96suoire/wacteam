package com.dev.wacteam.taskmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dev.wacteam.taskmanager.dialog.DialogAlert;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        if (mIsCurrentUser()) {
            mGoToActivity(MainActivity.class);
        }
        setContentView(R.layout.activity_login);
        mLoginMain = (RelativeLayout) findViewById(R.id.login_main);
        mSignUp = (Button) findViewById(R.id.btn_signUp);
        mSignIn = (Button) findViewById(R.id.btn_signIn);
        mEmail = (EditText) findViewById(R.id.et_email);
        mPassword = (EditText) findViewById(R.id.et_password);
//        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
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
                if (!mIsCurrentUser()) {
                    mDoSignIn();
                } else {
                    Toast.makeText(LoginActivity.this, "You're logined", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void mGoToActivity(Class c) {
        Intent intent = new Intent(getApplicationContext(), c);
        startActivity(intent);
        this.finish();
    }

    private void mDoSignUp() {
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
    }

    private void mDoSignIn() {

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
    }

    private String mGetErrorMessage(String errorCode) {
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

    private void mShowProgessDialog() {
        if (mProgressBar == null)
            mProgressBar = (ProgressBar) findViewById(R.id.pb_wait);
        mProgressBar.setVisibility(View.VISIBLE);
        mLoginMain.setVisibility(View.INVISIBLE);
    }

    private void mDismissProgessDialog() {
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.INVISIBLE);
        mLoginMain.setVisibility(View.VISIBLE);

    }


}
