package com.teamtreehouse.ribbit.adapters;

import com.teamtreehouse.ribbit.adapters.actions.ButtonAction;

import java.util.List;

/**
 * Created by javie on 4/2/2017.
 */

public interface RecyclerItemType {

    List<ButtonAction> getNegativeButtonResponses();
    List<ButtonAction> getPositiveButtonResponses();
    int cancelButtonVisibility();
    int acceptButtonVisibility();
    String cancelButtonText();
    String acceptButtonText();
    boolean setAcceptButtonEnabled();
    int editImageVisibility();
}
