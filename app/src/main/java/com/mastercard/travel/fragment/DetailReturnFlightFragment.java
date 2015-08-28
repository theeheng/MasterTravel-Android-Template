package com.mastercard.travel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.anypresence.sdk.master_travel_ecomm.models.Flight;
import com.mastercard.travel.R;
import com.mastercard.travel.activity.FlightDetailActivity;
import com.mastercard.travel.model.FlightSearch;
import com.mastercard.travel.util.ImageUtil;

/**
 * Created by ignacio on 25/02/2015.
 */
public class DetailReturnFlightFragment extends Fragment {

    private Flight flight;
    private FlightSearch flightSearch;

    private TextView departFromHour;
    private TextView departFromAirport;
    private TextView departToHour;
    private TextView departToAirport;

    private TextView scaleFromHour;
    private TextView scaleFromAirport;
    private TextView scaleToAirport;
    private TextView scaleDuration;
    private TextView scaleAirline;

    private TextView durationAndScales;
    private TextView airline;

    private NetworkImageView departureThumbnail;
    private NetworkImageView inBoundThumbnail;


    public static DetailReturnFlightFragment newInstance(Flight flight, FlightSearch flightSearch) {
        DetailReturnFlightFragment frag = new DetailReturnFlightFragment();

        Bundle bun = new Bundle();
        bun.putSerializable(FlightDetailActivity.FLIGHT_EXTRA, flight);
        bun.putSerializable(FlightDetailActivity.FLIGHT_SEARCH_EXTRA, flightSearch);

        frag.setArguments(bun);

        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        flight = (Flight) getArguments().getSerializable(FlightDetailActivity.FLIGHT_EXTRA);
        flightSearch = (FlightSearch) getArguments().getSerializable(FlightDetailActivity.FLIGHT_SEARCH_EXTRA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragement_depart_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initControls();
    }

    private void initControls() {

        departureThumbnail= (NetworkImageView) getView().findViewById(R.id.airline_thumnail);
        ImageUtil.setImageUrl(departureThumbnail, flight.getReturnAirlineThumbnailImageUrl(), getActivity());
        inBoundThumbnail= (NetworkImageView) getView().findViewById(R.id.departure_airline_thumnail);
        ImageUtil.setImageUrl(inBoundThumbnail,flight.getOutboundAirlineThumbnailImageUrl(),getActivity());


        departFromHour = (TextView) getView().findViewById(R.id.depart_from_hour);
        departFromHour.setText(flight.getReturnDepartsAtDisplayStr());

        departFromAirport = (TextView) getView().findViewById(R.id.depart_from_airport);
        departFromAirport.setText(flightSearch.getToAirport());

        departToHour = (TextView) getView().findViewById(R.id.depart_to_hour);
        departToHour.setText(flight.getReturnArrivesAtDisplayStr());

        departToAirport = (TextView) getView().findViewById(R.id.depart_to_airport);
        departToAirport.setText(flightSearch.getFromAirport());

        durationAndScales = (TextView) getView().findViewById(R.id.duration_and_scales);
        durationAndScales.setText(flight.getReturnDurationHours() + "h " +
                flight.getReturnDurationMinutes() + "m | " +
                flight.getOutboundNumStops() + " stop | ");

        airline = (TextView) getView().findViewById(R.id.airline_name);
        airline.setText(flight.getReturnAirline());

        scaleFromHour = (TextView) getView().findViewById(R.id.scale_from_hour);
        scaleFromHour.setText(flight.getReturnDepartsAtDisplayStr());

        scaleFromAirport = (TextView) getView().findViewById(R.id.scale_from_airport);
        scaleFromAirport.setText(flightSearch.getToAirport());

        scaleToAirport = (TextView) getView().findViewById(R.id.scale_to_airport);
        scaleToAirport.setText(flightSearch.getFromAirport());

        scaleDuration = (TextView) getView().findViewById(R.id.duration);
        scaleDuration.setText(flight.getReturnDurationHours() + "h " +
                flight.getReturnDurationMinutes() + "m");

        scaleAirline = (TextView) getView().findViewById(R.id.scale_airline_name);
        scaleAirline.setText(flight.getReturnAirline() + " " + flight.getReturnFlightNumber());

    }
}
