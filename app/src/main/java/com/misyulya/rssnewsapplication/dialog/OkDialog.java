package com.misyulya.rssnewsapplication.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.misyulya.rssnewsapplication.R;

public class OkDialog extends DialogFragment {

    public static final String MESSAGE_FOR_DIALOG = "com.misyulya.rssnewsapplication.dialog.messageForDialog";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String message = "";
        if (bundle != null) {
            message = bundle.getString(MESSAGE_FOR_DIALOG);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setPositiveButton(R.string.dialog_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }
}