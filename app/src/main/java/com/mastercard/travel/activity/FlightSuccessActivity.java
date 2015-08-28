package com.mastercard.travel.activity;

import android.content.Intent;

import com.mastercard.travel.R;
import com.mastercard.travel.fragment.BaseFragment;
import com.mastercard.travel.fragment.FlightSuccessFragment;

public class FlightSuccessActivity extends BaseActivityWithActionBar {
    @Override
    public Class<? extends BaseFragment> getFragmentClass() {
        return FlightSuccessFragment.class;
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(0, R.anim.get_out_back);
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.success;
    }
}
