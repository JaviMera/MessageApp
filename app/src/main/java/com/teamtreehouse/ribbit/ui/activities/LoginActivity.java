package com.teamtreehouse.ribbit.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.dialogs.DialogFragmentError;
import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.database.UserReadCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    @BindView(R.id.loginProgressBar)
    protected ProgressBar mProgressBar;

    @BindView(R.id.usernameField)
    protected EditText mUsername;

    @BindView(R.id.passwordField)
    protected EditText mPassword;

    @BindView(R.id.loginButton)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.signUpText)
    public void onSignupClick(View view) {

        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.loginButton)
    public void onLoginClick(View view) {

        String username = mUsername
            .getText()
            .toString()
            .toLowerCase();

        String password = mPassword
            .getText()
            .toString();

        username = username.trim();
        password = password.trim();

        if (username.isEmpty()) {

            DialogFragmentError dialog = DialogFragmentError.newInstance(
                getString(R.string.login_error_empty_username_message),
                getString(R.string.login_error_title));

            dialog.show(getSupportFragmentManager(), "dialog_error");
            return;
        }

        if(password.isEmpty()) {

            DialogFragmentError dialog = DialogFragmentError.newInstance(
                getString(R.string.login_error_empty_password_message),
                getString(R.string.login_error_title)
            );

            dialog.show(getSupportFragmentManager(), "dialog_error");
            return;
        }

        // Login
        mProgressBar.setVisibility(View.VISIBLE);
        final String usernameTemp = username;
        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(username + "@harambe.com", password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    mProgressBar.setVisibility(View.INVISIBLE);

                    if(!task.isSuccessful()) {

                        DialogFragmentError dialog = DialogFragmentError.newInstance(
                            task.getException().getMessage(),
                            getString(R.string.login_error_title));

                        dialog.show(getSupportFragmentManager(), "dialog_error");
                    }
                    else{

                        MessageDB.readUser(usernameTemp, new UserReadCallback() {

                            @Override
                            public void onUserRead(List<User> user) {

                                Auth.getInstance().setUser(user.get(0));
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        });
                    }

                }
            });
//        ObsoleteUser.logInInBackground(username, password, new LogInCallback() {
//            @Override
//            public void done(ObsoleteUser usernameText, Exception e) {
//
//                mProgressBar.setVisibility(View.INVISIBLE);
//
//                if (e == null) {
//                    // Success!
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                } else {
//
//                    DialogFragmentError dialog = DialogFragmentError.newInstance(
//                        e.getMessage(),
//                        getString(R.string.login_error_title));
//
//                    dialog.show(getSupportFragmentManager(), "dialog_error");
//                }
//            }
//        });
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }
}
