package com.teamtreehouse.ribbit.ui.fragments.inbox.view;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.teamtreehouse.ribbit.models.Auth;
import com.teamtreehouse.ribbit.models.users.User;
import com.teamtreehouse.ribbit.ui.activities.messages.view.ViewMessageActivity;

/**
 * Created by javie on 4/16/2017.
 */

public abstract class ViewFragmentMessage extends Fragment {

    protected ViewMessageActivity parent;
    protected User currentUser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.parent = (ViewMessageActivity) context;
        currentUser = Auth.getInstance().getUser();
    }
}
