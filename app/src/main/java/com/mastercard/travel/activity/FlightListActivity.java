package com.mastercard.travel.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.mastercard.travel.R;
import com.mastercard.travel.fragment.BaseFragment;
import com.mastercard.travel.fragment.FlightListFragment;
import com.mastercard.travel.model.FlightFilter;
import com.mastercard.travel.model.FlightSearch;
import com.mastercard.travel.util.DateFormater;

public class FlightListActivity extends BaseActivityWithNavigationDrawerAndBack {

    public static void startActivity(Context context, FlightSearch flightSearch, FlightFilter filter) {
        Intent intent = new Intent(context, FlightListActivity.class);
        intent.putExtras(createBundle(flightSearch, filter));
        context.startActivity(intent);
    }

    public static Bundle createBundle(FlightSearch flightSearch, FlightFilter filter) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(FlightDetailActivity.FLIGHT_SEARCH_EXTRA, flightSearch);
        bundle.putSerializable(FlightDetailActivity.FLIGHT_FILTER_EXTRA, filter);
        return bundle;
    }

    @Override
    public Class<? extends BaseFragment> getFragmentClass() {
        return FlightListFragment.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleAndSubtitle(R.string.find, 0);
        FlightSearch flightSearch = (FlightSearch) getIntent().getSerializableExtra(FlightDetailActivity.FLIGHT_SEARCH_EXTRA);
        ((TextView) findViewById(R.id.dateFilter)).setText(DateFormater.formatDateToShowInShortMonthAndDate(flightSearch.getDepartDate()) + " - " + DateFormater.formatDateToShowInShortMonthAndDate(flightSearch.getReturnDate()));
        ((TextView) findViewById(R.id.users_quantity)).setText(Integer.toString(flightSearch.getPassengersQuantity()));
        ((TextView) findViewById(R.id.room_or_cabin_class_quantity)).setText(flightSearch.getCabinClass().getNameResId());
        ((TextView) findViewById(R.id.room_or_cabin_class_quantity)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.economy_grey, 0, 0, 0);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_flight_list;
    }
}
