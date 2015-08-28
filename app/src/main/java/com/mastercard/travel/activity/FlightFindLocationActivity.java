package com.mastercard.travel.activity;

import android.os.Bundle;

import com.mastercard.travel.R;
import com.mastercard.travel.fragment.BaseFragment;
import com.mastercard.travel.fragment.FlightFindLocationFragment;

/**
 * Created by ignacio on 16/02/2015.
 */
public class FlightFindLocationActivity extends BaseActivityWithoutToolbar {

    @Override
    public Class<? extends BaseFragment> getFragmentClass() {
        return FlightFindLocationFragment.class;
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
