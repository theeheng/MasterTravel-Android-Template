package com.mastercard.travel.activity;

import com.mastercard.travel.R;
import com.mastercard.travel.fragment.BaseFragment;
import com.mastercard.travel.fragment.HotelFindFragment;

public class HotelFindActivity extends BaseActivityWithNavigationDrawerAndBack {

    @Override
    public Class<? extends BaseFragment> getFragmentClass() {
        return HotelFindFragment.class;
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.find_hotels;
    }
}
