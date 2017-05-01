package com.teamtreehouse.ribbit.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import com.google.firebase.auth.FirebaseUser;
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

public class LoginActivity
    extends
        AppCompatActivity
    implements
        DialogInterface.OnClickListener {

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.loginProgressBar)
    protected ProgressBar progressBar;

    @BindView(R.id.usernameField)
    protected EditText usernameEditText;

    @BindView(R.id.passwordField)
    protected EditText passwordEditText;

    @BindView(R.id.loginButton)
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Login");
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        FirebaseUser user = FirebaseAuth
            .getInstance()
            .getCurrentUser();

        if(getIntent().hasExtra("test")) {
            return;
        }

        if(user != null) {

            progressBar.setVisibility(View.VISIBLE);

            // Get the name part from the email
            String username = user.getEmail().split("@")[0];

            // Login the user
            login(username);
        }
    }

    @OnClick(R.id.signUpText)
    public void onSignupClick(View view) {

        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.loginButton)
    public void onLoginClick(View view) {

        String username = usernameEditText
            .getText()
            .toString()
            .toLowerCase()
            .trim();

        String password = passwordEditText
            .getText()
            .toString()
            .trim();

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
        progressBar.setVisibility(View.VISIBLE);
        final String usernameTemp = username;
        String domain = getString(R.string.account_domain);
        String extension = getString(R.string.account_extension);

        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(
                username + domain + extension,
                password
            )
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(!task.isSuccessful()) {

                        DialogFragmentError dialog = DialogFragmentError.newInstance(
                            task.getException().getMessage(),
                            getString(R.string.login_error_title));

                        dialog.show(getSupportFragmentManager(), "dialog_error");

                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    else{

                        login(usernameTemp);
                    }

                }
            });
    }

    private void login(String username) {

        MessageDB.readUser(username, new UserReadCallback() {

            @Override
            public void onUserRead(List<User> user) {

                progressBar.setVisibility(View.INVISIBLE);

                // Store the current user logged in as a singleton
                Auth.getInstance().setUser(user.get(0));

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }
}
