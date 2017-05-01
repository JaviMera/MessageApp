package com.teamtreehouse.ribbit.ui.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.dialogs.DialogFragmentError;
import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.models.users.UserCurrent;
import com.teamtreehouse.ribbit.database.UserInsertCallback;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity implements UserInsertCallback, DialogInterface.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.usernameField)
    protected EditText usernameView;

    @BindView(R.id.passwordField)
    protected EditText passwordView;

    @BindView(R.id.emailField)
    protected EditText emailView;

    @BindView(R.id.signupButton)
    protected Button mSignUpButton;

    @BindView(R.id.cancelButton)
    protected Button mCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign up Today!");

        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
    }

    private boolean isTextViewEmpty(TextView view) {

        return view
            .getText()
            .toString()
            .trim()
            .isEmpty();
    }

    @OnClick(R.id.signupButton)
    public void onSignUpClick(View view) {

        progressBar.setVisibility(View.VISIBLE);

        if (isTextViewEmpty(usernameView) || isTextViewEmpty(passwordView) || isTextViewEmpty(emailView)) {

            DialogFragment dialog = DialogFragmentError.newInstance(
                    getString(R.string.signup_error_message),
                    getString(R.string.signup_error_title));

            dialog.show(getSupportFragmentManager(), "dialog_signup");
        }
        else {

            final String email = emailView.getText().toString();
            final String username = usernameView.getText().toString();
            String domain = getString(R.string.account_domain);
            String extension = getString(R.string.account_extension);
            final String password = passwordView.getText().toString();

            FirebaseAuth
                .getInstance()
                .createUserWithEmailAndPassword(
                    username + domain + extension,
                    password
                )
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {

                            DialogFragment dialog = DialogFragmentError.newInstance(
                                    task.getException().getMessage(),
                                    getString(R.string.signup_error_title));

                            dialog.show(getSupportFragmentManager(), "dialog_signup");
                        } else {

                            FirebaseUser firebaseUser = task.getResult().getUser();
                            User newUser = new UserCurrent(
                                firebaseUser.getUid(),
                                email,
                                username.toLowerCase(),
                                username,
                                ""
                            );

                            MessageDB.insertUser(newUser, SignUpActivity.this);
                        }
                    }
                });
        }
    }

    @OnClick(R.id.cancelButton)
    public void onCancelClick(View view) {

        finish();
    }

    @Override
    public void onSuccess(User user) {

        progressBar.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailure(String message) {

        progressBar.setVisibility(View.INVISIBLE);
        DialogFragment dialog = DialogFragmentError.newInstance(
                getString(R.string.existing_user_error_title),
                message);

        dialog.show(getSupportFragmentManager(), "dialog_signup");
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }
}
