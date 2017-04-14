package com.teamtreehouse.ribbit.ui.activities;

/**
 * Created by javie on 4/9/2017.
 */
public interface MessageActivityView extends ActivityView {

    void recipientRemoved(int position);
    void showSendButton();
    void hideSendButton();
    void showInputEditText();
    void hideInputEditText();
    void showProgress();
    void hideProgress();
}
