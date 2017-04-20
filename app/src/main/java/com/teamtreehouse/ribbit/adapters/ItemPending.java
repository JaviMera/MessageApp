package com.teamtreehouse.ribbit.adapters;

import android.view.View;

import com.teamtreehouse.ribbit.adapters.actions.ButtonAction;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by javie on 4/2/2017.
 */

public class ItemPending implements RecyclerItemType {

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

        return View.VISIBLE;
    }

    @Override
    public String cancelButtonText() {

        return "";
    }

    @Override
    public String acceptButtonText() {

        return "Pending Request";
    }

    @Override
    public boolean setAcceptButtonEnabled() {

        return false;
    }

    @Override
    public int editImageVisibility() {

        return View.GONE;
    }
}
