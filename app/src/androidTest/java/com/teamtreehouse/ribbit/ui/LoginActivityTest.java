package com.teamtreehouse.ribbit.ui;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.models.users.UserCurrent;
import com.teamtreehouse.ribbit.ui.activities.LoginActivity;
import com.teamtreehouse.ribbit.ui.activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by javie on 3/24/2017.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {

    User user;

    @Rule
    public IntentsTestRule<LoginActivity> mActivityRule = new IntentsTestRule<LoginActivity>(
        LoginActivity.class, false, false);

    @Before
    public void setUp() throws Exception {

        Intent intent = new Intent();
        intent.putExtra("test", true);
        mActivityRule.launchActivity(intent);

        user = new UserCurrent("lksdjfdsjf", "javier@hotmail.com", "Javito", "Javito", "");
    }

    @Test
    public void emptyUser_LaunchesErrorDialog() throws Exception {

        // Arrange
        String expectedMessage = mActivityRule
            .getActivity()
            .getString(R.string.login_error_empty_username_message);

        mActivityRule.getActivity().closeKeyboard();
        // Act
        onView(withId(R.id.loginButton)).perform(click());

        // Assert
        onView(withText(expectedMessage)).check(matches(isDisplayed()));
    }

    @Test
    public void emptyPassword_LaunchesErrorDialog() throws Exception {

        // Arrange
        String expectedMessage = mActivityRule
            .getActivity()
            .getString(R.string.login_error_empty_password_message);


        onView(withId(R.id.usernameField)).perform(typeText(user.getUsername()));

        mActivityRule.getActivity().closeKeyboard();

        // Act
        onView(withId(R.id.loginButton)).perform(click());

        // Assert
        onView(withText(expectedMessage)).check(matches(isDisplayed()));
    }

    @Test
    public void invalidUserName_LaunchesErrorDialog() {

        // Arrange
        String expectedMessage = "There is no user record corresponding to this identifier. The user may have been deleted.";
        onView(withId(R.id.usernameField)).perform(typeText("Harambe"));

        mActivityRule.getActivity().closeKeyboard();

        onView(withId(R.id.passwordField)).perform(typeText("roflolmao"));

        mActivityRule.getActivity().closeKeyboard();

        // Act
        onView(withId(R.id.loginButton)).perform(click());

        // Assert
        onView(withText(expectedMessage)).check(matches(isDisplayed()));
    }
}