package com.mastercard.travel.adapter;

import android.content.Context;

import com.anypresence.sdk.master_travel_ecomm.models.Hotel;
import com.mastercard.travel.util.ImageUtil;

/**
 * Created by Emiliano on 14/02/2015.
 */
public class HotelFillInformation {
    public static void fillHotelView(HotelViewHolder holder, Hotel hotel,Context context) {
        holder.price.setText("$" + ((Integer) (hotel.getPrice() / 100)).toString());
        holder.distance.setText(hotel.getDistance().toString() + " " + hotel.getDistanceUnitOfMeasure());
        holder.description.setText(hotel.getAverageRating().toString() + " | " + hotel.getNumberOfReviews() + "reviews");
        holder.title.setText(hotel.getName().toString());
        holder.starRating.setRating(hotel.getStarRating());
        //TODO Falta imagen real
        ImageUtil.setImageUrl(holder.image, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcROslrYvLsfoM9Xlqted5OZQOlD6JwA7oNhhn2I4_axDWF3P_0bjw",context);
    }
}
