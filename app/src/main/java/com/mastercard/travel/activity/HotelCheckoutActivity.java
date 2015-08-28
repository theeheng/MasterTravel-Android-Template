package com.mastercard.travel.activity;

import com.mastercard.travel.R;
import com.mastercard.travel.fragment.BaseFragment;
import com.mastercard.travel.fragment.HotelCheckoutFragment;

public class HotelCheckoutActivity extends BaseActivityWithNavigationDrawerAndBack {

    @Override
    public Class<? extends BaseFragment> getFragmentClass() {
        return HotelCheckoutFragment.class;
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.checkout;
    }
}
