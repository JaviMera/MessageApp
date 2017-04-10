package com.teamtreehouse.ribbit.ui;

import android.content.Context;
import android.support.v4.app.FragmentManager;

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
    public void setAdapter() throws Exception {

        // Arrange
        FragmentManager fragmentManager = null;
        FragmentPager[] fragments = null;

        // Act
        this.target.setPagerAdapter(fragmentManager, fragments);

        // Assert
        Mockito.verify(this.view).setPagerAdapter(fragmentManager, fragments);
    }
}