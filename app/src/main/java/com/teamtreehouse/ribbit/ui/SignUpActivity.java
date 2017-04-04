package com.teamtreehouse.ribbit.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.database.MessageDB;
import com.teamtreehouse.ribbit.dialogs.DialogFragmentError;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.models.UserRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    public interface InsertCallback {

        void onSuccess(User user);
        void onFailure(String message);
    }

    @BindView(R.id.usernameField)
    protected EditText mUsername;

    @BindView(R.id.passwordField)
    protected EditText mPassword;

    @BindView(R.id.emailField)
    protected EditText mEmail;

    @BindView(R.id.signupButton)
    protected Button mSignUpButton;

    @BindView(R.id.cancelButton)
    protected Button mCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);
    }


    @OnClick(R.id.signupButton)
    public void onSignUpClick(View view) {

        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        String email = mEmail.getText().toString();

        username = username.trim();
        password = password.trim();
        email = email.trim();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {

            DialogFragment dialog = DialogFragmentError.newInstance(
                    getString(R.string.signup_error_message),
                    getString(R.string.signup_error_title));

            dialog.show(getSupportFragmentManager(), "dialog_signup");

        } else {

            FirebaseAuth auth = FirebaseAuth.getInstance();
            final String finalEmail = email;
            final String finalUsername = username;

            auth
                .createUserWithEmailAndPassword(email, password)
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
                            User newUser = new UserRequest(
                                firebaseUser.getUid(),
                                finalEmail,
                                finalUsername
                            );

                            MessageDB.insertUser(newUser, new InsertCallback() {
                                @Override
                                public void onSuccess(User user) {

                                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                    finish();
                                }

                                @Override
                                public void onFailure(String message) {

                                    DialogFragment dialog = DialogFragmentError.newInstance(
                                            getString(R.string.existing_user_error_title),
                                            message);

                                    dialog.show(getSupportFragmentManager(), "dialog_signup");
                                }
                            });
                        }
                    }
                });
        }
    }

    @OnClick(R.id.cancelButton)
    public void onCancelClick(View view) {

        finish();
    }
}
