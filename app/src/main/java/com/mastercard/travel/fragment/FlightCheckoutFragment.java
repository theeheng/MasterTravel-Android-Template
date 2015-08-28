package com.mastercard.travel.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import com.anypresence.masterpass_android_library.dto.PairCheckoutResponse;
import com.anypresence.sdk.master_travel_ecomm.models.Flight;
import com.mastercard.travel.Constants;
import com.mastercard.travel.R;
import com.mastercard.travel.activity.FlightDetailActivity;
import com.mastercard.travel.activity.FlightSuccessActivity;
import com.mastercard.travel.activity.HotelSuccessActivity;
import com.mastercard.travel.model.FlightSearch;
import com.mastercard.travel.util.DateFormater;

/**
 * Created by emi91_000 on 05/02/2015.
 */
public class FlightCheckoutFragment extends BaseFragment {

    private Flight flight;
    private FlightSearch flightSearch;

    private TextView fromAirportCode;
    private TextView toAirportCode;

    private TextView passengersQuantity;
    private TextView cabinClass;
    private TextView dateSearch;

    private TextView flightPrice;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flight_checkout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        flight = (Flight) getArguments().getSerializable(FlightDetailActivity.FLIGHT_EXTRA);
        flightSearch = (FlightSearch) getArguments().getSerializable(FlightDetailActivity.FLIGHT_SEARCH_EXTRA);

        BaseFragment fragment = new CheckoutLogicFragment();
        Bundle bundle = getArguments();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = this
                    .getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flight_checkout_logic, fragment,
                            fragment.getClass().getName());
            fragmentTransaction.commit();
        }
        initControls();
    }

    private void initControls() {

        fromAirportCode = (TextView) getView().findViewById(R.id.from_airport_code);
        fromAirportCode.setText(flight.getOriginationAirportCode());

        toAirportCode = (TextView) getView().findViewById(R.id.to_airport_code);
        toAirportCode.setText(flight.getDestinationAirportCode());

        passengersQuantity = (TextView) getView().findViewById(R.id.passengers_quantity);
        passengersQuantity.setText(String.valueOf(flightSearch.getPassengersQuantity()));

        cabinClass = (TextView) getView().findViewById(R.id.cabin_class);
        cabinClass.setText(flightSearch.getCabinClass().getNameResId());

        dateSearch = (TextView) getView().findViewById(R.id.dateFilter);
        dateSearch.setText(DateFormater.formatDateToShowInMonthAndDate(flightSearch.getDepartDate()) + " - " +
                DateFormater.formatDateToShowInMonthAndDate(flightSearch.getReturnDate()));

        ((ViewStub) getView().findViewById(R.id.stub)).inflate();

        flightPrice = (TextView) getView().findViewById(R.id.hotel_price);
        flightPrice.setText("$" + flight.getPrice() / 100);
    }

    @Override
    public void preCheckoutDidComplete(Boolean success, PairCheckoutResponse data, Throwable error) {
        super.preCheckoutDidComplete(success, data, error);
        if (success) {
            CheckoutLogicFragment checkoutLogicFragment = (CheckoutLogicFragment) getChildFragmentManager().findFragmentById(R.id.flight_checkout_logic);
            if (checkoutLogicFragment != null) {
                checkoutLogicFragment.preCheckoutDidComplete(success, data, error);
            }
        }
    }

    @Override
    public void checkoutDidComplete(Boolean success, Throwable error) {
        super.checkoutDidComplete(success,error);
        if (success) {
            Bundle bundle = getArguments();
            Intent intent = new Intent(getActivity(), FlightSuccessActivity.class);
            intent.putExtra(Constants.MASTERPASS, true);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }


    @Override
    public void pairCheckoutDidComplete(Boolean success, Throwable error) {
        super.pairCheckoutDidComplete(success, error);
        if (success) {
            Bundle bundle = getArguments();
            Intent intent = new Intent(getActivity(), FlightSuccessActivity.class);
            intent.putExtra(Constants.MASTERPASS, false);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    public void manualCheckoutDidComplete(Boolean success, Throwable error) {
        super.manualCheckoutDidComplete(success,error);
        if (success) {
            Bundle bundle = getArguments();
            Intent intent = new Intent(getActivity(), FlightSuccessActivity.class);
            intent.putExtra(Constants.MASTERPASS, false);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
