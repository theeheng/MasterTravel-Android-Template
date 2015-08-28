package com.mastercard.travel.activity;

import com.mastercard.travel.fragment.BaseFragment;
import com.mastercard.travel.fragment.LoginFragment;

public class LoginActivity extends BaseActivityWithoutToolbar {

    @Override
    public Class<? extends BaseFragment> getFragmentClass() {
        return LoginFragment.class;
    }

}
