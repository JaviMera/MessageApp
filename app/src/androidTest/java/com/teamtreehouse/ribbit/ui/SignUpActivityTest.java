package com.teamtreehouse.ribbit.ui;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.models.users.UserCurrent;
import com.teamtreehouse.ribbit.ui.activities.LoginActivity;
import com.teamtreehouse.ribbit.ui.activities.SignUpActivity;

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
 * Created by javie on 5/3/2017.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignUpActivityTest {

    User user;

    @Rule
    public IntentsTestRule<SignUpActivity> mActivityRule = new IntentsTestRule<SignUpActivity>(
            SignUpActivity.class);

    @Before
    public void setUp() throws Exception {

        user = new UserCurrent("lksdjfdsjf", "javier@hotmail.com", "Javito", "Javito", "");
    }

    @Test
    public void emptyUser_LaunchesErrorDialog() throws Exception {

        // Arrange
        String expectedMessage = mActivityRule
                .getActivity()
                .getString(R.string.signup_error_message);

        mActivityRule.getActivity().closeKeyboard();
        // Act
        onView(withId(R.id.signupButton)).perform(click());

        // Assert
        onView(withText(expectedMessage)).check(matches(isDisplayed()));
    }

    @Test
    public void emptyPassword_LaunchesErrorDialog() throws Exception {

        // Arrange
        String expectedMessage = mActivityRule
                .getActivity()
                .getString(R.string.signup_error_message);

        mActivityRule.getActivity().closeKeyboard();

        onView(withId(R.id.usernameField)).perform(typeText(user.getUsername()));

        mActivityRule.getActivity().closeKeyboard();

        // Act
        onView(withId(R.id.signupButton)).perform(click());

        // Assert
        onView(withText(expectedMessage)).check(matches(isDisplayed()));
    }

    @Test
    public void emptyEmail_LaunchesErrorDialog() throws Exception {

        // Arrange
        String expectedMessage = mActivityRule
                .getActivity()
                .getString(R.string.signup_error_message);

        mActivityRule.getActivity().closeKeyboard();

        onView(withId(R.id.usernameField)).perform(typeText(user.getUsername()));

        mActivityRule.getActivity().closeKeyboard();

        onView(withId(R.id.passwordField)).perform(typeText("harambe"));

        mActivityRule.getActivity().closeKeyboard();

        // Act
        onView(withId(R.id.signupButton)).perform(click());

        // Assert
        onView(withText(expectedMessage)).check(matches(isDisplayed()));
    }
}
