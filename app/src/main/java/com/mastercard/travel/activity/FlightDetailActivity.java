package com.mastercard.travel.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.anypresence.sdk.master_travel_ecomm.models.Flight;
import com.mastercard.travel.R;
import com.mastercard.travel.fragment.BaseFragment;
import com.mastercard.travel.fragment.FlightDetailFragment;
import com.mastercard.travel.model.FlightSearch;

public class FlightDetailActivity extends BaseActivityWithNavigationDrawerAndBack {
    public final static String FLIGHT_EXTRA = "flightExtra";
    public final static String FLIGHT_FILTER_EXTRA = "flightFilter";
    public final static String FLIGHT_SEARCH_EXTRA = "flightSearch";

    public static void startActivity(Context context, Flight flight, FlightSearch flightSearch) {
        Intent intent = new Intent(context, FlightDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(FLIGHT_EXTRA, flight);
        bundle.putSerializable(FLIGHT_SEARCH_EXTRA, flightSearch);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public Class<? extends BaseFragment> getFragmentClass() {
        return FlightDetailFragment.class;
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.detail;
    }

}
