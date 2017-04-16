package com.teamtreehouse.ribbit.ui.activities;

import android.view.animation.Animation;

/**
 * Created by javie on 4/15/2017.
 */

public class MessageActivityPresenter {

    private MessageActivityView view;

    public MessageActivityPresenter(MessageActivityView view) {

        this.view = view;
    }

    public void setSendImageVisibility(int visible) {

        this.view.setSendImageVisibility(visible);
    }

    public void setMessageEditTextVisibility(int visible) {

        this.view.setMessageEditTextVisibility(visible);
    }

    public void setSendLayoutVisibility(int visibility) {

        this.view.setSendLayoutVisibility(visibility);
    }

    public void setProgressbarVisibility(int visibility) {

        this.view.setProgressBarVisibility(visibility);
    }

    public void setSendImageViewAnimation(Animation anim) {

        this.view.setSendImageViewAnimation(anim);
    }

    public void setMessageEditTextAnimation(Animation anim) {

        this.view.setMessageEditTextAnimation(anim);
    }
}
