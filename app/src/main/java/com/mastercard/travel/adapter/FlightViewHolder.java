package com.mastercard.travel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.mastercard.travel.R;

/**
 * Created by Emiliano on 14/02/2015.
 */
public class FlightViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    public TextView price;
    public TextView dateFlightFrom;
    public TextView departureFromTime;
    public TextView departureFromAirportCode;
    public TextView departureToTime;
    public TextView departureToAirportCode;
    public TextView departuredurationAndScales;
    public NetworkImageView departAirportThumnail;
    public TextView departureAirlineName;

    public TextView dateFlightTo;
    public TextView returnFromTime;
    public TextView returnFromAirportCode;
    public TextView returnToTime;
    public TextView returnToAirportCode;
    public TextView returndurationAndScales;
    public NetworkImageView returnAirportThumnail;
    public TextView returnAirlineName;

    public View.OnClickListener listener;
    public ViewGroup container;

    public FlightViewHolder(ViewGroup viewGroup) {
        super(viewGroup);
        container = viewGroup;
        this.price = (TextView) viewGroup.findViewById(R.id.price);
        this.dateFlightFrom = (TextView) viewGroup.findViewById(R.id.date_flight_from);
        this.departureFromAirportCode = (TextView) viewGroup.findViewById(R.id.airport_code_depart_from);
        this.departureFromTime = (TextView) viewGroup.findViewById(R.id.time_depart_from);
        this.departureToTime = (TextView) viewGroup.findViewById(R.id.time_depart_to);
        this.departureToAirportCode = (TextView) viewGroup.findViewById(R.id.airport_code_depart_to);
        this.departuredurationAndScales = (TextView) viewGroup.findViewById(R.id.departure_duration_and_scales);
        this.departAirportThumnail = (NetworkImageView) viewGroup.findViewById(R.id.departure_airline_thumnail);
        this.departureAirlineName = (TextView) viewGroup.findViewById(R.id.departure_airline_name);

        this.dateFlightTo = (TextView) viewGroup.findViewById(R.id.date_flight_to);
        this.returnFromTime = (TextView) viewGroup.findViewById(R.id.time_return_from);
        this.returnFromAirportCode = (TextView) viewGroup.findViewById(R.id.airport_code_return_from);
        this.returnToTime = (TextView) viewGroup.findViewById(R.id.time_return_to);
        this.returnToAirportCode = (TextView) viewGroup.findViewById(R.id.airport_code_return_to);
        this.returndurationAndScales = (TextView) viewGroup.findViewById(R.id.return_duration_and_scales);
        this.returnAirportThumnail = (NetworkImageView) viewGroup.findViewById(R.id.return_airline_thumbnail);
        this.returnAirlineName = (TextView) viewGroup.findViewById(R.id.return_airline_name);

    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
        container.setOnClickListener(listener);
    }
}