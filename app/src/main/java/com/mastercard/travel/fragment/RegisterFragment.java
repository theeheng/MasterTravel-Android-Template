package com.mastercard.travel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.anypresence.rails_droid.IAPFutureCallback;
import com.anypresence.sdk.master_travel_ecomm.models.User;
import com.mastercard.travel.R;

/**
 * Created by emi91_000 on 05/02/2015.
 */
public class RegisterFragment extends BaseFragment {

    private EditText emailEdit;
    private IAPFutureCallback<?> userCallback = new IAPFutureCallback<Object>() {
        @Override
        public void finished(Object o, Throwable throwable) {
            Log.e(LOG_TAG, throwable.toString());
            dismissProgressInThread();
        }

        @Override
        public void onSuccess(Object o) {
            finishActivity();
        }

        @Override
        public void onFailure(Throwable throwable) {
            Log.e(LOG_TAG, throwable.toString());
            dismissProgressInThread();
        }
    };
    private View.OnClickListener onRegisterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = emailEdit.getText().toString();
            if (email.isEmpty()) {
                emailEdit.setError(getActivity().getString(R.string.register_empty));
            } else {
                mConnectionProgressDialog.show();
                User user = new User();
                user.setEmail(email);
                user.saveInBackground(userCallback);
            }
        }
    };
    private View.OnClickListener onEmailEditClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            emailEdit.setError(null);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, null, false);
    }

    private void dismissProgressInThread() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                emailEdit.setError(getString(R.string.register_error));
                mConnectionProgressDialog.dismiss();
            }
        });
    }

    private void finishActivity() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mConnectionProgressDialog.dismiss();
                getActivity().finish();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View register = view.findViewById(R.id.dialog_ok);
        register.setOnClickListener(onRegisterClickListener);
        emailEdit = (EditText) view.findViewById(R.id.dialog_edit);
        emailEdit.setOnClickListener(onEmailEditClickListener);
    }
}
