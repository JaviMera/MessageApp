package com.teamtreehouse.ribbit.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;

/**
 * Created by javie on 3/24/2017.
 */

public class DialogFragmentError extends DialogFragment {

    private DialogInterface.OnClickListener listener;

    public static DialogFragmentError newInstance(String message, String title) {

        DialogFragmentError dialogFragment = new DialogFragmentError();
        Bundle bundle = new Bundle();
        bundle.putString("dialog_message", message);
        bundle.putString("dialog_title", title);
        dialogFragment.setArguments(bundle);

        return dialogFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (DialogInterface.OnClickListener)context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Get the message from bundle
        String message = getArguments().getString("dialog_message");

        // Get the title from bundle
        String title = getArguments().getString("dialog_title");

        return new AlertDialog
            .Builder(getContext())
            .setMessage(message)
            .setTitle(title)
            .setPositiveButton(android.R.string.ok, listener)
            .create();
    }
}
