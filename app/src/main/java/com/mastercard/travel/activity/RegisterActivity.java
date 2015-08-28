package com.mastercard.travel.activity;

import android.os.Bundle;

import com.mastercard.travel.R;
import com.mastercard.travel.fragment.BaseFragment;
import com.mastercard.travel.fragment.RegisterFragment;

public class RegisterActivity extends BaseActivityWithNavigationDrawerAndBack {

    @Override
    public Class<? extends BaseFragment> getFragmentClass() {
        return RegisterFragment.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createToolBarWithBack();
    }

    protected int getActionBarTitle() {
        return R.string.login_register;
    }
}
