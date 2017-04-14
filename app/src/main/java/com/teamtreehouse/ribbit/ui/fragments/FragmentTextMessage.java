package com.teamtreehouse.ribbit.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamtreehouse.ribbit.ui.activities.MessageActivity;

/**
 * Created by javie on 4/14/2017.
 */

public class FragmentTextMessage extends Fragment{

    public static Fragment newInstance() {

        return new FragmentTextMessage();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        ((MessageActivity)getActivity()).hideProgress();
        return view;
    }
}
