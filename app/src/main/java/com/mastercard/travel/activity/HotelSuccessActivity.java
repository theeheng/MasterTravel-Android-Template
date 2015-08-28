package com.mastercard.travel.activity;

import android.content.Intent;

import com.mastercard.travel.Application;
import com.mastercard.travel.R;
import com.mastercard.travel.fragment.BaseFragment;
import com.mastercard.travel.fragment.HotelSuccessFragment;

public class HotelSuccessActivity extends BaseActivityWithActionBar {
    @Override
    public Class<? extends BaseFragment> getFragmentClass() {
        return HotelSuccessFragment.class;
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(0, R.anim.get_out_back);
        Application application = Application.getInstance();
        application.setOrderHeader(null);
        application.setPairCheckout(null);
        application.setPreCheckout(null);
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.success;
    }
}
