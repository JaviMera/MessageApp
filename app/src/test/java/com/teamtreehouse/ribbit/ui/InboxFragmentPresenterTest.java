package com.teamtreehouse.ribbit.ui;

import android.support.v7.widget.RecyclerView;

import com.teamtreehouse.ribbit.ui.fragments.messages.InboxFragmentPresenter;
import com.teamtreehouse.ribbit.ui.fragments.messages.InboxFragmentView;

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
public class InboxFragmentPresenterTest {

    private InboxFragmentPresenter target;

    @Mock
    public InboxFragmentView view;

    @Before
    public void setUp() throws Exception {

        target = new InboxFragmentPresenter(view);
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

    @Test
    public void setRefresherTheme() throws Exception {

        // Arrange
        int[] colors = new int[0];

        // Act
        target.setRefresherTheme(colors);

        // Assert
        Mockito.verify(view).setRefresherColors(colors);
    }

    @Test
    public void setRefresherListener() throws Exception {

        // Act
        target.setRefresherListener();

        // Assert
        Mockito.verify(view).setRefresherListener();
    }
}