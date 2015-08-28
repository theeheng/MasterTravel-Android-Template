package com.mastercard.travel.activity;

import android.os.Bundle;

import com.mastercard.travel.R;
import com.mastercard.travel.fragment.BaseFragment;
import com.mastercard.travel.fragment.ProfileFragment;

public class ProfileActivity extends BaseActivityWithNavigationDrawerWithToggle {

    @Override
    public Class<? extends BaseFragment> getFragmentClass() {
        return ProfileFragment.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createToolBar();
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.my_profile;
    }
}
