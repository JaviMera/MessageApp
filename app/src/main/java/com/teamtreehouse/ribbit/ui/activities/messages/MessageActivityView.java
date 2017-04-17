package com.teamtreehouse.ribbit.ui.activities.messages;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.Animation;

import com.teamtreehouse.ribbit.ui.activities.ActivityView;

/**
 * Created by javie on 4/9/2017.
 */
public interface MessageActivityView extends ActivityView {

    void recipientRemoved(int position);
    void setSendImageVisibility(int visibility);
    void setMessageEditTextVisibility(int visible);
    void setSendLayoutVisibility(int visibility);
    void setProgressBarVisibility(int visibility);
    void setSendImageViewAnimation(Animation anim);
    void setMessageEditTextAnimation(Animation animation);
    void replaceFragment(int containerId, Fragment fragment);
    <TFragment> TFragment findFragmentById(int containerId);
}
