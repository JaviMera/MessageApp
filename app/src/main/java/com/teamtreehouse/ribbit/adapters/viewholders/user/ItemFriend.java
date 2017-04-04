package com.teamtreehouse.ribbit.adapters.viewholders.user;

import android.view.View;

import com.teamtreehouse.ribbit.adapters.viewholders.actions.ButtonAction;
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
    public User cancelUserType(User user) {
        return null;
    }

    @Override
    public User acceptUserType(User user) {
        return null;
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
