package com.mastercard.travel.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anypresence.masterpass_android_library.dto.PreCheckoutResponse;
import com.anypresence.masterpass_android_library.interfaces.FutureCallback;
import com.anypresence.sdk.acl.AuthManager;
import com.anypresence.sdk.acl.IAuthenticatable;
import com.anypresence.sdk.callbacks.APCallback;
import com.anypresence.sdk.master_travel_ecomm.models.User;
import com.mastercard.travel.Application;
import com.mastercard.travel.Constants;
import com.mastercard.travel.R;
import com.mastercard.travel.activity.HomeActivity;
import com.mastercard.travel.activity.RegisterActivity;
import com.mastercard.travel.interfaces.LoginListener;

/**
 * Created by emi91_000 on 05/02/2015.
 */
public class LoginFragment extends BaseFragment {

    TextView.OnEditorActionListener onDone = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onLogin();
                return true;
            }
            return false;
        }
    };
    private EditText username;
    private EditText password;
    private CheckBox remember;
    private TextView register;
    private View.OnClickListener onSignInClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onLogin();
        }

    };
    private FutureCallback<PreCheckoutResponse> callbackPreCheckout = new FutureCallback<PreCheckoutResponse>() {

        @Override
        public void onSuccess(PreCheckoutResponse preCheckoutResponse) {
            LoginFragment.this.setPairStatus(true);
            openHome();
        }

        @Override
        public void onFailure(Throwable throwable) {
            LoginFragment.this.setPairStatus(false);
            openHome();
        }
    };

    private LoginListener loginListener = new LoginListener() {
        @Override
        public void onLoginFailed(Throwable ex) {
            Log.e(LOG_TAG, ex.toString());
            unsuccessfulLogin(ex);
        }

        @Override
        public void onLoginSuccess(IAuthenticatable user) {
            if (user instanceof User) {
                Application.getInstance().setUser((User) user);
            }
            setRememberUser();
            getMCLibrary().preCheckout(LoginFragment.this, callbackPreCheckout);
        }
    };

    private void openHome() {
        if (getActivity() != null & !getActivity().isFinishing()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                    mConnectionProgressDialog.dismiss();
                }
            });
            getActivity().finish();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, null, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        username = (EditText) view.findViewById(R.id.login_username);
        password = (EditText) view.findViewById(R.id.login_password);
        password.setOnEditorActionListener(onDone);
        remember = (CheckBox) view.findViewById(R.id.login_remember_password);
        register = (TextView) view.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RegisterActivity.class));
            }
        });
        view.findViewById(R.id.login_sign_in).setOnClickListener(onSignInClick);
        loadRememberUser();
        mConnectionProgressDialog.dismiss();
    }

    private void loadRememberUser() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String usernameValue = sp.getString(Constants.SP_USERNAME, null);
        if (usernameValue != null) {
            username.setText(usernameValue);
            password.requestFocus();
        }
    }

    private void setRememberUser() {
        String username = this.username.getText().toString();
        if (!remember.isChecked())
            username = null;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(Constants.SP_USERNAME, username);
        edit.commit();
    }

    private void onLogin() {
        String usernameValue = username.getText().toString();
        String passwordValue = password.getText().toString();

        if (usernameValue.isEmpty() || passwordValue.isEmpty()) {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(), getString(R.string.login_empty_error), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            mConnectionProgressDialog.show();
            User user = new User();
            user.setEmail(usernameValue);
            user.setPassword(passwordValue);
            callLogin(user, loginListener);
        }
    }

    public void callLogin(User user, final LoginListener listener) {
        AuthManager manager = new AuthManager.Builder(user.getClass()).useAnyPresenceAuth(user).build();
        manager.authenticate(new APCallback<IAuthenticatable>() {
            @Override
            public void finished(IAuthenticatable result, Throwable ex) {
                if (ex != null) {
                    ex.printStackTrace();
                    if (listener != null) listener.onLoginFailed(ex);
                    return;
                }
            }

            @Override
            public void onSuccess(IAuthenticatable object) {
                super.onSuccess(object);
                listener.onLoginSuccess(object);
            }
        });
    }


    public void unsuccessfulLogin(final Throwable e) {
        mConnectionProgressDialog.dismiss();
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if (e instanceof NullPointerException) {
                    Toast.makeText(getActivity(), R.string.no_internet_connection, Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getActivity(), R.string.login_invalid_error, Toast.LENGTH_LONG).show();
            }
        });
    }
}
