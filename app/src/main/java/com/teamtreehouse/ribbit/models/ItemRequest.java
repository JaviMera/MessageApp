package com.teamtreehouse.ribbit.models;

import android.view.View;

import com.teamtreehouse.ribbit.adapters.RecyclerItemType;
import com.teamtreehouse.ribbit.adapters.actions.ButtonAction;
import com.teamtreehouse.ribbit.adapters.actions.ButtonRequest;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.models.UserSender;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by javie on 4/2/2017.
 */

public class ItemRequest implements RecyclerItemType {

    @Override
    public List<ButtonAction> getNegativeButtonResponses() {

        return new LinkedList<>();
    }

    @Override
    public List<ButtonAction> getPositiveButtonResponses() {

        return new LinkedList<ButtonAction>(){
            {
                add(new ButtonRequest());
            }
        };
    }

    @Override
    public int cancelButtonVisibility() {

        return View.GONE;
    }

    @Override
    public int acceptButtonVisibility() {

        return View.VISIBLE;
    }

    @Override
    public String cancelButtonText() {

        return "";
    }

    @Override
    public String acceptButtonText() {

        return "friend request";
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
