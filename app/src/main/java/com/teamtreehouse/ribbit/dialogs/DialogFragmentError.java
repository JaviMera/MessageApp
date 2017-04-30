package com.teamtreehouse.ribbit.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by javie on 3/24/2017.
 */

public class DialogFragmentError extends DialogFragment {

    public static final String DIALOG_ERROR_MESSAGE_KEY = "dialog_message";
    public static final String DIALOG_ERROR_TITLE_KEY = "dialog_title";

    public static DialogFragmentError newInstance(String message, String title) {

        DialogFragmentError dialogFragment = new DialogFragmentError();
        Bundle bundle = new Bundle();
        bundle.putString(DIALOG_ERROR_MESSAGE_KEY, message);
        bundle.putString(DIALOG_ERROR_TITLE_KEY, title);
        dialogFragment.setArguments(bundle);

        return dialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Get the message from bundle
        String message = getArguments().getString(DIALOG_ERROR_MESSAGE_KEY);

        // Get the title from bundle
        String title = getArguments().getString(DIALOG_ERROR_TITLE_KEY);

        return new AlertDialog
            .Builder(getContext())
            .setMessage(message)
            .setTitle(title)
            .setPositiveButton(android.R.string.ok, null)
            .create();
    }
}
