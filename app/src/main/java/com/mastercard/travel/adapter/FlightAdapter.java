package com.mastercard.travel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anypresence.sdk.master_travel_ecomm.models.Flight;
import com.mastercard.travel.R;
import com.mastercard.travel.model.FlightSearch;
import com.mastercard.travel.util.DateFormater;
import com.mastercard.travel.util.ImageUtil;

import java.util.List;

/**
 * Created by emi91_000 on 09/02/2015.
 */
public class FlightAdapter extends RecyclerView.Adapter<FlightViewHolder> {
    private List<Flight> mDataset;
    private OnItemClickListener onItemClickListener;
    private FlightSearch flightSearch;
    private Context context;

    public FlightAdapter(List<Flight> myDataset, FlightSearch flightSearch, Context context) {
        mDataset = myDataset;
        this.flightSearch = flightSearch;
        this.context = context;
    }

    @Override
    public FlightViewHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        // create a new view
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_flight, parent, false);
        FlightViewHolder vh = new FlightViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(FlightViewHolder holder, final int position) {
        final Flight flight = mDataset.get(position);
        holder.dateFlightFrom.setText(DateFormater.formatDateToShowInFlightList(flightSearch.getDepartDate()));
        holder.price.setText("$" + ((Integer) (flight.getPrice() / 100)).toString());
        holder.departureFromTime.setText(flight.getOutboundDepartsAtDisplayStr());
        holder.departureToTime.setText(flight.getOutboundArrivesAtDisplayStr());
        holder.departureFromAirportCode.setText(flight.getOriginationAirportCode());
        holder.departureToAirportCode.setText(flight.getDestinationAirportCode());
        holder.departureAirlineName.setText(flight.getOutboundAirline());
        holder.departuredurationAndScales.setText(flight.getOutboundDurationHours() + "h " + flight.getOutboundDurationMinutes() + "m " + "| " + flight.getOutboundNumStops() + " stop");

        holder.dateFlightTo.setText(DateFormater.formatDateToShowInFlightList(flightSearch.getReturnDate()));
        holder.returnFromTime.setText(flight.getReturnDepartsAtDisplayStr());
        holder.returnToTime.setText(flight.getReturnArrivesAtDisplayStr());
        holder.returnFromAirportCode.setText(flight.getDestinationAirportCode());
        holder.returnToAirportCode.setText(flight.getOriginationAirportCode());
        holder.returnAirlineName.setText(flight.getReturnAirline());
        holder.returndurationAndScales.setText(flight.getReturnDurationHours() + "h " + flight.getReturnDurationMinutes() + "m " + "| " + flight.getReturnNumStops() + " stop");

        //TODO Falta imagen real
        ImageUtil.setImageUrl(holder.departAirportThumnail, flight.getOutboundAirlineThumbnailImageUrl(),context);
        ImageUtil.setImageUrl(holder.returnAirportThumnail, flight.getReturnAirlineThumbnailImageUrl(),context);
        holder.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(flight);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(Flight flight);
    }
}