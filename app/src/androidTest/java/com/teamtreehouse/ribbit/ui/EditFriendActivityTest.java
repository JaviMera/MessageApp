package com.teamtreehouse.ribbit.ui;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.models.users.UserFriend;
import com.teamtreehouse.ribbit.ui.activities.EditFriendActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by javie on 4/7/2017.
 */

@RunWith(AndroidJUnit4.class)
public class EditFriendActivityTest {

    @Rule
    public ActivityTestRule<EditFriendActivity> rule =
        new ActivityTestRule<EditFriendActivity>(EditFriendActivity.class, true, false);

    @Test
    public void activityIsInitializedWithIntentExtra() throws Exception {

        // Arrange
        Intent intent = createIntent();

        // Act
        rule.launchActivity(intent);

        // Assert
        onView(withId(R.id.username)).check(matches(withText("harambe")));
    }

    @Test
    public void removeIconClickDisplaysWarningDialog() throws Exception {

        // Arrange
        rule.launchActivity(createIntent());
        String expectedTitle = rule.getActivity().getString(R.string.delete_friend_message);

        // Act
        onView(withId(R.id.action_delete)).perform(click());

        // Assert
        onView(withId(android.R.id.message)).check(matches(withText(expectedTitle)));
    }

    private Intent createIntent(){

        Intent intent = new Intent();
        intent.putExtra(EditFriendActivity.EDIT_FRIEND_KEY, new UserFriend("", "harambe"));
        return intent;
    }
}
