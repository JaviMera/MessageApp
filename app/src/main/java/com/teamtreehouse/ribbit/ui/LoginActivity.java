package com.teamtreehouse.ribbit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.dialogs.DialogFragmentError;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.models.purgatory.ObsoleteUser;
import com.teamtreehouse.ribbit.models.callbacks.LogInCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    @BindView(R.id.loginProgressBar)
    protected ProgressBar mProgressBar;

    @BindView(R.id.usernameField)
    protected EditText mUsername;

    @BindView(R.id.passwordField)
    protected EditText mPassword;

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

        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();

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

        ObsoleteUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ObsoleteUser user, Exception e) {

                mProgressBar.setVisibility(View.INVISIBLE);

                if (e == null) {
                    // Success!
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {

                    DialogFragmentError dialog = DialogFragmentError.newInstance(
                        e.getMessage(),
                        getString(R.string.login_error_title));

                    dialog.show(getSupportFragmentManager(), "dialog_error");
                }
            }
        });
    }
}
