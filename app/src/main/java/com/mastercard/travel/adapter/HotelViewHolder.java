package com.mastercard.travel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.mastercard.travel.R;

/**
 * Created by Emiliano on 14/02/2015.
 */
public class HotelViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    public TextView price;
    public TextView title;
    public TextView description;
    public TextView distance;
    public NetworkImageView image;
    public View.OnClickListener listener;
    public ViewGroup container;
    public RatingBar starRating;

    public HotelViewHolder(ViewGroup viewGroup) {
        super(viewGroup);
        this.price = (TextView) viewGroup.findViewById(R.id.price);
        this.description = (TextView) viewGroup.findViewById(R.id.detail);
        this.distance = (TextView) viewGroup.findViewById(R.id.distance);
        this.title = (TextView) viewGroup.findViewById(R.id.title);
        this.image = (NetworkImageView) viewGroup.findViewById(R.id.hotel_image);
        container = (ViewGroup) viewGroup.findViewById(R.id.list_item_container);
        this.starRating = (RatingBar) viewGroup.findViewById(R.id.rating_bar);
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
        container.setOnClickListener(listener);
    }
}