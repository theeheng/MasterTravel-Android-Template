package com.mastercard.travel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anypresence.sdk.master_travel_ecomm.models.Hotel;
import com.mastercard.travel.R;

import java.util.List;

/**
 * Created by emi91_000 on 09/02/2015.
 */
public class HotelAdapter extends RecyclerView.Adapter<HotelViewHolder> {
    private List<Hotel> mDataset;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public HotelAdapter(List<Hotel> myDataset,Context context) {
        mDataset = myDataset; this.context=context;
    }

    @Override
    public HotelViewHolder onCreateViewHolder(ViewGroup parent,
                                              int viewType) {
        // create a new view
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hotel, parent, false);
        HotelViewHolder vh = new HotelViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(HotelViewHolder holder, final int position) {
        final Hotel hotel = mDataset.get(position);
        HotelFillInformation.fillHotelView(holder, hotel,context);
        holder.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(hotel);
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
        public void onItemClick(Hotel hotel);
    }
}