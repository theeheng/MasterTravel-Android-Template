package com.mastercard.travel.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;


@SuppressLint("ValidFragment")
public class WarningDialog extends BaseDialogFragment {

    private OnAcceptListener listener;

    public WarningDialog(Integer integer) {
        super(integer);
    }

    public WarningDialog(String messageResId) {
        super(messageResId);
    }

    protected void onPositiveButtonClicked() {
        if (listener != null) {
            listener.onAccept(this.getTag());
        }
    }

    public void setListener(OnAcceptListener listener) {
        this.listener = listener;
    }

    @Override
    protected void configureBuilder(Builder builder) {
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        WarningDialog.this.dismiss();
                        WarningDialog.this.onPositiveButtonClicked();
                    }
                });
    }

}
