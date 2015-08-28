package com.mastercard.travel.activity;

import android.os.Bundle;

import com.mastercard.travel.fragment.BaseFragment;
import com.mastercard.travel.fragment.HomeFragment;

public class HomeActivity extends BaseActivityWithNavigationDrawerWithToggle {

    @Override
    public Class<? extends BaseFragment> getFragmentClass() {
        return HomeFragment.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTextIconInActionBar();
    }
}
