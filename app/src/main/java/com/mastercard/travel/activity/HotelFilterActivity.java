package com.mastercard.travel.activity;

import android.os.Bundle;

import com.mastercard.travel.R;
import com.mastercard.travel.fragment.BaseFragment;
import com.mastercard.travel.fragment.HotelFilterFragment;

public class HotelFilterActivity extends BaseActivityWithoutToolbar {

    @Override
    public Class<? extends BaseFragment> getFragmentClass() {
        return HotelFilterFragment.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.get_in_to_up, R.anim.get_out_only_alpha);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.get_in_to_down);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.get_in_to_down);
    }
}
