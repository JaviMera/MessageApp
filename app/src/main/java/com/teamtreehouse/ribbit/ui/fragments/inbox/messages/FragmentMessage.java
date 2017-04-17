package com.teamtreehouse.ribbit.ui.fragments.inbox.messages;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.teamtreehouse.ribbit.ui.activities.messages.MessageActivityView;

/**
 * Created by javie on 4/16/2017.
 */

public abstract class FragmentMessage<TValue> extends Fragment implements FragmentMessageView<TValue> {

    protected MessageActivityView parent;
    protected TValue value;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.parent = (MessageActivityView)context;
    }

    @Override
    public TValue getValue() {

        return this.value;
    }
}
