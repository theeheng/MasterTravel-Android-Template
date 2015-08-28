package com.mastercard.travel.activity;

import com.mastercard.travel.R;
import com.mastercard.travel.fragment.BaseFragment;
import com.mastercard.travel.fragment.FlightFindFragment;

public class FlightFindActivity extends BaseActivityWithNavigationDrawerAndBack {

    @Override
    public Class<? extends BaseFragment> getFragmentClass() {
        return FlightFindFragment.class;
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.find_flights;
    }
}
