package com.teamtreehouse.ribbit.adapters.viewholders.user;

import android.view.View;

import com.teamtreehouse.ribbit.adapters.viewholders.actions.ButtonAccept;
import com.teamtreehouse.ribbit.adapters.viewholders.actions.ButtonAction;
import com.teamtreehouse.ribbit.adapters.viewholders.actions.ButtonCancel;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.models.UserFriend;
import com.teamtreehouse.ribbit.models.UserRequest;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by javie on 4/2/2017.
 */

public class ItemInvite implements RecyclerItemType {
    @Override
    public List<ButtonAction> getNegativeButtonResponses() {

        return new LinkedList<ButtonAction>(){
            {
                add(new ButtonCancel());
            }
        };
    }

    @Override
    public List<ButtonAction> getPositiveButtonResponses() {
        return new LinkedList<ButtonAction>(){
            {
                add(new ButtonCancel());
                add(new ButtonAccept());
            }
        };
    }

    @Override
    public User cancelUserType(User user) {

        return new UserRequest(user.getId(), user.getUsername());
    }

    @Override
    public User acceptUserType(User user) {

        return new UserFriend(user.getId(), user.getUsername());
    }

    @Override
    public int cancelButtonVisibility() {

        return View.VISIBLE;
    }

    @Override
    public int acceptButtonVisibility() {

        return View.VISIBLE;
    }

    @Override
    public String cancelButtonText() {

        return "Cancel";
    }

    @Override
    public String acceptButtonText() {

        return "Accept";
    }

    @Override
    public boolean setAcceptButtonEnabled() {

        return true;
    }

    @Override
    public int editImageVisibility() {

        return View.GONE;
    }
}
