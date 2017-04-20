package com.teamtreehouse.ribbit.adapters;

import android.view.View;

import com.teamtreehouse.ribbit.adapters.actions.ButtonAccept;
import com.teamtreehouse.ribbit.adapters.actions.ButtonAction;
import com.teamtreehouse.ribbit.adapters.actions.ButtonCancel;

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
                add(new ButtonAccept());
            }
        };
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
