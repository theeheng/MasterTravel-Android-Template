package com.mastercard.travel.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

public abstract class BaseDialogFragment extends DialogFragment {

    private final static String TAG = "defaultTag";
    protected int resId = 0;
    private String string;

    public BaseDialogFragment() {
        super();
    }

    public BaseDialogFragment(int resId) {
        super();
        this.resId = resId;
    }

    public BaseDialogFragment(String string) {
        super();
        this.string = string;
    }

    public void show(FragmentManager fm) {
        this.show(fm, BaseDialogFragment.TAG);
    }

    @Override
    public void show(FragmentManager fm, String tag) {
        super.show(fm, tag);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                this.getActivity());

        if (resId == 0) {
            builder.setMessage(string);
        } else {
            builder.setMessage(resId);
        }

        this.configureBuilder(builder);
        return builder.create();
    }

    protected abstract void configureBuilder(AlertDialog.Builder builder);
}
