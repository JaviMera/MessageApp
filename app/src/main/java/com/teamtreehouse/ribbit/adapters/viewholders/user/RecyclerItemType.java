package com.teamtreehouse.ribbit.adapters.viewholders.user;

import com.teamtreehouse.ribbit.adapters.viewholders.actions.ButtonAction;
import com.teamtreehouse.ribbit.models.User;

import java.util.List;

/**
 * Created by javie on 4/2/2017.
 */

public interface RecyclerItemType {

    List<ButtonAction> getNegativeButtonResponses();
    List<ButtonAction> getPositiveButtonResponses();
    User cancelUserType(User user);
    User acceptUserType(User user);
    int cancelButtonVisibility();
    int acceptButtonVisibility();
    String cancelButtonText();
    String acceptButtonText();
    boolean setAcceptButtonEnabled();
    int editImageVisibility();
}
