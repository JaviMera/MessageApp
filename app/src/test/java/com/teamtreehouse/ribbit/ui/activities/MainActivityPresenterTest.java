package com.teamtreehouse.ribbit.ui.activities;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.teamtreehouse.ribbit.ui.activities.MainActivityPresenter;
import com.teamtreehouse.ribbit.ui.activities.MainActivityView;
import com.teamtreehouse.ribbit.ui.fragments.FragmentPager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by javie on 4/9/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainActivityPresenterTest {

    private MainActivityPresenter target;

    @Mock
    public MainActivityView view;

    @Before
    public void setUp() throws Exception {

        this.target = new MainActivityPresenter(this.view);
    }

    @Test
    public void showFabMenu() throws Exception {

        // Act
        this.target.showFabMenu();

        // Assert
        Mockito.verify(this.view).showFabMenu();
    }

    @Test
    public void hideFabMenu() throws Exception {

        // Act
        this.target.hideFabMenu();

        // Assert
        Mockito.verify(this.view).hideFabMenu();
    }

    @Test
    public void getFabMenuVisibility() throws Exception {

        // Act
        this.target.getFabMenuVisitibility();

        // Assert
        Mockito.verify(this.view).getFabMenuVisibility();
    }

    @Test
    public void checkPermissions() throws Exception {

        // Arrange
        String[] permissions = null;

        // Act
        this.target.checkPermissions(permissions);

        // Assert
        Mockito.verify(this.view).checkPermissions(permissions);
    }

    @Test
    public void requestPermissions() throws Exception {

        // Arrange
        String[] permissions = null;

        // Act
        this.target.requestPermissions(permissions);

        // Assert
        Mockito.verify(this.view).requestPermissions(permissions);
    }

    @Test
    public void setFabIcon() throws Exception {

        // Arrange
        int icon = 1;

        // Act
        this.target.setFabIcon(icon);

        // Assert
        Mockito.verify(this.view).setFabIcon(icon);
    }
}