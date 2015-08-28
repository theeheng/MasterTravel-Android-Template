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
import com.mastercard.travel.model.FlightSort;

/**
 * Created by emi91_000 on 05/02/2015.
 */
public class FlightSortFragment extends BaseFragment {
    public static final String FLIGHT_SORT_EXTRA = "flightSortExtra";

    private TextView price;
    private TextView departureTakeOffTime;
    private TextView departureLandingTime;
    private TextView returnTakeOffTime;
    private TextView returnLandingTime;
    private TextView airline;
    private TextView duration;
    private FlightSort flightSort;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flight_sort, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initControls(view);
        setListeners();
        if (getArguments().getSerializable(FLIGHT_SORT_EXTRA) != null) {
            flightSort = (FlightSort) getArguments().getSerializable(FLIGHT_SORT_EXTRA);
            switch (flightSort) {
                case PRICE:
                    setSelected(price);
                    break;
                case DEPARTURE_LANDING_TIME:
                    setSelected(departureLandingTime);
                    break;
                case DEPARTURE_TAKE_OFF_TIME:
                    setSelected(departureTakeOffTime);
                    break;
                case RETURN_TAKE_OFF_TIME:
                    setSelected(returnTakeOffTime);
                    break;
                case RETURN_LANDING_TIME:
                    setSelected(returnLandingTime);
                    break;
                case AIRLINE:
                    setSelected(airline);
                    break;
                case DURATION:
                    setSelected(duration);
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
        price.setOnClickListener(listener);
        departureTakeOffTime.setOnClickListener(listener);
        departureLandingTime.setOnClickListener(listener);
        returnTakeOffTime.setOnClickListener(listener);
        returnLandingTime.setOnClickListener(listener);
        airline.setOnClickListener(listener);
        duration.setOnClickListener(listener);
    }

    private void initControls(View view) {
        price = (TextView) view.findViewById(R.id.price);
        departureTakeOffTime = (TextView) view.findViewById(R.id.departure_take_off_time);
        departureLandingTime = (TextView) view.findViewById(R.id.departure_landing_time);
        returnTakeOffTime = (TextView) view.findViewById(R.id.return_take_off_time);
        returnLandingTime = (TextView) view.findViewById(R.id.return_landing_time);
        airline = (TextView) view.findViewById(R.id.sort_airline);
        duration = (TextView) view.findViewById(R.id.sort_duration);
    }

    public void setSelected(View view) {
        if (price == view) flightSort = FlightSort.PRICE;
        if (departureTakeOffTime == view) flightSort = FlightSort.DEPARTURE_TAKE_OFF_TIME;
        if (departureLandingTime == view) flightSort = FlightSort.DEPARTURE_LANDING_TIME;
        if (returnTakeOffTime == view) flightSort = FlightSort.RETURN_TAKE_OFF_TIME;
        if (returnLandingTime == view) flightSort = FlightSort.RETURN_LANDING_TIME;
        if (airline == view) flightSort = FlightSort.AIRLINE;
        if (duration == view) flightSort = FlightSort.DURATION;


        price.setCompoundDrawablesWithIntrinsicBounds(0, 0, view == price ? R.drawable.check : 0, 0);
        departureTakeOffTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, view == departureTakeOffTime ? R.drawable.check : 0, 0);
        departureLandingTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, view == departureLandingTime ? R.drawable.check : 0, 0);
        returnTakeOffTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, returnTakeOffTime == view ? R.drawable.check : 0, 0);
        returnLandingTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, returnLandingTime == view ? R.drawable.check : 0, 0);
        airline.setCompoundDrawablesWithIntrinsicBounds(0, 0, airline == view ? R.drawable.check : 0, 0);
        duration.setCompoundDrawablesWithIntrinsicBounds(0, 0, duration == view ? R.drawable.check : 0, 0);

    }

    public void markAndSort(View view) {
        setSelected(view);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FLIGHT_SORT_EXTRA, flightSort);
        intent.putExtras(bundle);
        getActivity().setIntent(intent);
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

}
