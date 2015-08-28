package com.mastercard.travel.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.anypresence.sdk.master_travel_ecomm.models.Flight;
import com.mastercard.travel.Application;
import com.mastercard.travel.R;
import com.mastercard.travel.activity.FlightDetailActivity;
import com.mastercard.travel.activity.HomeActivity;
import com.mastercard.travel.model.FlightSearch;
import com.mastercard.travel.util.DateFormater;

/**
 * Created by emi91_000 on 05/02/2015.
 */
public class FlightSuccessFragment extends BaseFragment {

    private Flight flight;
    private FlightSearch flightSearch;

    private TextView fromAirportCode;
    private TextView toAirportCode;

    private ImageView arrows;
    private RatingBar hotelRating;

    private TextView guestQuantity;
    private TextView cabinClass;

    private TextView dateSearch;

    private TextView flightPrice;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flight_success, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        flight = (com.anypresence.sdk.master_travel_ecomm.models.Flight) getArguments().getSerializable(FlightDetailActivity.FLIGHT_EXTRA);
        flightSearch = (FlightSearch) getArguments().getSerializable(FlightDetailActivity.FLIGHT_SEARCH_EXTRA);

        initControls();

        getView().findViewById(R.id.doneButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Application application = Application.getInstance();
                application.setOrderHeader(null);
                application.setPairCheckout(null);
                application.setPreCheckout(null);
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
    }

    private void initControls() {
        fromAirportCode = (TextView) getView().findViewById(R.id.from_airport_code);
        fromAirportCode.setTextColor(Color.WHITE);
        arrows = (ImageView) getView().findViewById(R.id.arrows);
        arrows.setImageResource(R.drawable.arrows_white);
        toAirportCode = (TextView) getView().findViewById(R.id.to_airport_code);
        toAirportCode.setTextColor(Color.WHITE);

        cabinClass = (TextView) getView().findViewById(R.id.cabin_class);
        cabinClass.setTextColor(Color.WHITE);
        cabinClass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.econo_white, 0, 0, 0);
        cabinClass.setText(flightSearch.getCabinClass().getNameResId());

        guestQuantity = (TextView) getView().findViewById(R.id.passengers_quantity);
        guestQuantity.setTextColor(Color.WHITE);
        guestQuantity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.user_white_small, 0, 0, 0);
        guestQuantity.setText(String.valueOf(flightSearch.getPassengersQuantity()));

        dateSearch = (TextView) getView().findViewById(R.id.dateFilter);
        dateSearch.setTextColor(Color.WHITE);
        dateSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.calendar_white, 0, 0, 0);
        dateSearch.setText(DateFormater.formatDateToShowInShortMonthAndDate(flightSearch.getDepartDate()) + " - " +
                DateFormater.formatDateToShowInShortMonthAndDate(flightSearch.getReturnDate()));

        flightPrice = (TextView) getView().findViewById(R.id.hotel_price);
        flightPrice.setText("$" + flight.getPrice() / 100);
    }
}
