package com.mastercard.travel.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anypresence.masterpass_android_library.dto.PreCheckoutResponse;
import com.anypresence.masterpass_android_library.interfaces.FutureCallback;
import com.mastercard.travel.Application;
import com.mastercard.travel.R;
import com.mastercard.travel.dialogs.PairDialog;
import com.mastercard.travel.view.TwoLinesEditText;

/**
 * Created by ignacio on 10/02/15
 * Copyright (c) 2015 AnyPresence, Inc. All rights reserved..
 */
public class ProfileFragment extends BaseFragment {

    private TwoLinesEditText firstName;

    private TextView learnMore;
    private TextView pairedMasterPass;
    private ImageView connectMasterPass;
    private FutureCallback<PreCheckoutResponse> preCheckoutCallback = new FutureCallback<PreCheckoutResponse>() {

        @Override
        public void onSuccess(PreCheckoutResponse preCheckoutResponse) {
            ProfileFragment.this.setPairStatus(true);
            updatePairStatus();
        }

        @Override
        public void onFailure(Throwable throwable) {
            ProfileFragment.this.setPairStatus(false);
            updatePairStatus();
        }
    };
    private View.OnClickListener onConnectMasterPass = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showProgress();
            getMCLibrary().pair(ProfileFragment.this);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initControls();

        setListeners();

        updatePairStatus();
    }

    private void initControls() {

        firstName = (TwoLinesEditText) getView().findViewById(R.id.firts_name);
        firstName.setText(Application.getInstance().getUser().getEmail());
        firstName.setEditable(false);

        pairedMasterPass = (TextView) getView().findViewById(R.id.paired_master_pass);

        connectMasterPass = (ImageView) getView().findViewById(R.id.profile_connect_masterpass);
        connectMasterPass.setOnClickListener(onConnectMasterPass);
        learnMore = (TextView) getView().findViewById(R.id.learn_more);
    }

    private void setListeners() {

        learnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLearnMore();
            }
        });
    }

    private void showLearnMore() {

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity()).
                setTitle(R.string.my_profile_learn_more_dialog_title).
                setMessage(R.string.my_profile_learn_more_dialog_message).
                setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        Dialog dialog = alert.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        updatePairStatus();
        getMCLibrary().preCheckout(this, preCheckoutCallback);
    }

    private void updatePairStatus() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    connectMasterPass.setVisibility(isAppPaired() ? View.GONE : View.VISIBLE);
                    pairedMasterPass.setVisibility(isAppPaired() ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    @Override
    public void pairingDidComplete(Boolean success, Throwable error) {
        super.pairingDidComplete(success, error);
        if (success)
            (new PairDialog()).show(getFragmentManager(), "pair_dialog");
    }
}
