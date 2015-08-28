package com.mastercard.travel.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mastercard.travel.R;
import com.mastercard.travel.activity.FlightDetailActivity;
import com.mastercard.travel.model.CabinClass;
import com.mastercard.travel.model.FlightSearch;

/**
 * Created by emi91_000 on 05/02/2015.
 */
public class FlightCabinClassFragment extends BaseFragment {

    private TextView economy;
    private TextView premium_economy;
    private TextView business;
    private TextView firstClass;
    private FlightSearch flightSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cabin_class, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initControls(view);
        setListeners();
        if (getArguments().getSerializable(FlightDetailActivity.FLIGHT_SEARCH_EXTRA) != null) {
            flightSearch = (FlightSearch) getArguments().getSerializable(FlightDetailActivity.FLIGHT_SEARCH_EXTRA);
            switch (flightSearch.getCabinClass()) {
                case ECONOMY:
                    setSelected(economy);
                    break;
                case PREMIUM_ECONOMY:
                    setSelected(business);
                    break;
                case BUSINESS:
                    setSelected(premium_economy);
                    break;
                case FIRST_CLASS:
                    setSelected(firstClass);
                    break;
                default:
                    break;
            }
        }

    }

    private void setListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markAndSort(v);
            }
        };
        economy.setOnClickListener(listener);
        premium_economy.setOnClickListener(listener);
        business.setOnClickListener(listener);
        firstClass.setOnClickListener(listener);
    }

    private void initControls(View view) {
        economy = (TextView) view.findViewById(R.id.economy);
        premium_economy = (TextView) view.findViewById(R.id.premium_economy);
        business = (TextView) view.findViewById(R.id.business);
        firstClass = (TextView) view.findViewById(R.id.first_class);
    }

    public void setSelected(View view) {
        if (economy == view) flightSearch.setCabinClass(CabinClass.ECONOMY);
        if (premium_economy == view) flightSearch.setCabinClass(CabinClass.PREMIUM_ECONOMY);
        if (business == view) flightSearch.setCabinClass(CabinClass.BUSINESS);
        if (firstClass == view) flightSearch.setCabinClass(CabinClass.FIRST_CLASS);

        economy.setCompoundDrawablesWithIntrinsicBounds(0, 0, view == economy ? R.drawable.check : 0, 0);
        premium_economy.setCompoundDrawablesWithIntrinsicBounds(0, 0, view == premium_economy ? R.drawable.check : 0, 0);
        business.setCompoundDrawablesWithIntrinsicBounds(0, 0, view == business ? R.drawable.check : 0, 0);
        firstClass.setCompoundDrawablesWithIntrinsicBounds(0, 0, firstClass == view ? R.drawable.check : 0, 0);
    }

    public void markAndSort(View view) {
        setSelected(view);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FlightDetailActivity.FLIGHT_SEARCH_EXTRA, flightSearch);
        intent.putExtras(bundle);
        getActivity().setIntent(intent);
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

}
