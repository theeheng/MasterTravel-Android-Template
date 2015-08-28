package com.mastercard.travel.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.mastercard.travel.R;
import com.mastercard.travel.fragment.BaseFragment;
import com.mastercard.travel.fragment.FlightCabinClassFragment;
import com.mastercard.travel.model.FlightSearch;

public class FlightCabinClassActivity extends BaseActivityWithoutToolbar {

    public static void startActivityForResult(Activity context, int requestCode, FlightSearch flightSearch) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(FlightDetailActivity.FLIGHT_SEARCH_EXTRA, flightSearch);
        Intent intent = new Intent(context, FlightCabinClassActivity.class);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    public Class<? extends BaseFragment> getFragmentClass() {
        return FlightCabinClassFragment.class;
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
