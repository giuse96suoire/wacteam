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
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private CallbackManager mCallbackManager;
    private LoginButton mLoginButton;
    private FirebaseAuth mAuth;
    private Button mSignUp, mSignIn;
    private EditText mEmail;
    private EditText mPassword;
    private ProgressDialog mDialog;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        if (mIsCurrentUser()) {
            mGoToActivity(MainActivity.class);
        }
        setContentView(R.layout.activity_login);
        mSignUp = (Button) findViewById(R.id.signUp_button);
        mSignIn = (Button) findViewById(R.id.signIn_button);
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsCurrentUser()) {
                    mDoSignUp();
                } else {
                    Toast.makeText(getApplicationContext(), "You're logined", Toast.LENGTH_LONG).show();

                }
            }
        });
        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsCurrentUser()) {
                    mDoSignIn();
                } else {
                    Toast.makeText(getApplicationContext(), "You're logined", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getApplicationContext(), "Sign up complete", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Sign up failed!", Toast.LENGTH_LONG).show();
                        mDismissProgessDialog();
                    }
                })
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(getApplicationContext(), "Sign up successed!", Toast.LENGTH_LONG).show();
                        mDismissProgessDialog();
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
                        Toast.makeText(getApplicationContext(), "Sign in complete", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Sign in failed!", Toast.LENGTH_LONG).show();
                        mDismissProgessDialog();

                    }
                })
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(getApplicationContext(), "Sign in successed!", Toast.LENGTH_LONG).show();
                        mDismissProgessDialog();
                        mGoToActivity(MainActivity.class);

                    }
                });
    }

    private boolean mIsCurrentUser() {
        return (mAuth.getCurrentUser() == null) ? false : true;
    }

    private void mShowProgessDialog() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void mDismissProgessDialog() {
        mProgressBar.setVisibility(View.INVISIBLE);

    }
}
