package com.mastercard.travel.activity;

import com.mastercard.travel.R;
import com.mastercard.travel.fragment.BaseFragment;
import com.mastercard.travel.fragment.FlightCheckoutFragment;

public class FlightCheckoutActivity extends BaseActivityWithNavigationDrawerAndBack {

    @Override
    public Class<? extends BaseFragment> getFragmentClass() {
        return FlightCheckoutFragment.class;
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.checkout;
    }
}
