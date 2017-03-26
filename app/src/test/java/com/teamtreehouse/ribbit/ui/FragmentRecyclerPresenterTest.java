package com.teamtreehouse.ribbit.ui;

import android.support.v7.widget.RecyclerView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by javie on 3/25/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class FragmentRecyclerPresenterTest {

    private FragmentRecyclerPresenter target;

    @Mock
    public FragmentRecyclerView view;

    @Before
    public void setUp() throws Exception {

        target = new FragmentRecyclerPresenter(view);
    }

    @Test
    public void initializeRecyclerAdapter() throws Exception {

        // Arrange
        RecyclerView.Adapter adapter = null;

        // Act
        target.setRecyclerAdapter(adapter);

        // Assert
        Mockito.verify(view).setRecyclerAdapter(adapter);
    }

    @Test
    public void initializeRecyclerLayout() throws Exception {

        // Arrange
        RecyclerView.LayoutManager layoutManager = null;

        // Act
        target.setRecyclerLayout(layoutManager);

        // Assert
        Mockito.verify(view).setRecyclerLayout(layoutManager);
    }

    @Test
    public void initializeRecyclerSize() throws Exception {

        // Arrange
        boolean fixedSize = false;

        // Act
        target.setRecyclerFixedSize(fixedSize);

        // Assert
        Mockito.verify(view).setRecyclerFixedSize(fixedSize);
    }
}