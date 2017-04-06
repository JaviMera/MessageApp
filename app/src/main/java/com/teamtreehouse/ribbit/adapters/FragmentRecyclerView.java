package com.teamtreehouse.ribbit.adapters;

import com.teamtreehouse.ribbit.adapters.viewholders.actions.ButtonAction;
import com.teamtreehouse.ribbit.models.User;

import java.util.List;

/**
 * Created by javie on 4/2/2017.
 */

public interface FragmentRecyclerView {

    void onButtonClick(List<ButtonAction> buttonActions, int position);
}
