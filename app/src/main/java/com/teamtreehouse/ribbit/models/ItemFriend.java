package com.teamtreehouse.ribbit.models;

import android.view.View;

import com.teamtreehouse.ribbit.adapters.RecyclerItemType;
import com.teamtreehouse.ribbit.adapters.actions.ButtonAction;
import com.teamtreehouse.ribbit.models.User;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by javie on 4/2/2017.
 */

public class ItemFriend implements RecyclerItemType {

    @Override
    public List<ButtonAction> getNegativeButtonResponses() {
        return new LinkedList<>();
    }

    @Override
    public List<ButtonAction> getPositiveButtonResponses() {
        return new LinkedList<>();
    }

    @Override
    public int cancelButtonVisibility() {

        return View.GONE;
    }

    @Override
    public int acceptButtonVisibility() {

        return View.GONE;
    }

    @Override
    public String cancelButtonText() {

        return "";
    }

    @Override
    public String acceptButtonText() {

        return "";
    }

    @Override
    public boolean setAcceptButtonEnabled() {

        return true;
    }

    @Override
    public int editImageVisibility() {

        return View.VISIBLE;
    }
}