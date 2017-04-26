package com.teamtreehouse.ribbit.ui;

import android.support.v7.widget.RecyclerView;

import com.teamtreehouse.ribbit.ui.fragments.inbox.FragmentInboxView;
import com.teamtreehouse.ribbit.ui.fragments.inbox.FragmentInboxPresenter;

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
public class FragmentInboxPresenterTest {

    private FragmentInboxPresenter target;

    @Mock
    public FragmentInboxView view;

    @Before
    public void setUp() throws Exception {

        target = new FragmentInboxPresenter(view);
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

    @Test
    public void setEmptyMessageViewVisibility() throws Exception {

        // Arrange
        boolean visible = true;

        // Act
        target.setEmptyTextViewVisibility(visible);

        // Assert
        Mockito.verify(view).setEmptyTextViewVisibility(visible);
    }
}