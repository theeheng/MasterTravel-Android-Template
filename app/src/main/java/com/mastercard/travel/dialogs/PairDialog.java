package com.mastercard.travel.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.mastercard.travel.R;

/**
 * Created by diego.rotondale on 2/10/2015.
 * Copyright (c) 2015 AnyPresence, Inc. All rights reserved.
 */
public class PairDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.profile_pair_title)
                .setMessage(R.string.profile_pair_message)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dismiss();
                            }
                        }
                )
                .create();
    }
}
