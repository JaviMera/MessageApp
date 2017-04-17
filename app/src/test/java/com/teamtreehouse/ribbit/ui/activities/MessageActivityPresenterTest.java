package com.teamtreehouse.ribbit.ui.activities;

import android.view.View;
import android.view.animation.Animation;

import com.teamtreehouse.ribbit.ui.activities.messages.MessageActivityPresenter;
import com.teamtreehouse.ribbit.ui.activities.messages.MessageActivityView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by javie on 4/15/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class MessageActivityPresenterTest {

    @Mock
    public MessageActivityView view;

    private MessageActivityPresenter target;

    @Before
    public void setUp() throws Exception {

        this.target = new MessageActivityPresenter(this.view);
    }

    @Test
    public void setSendButtonVisibility() throws Exception {

        // Arrange
        int visible = View.GONE;

        // Act
        this.target.setSendImageVisibility(visible);

        // Assert
        Mockito.verify(this.view).setSendImageVisibility(visible);
    }

    @Test
    public void setMessageEditTextVisibility() throws Exception {

        // Arrange
        int visible = View.GONE;

        // Act
        this.target.setMessageEditTextVisibility(visible);

        // Assert
        Mockito.verify(this.view).setMessageEditTextVisibility(visible);
    }

    @Test
    public void setSendLayoutVisibility() throws Exception {

        // Arrange
        int visibility = View.GONE;

        // Act
        this.target.setSendLayoutVisibility(visibility);

        // Assert
        Mockito.verify(this.view).setSendLayoutVisibility(visibility);
    }

    @Test
    public void setProgressbarVisibility() throws Exception {

        // Arrange
        int visibility = View.GONE;

        // Act
        this.target.setProgressbarVisibility(visibility);

        // Assert
        Mockito.verify(this.view).setProgressBarVisibility(visibility);
    }

    @Test
    public void setSendImageViewAnimation() throws Exception {

        // Arrange
        Animation anim = null;

        // Act
        this.target.setSendImageViewAnimation(anim);

        // Assert
        Mockito.verify(this.view).setSendImageViewAnimation(anim);
    }

    @Test
    public void setMessageEditTextAnimation() throws Exception {

        // Arrange
        Animation anim = null;

        // Act
        this.target.setMessageEditTextAnimation(anim);

        // Assert
        Mockito.verify(this.view).setMessageEditTextAnimation(anim);
    }
}