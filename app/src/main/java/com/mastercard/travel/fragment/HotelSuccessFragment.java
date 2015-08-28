package com.mastercard.travel.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.anypresence.sdk.master_travel_ecomm.models.Hotel;
import com.mastercard.travel.Application;
import com.mastercard.travel.R;
import com.mastercard.travel.activity.HomeActivity;
import com.mastercard.travel.activity.HotelDetailActivity;
import com.mastercard.travel.model.HotelSearch;
import com.mastercard.travel.util.DateFormater;

/**
 * Created by emi91_000 on 05/02/2015.
 */
public class HotelSuccessFragment extends BaseFragment {

    private Hotel hotel;
    private HotelSearch hotelSearch;

    private TextView hotelName;
    private RatingBar hotelRating;

    private TextView guestQuantity;
    private TextView roomsQuantity;

    private TextView dateSearch;

    private TextView hotelPrice;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hotel_success, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hotel = (Hotel) getArguments().getSerializable(HotelDetailActivity.HOTEL_EXTRA);
        hotelSearch = (HotelSearch) getArguments().getSerializable(HotelDetailActivity.HOTEL_SEARCH_EXTRA);

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

        hotelName = (TextView) getView().findViewById(R.id.hotel_name);
        hotelName.setText(hotel.getName());

        hotelRating = (RatingBar) getView().findViewById(R.id.hotel_rating);
        hotelRating.setEnabled(false);
        hotelRating.setProgress(hotel.getStarRating());

        roomsQuantity = (TextView) getView().findViewById(R.id.room_or_cabin_class_quantity);
        roomsQuantity.setTextColor(Color.WHITE);
        roomsQuantity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bed_white, 0, 0, 0);
        roomsQuantity.setText(String.valueOf(hotelSearch.getRoomQuantity()) + " Room");

        guestQuantity = (TextView) getView().findViewById(R.id.users_quantity);
        guestQuantity.setTextColor(Color.WHITE);
        guestQuantity.setCompoundDrawablesWithIntrinsicBounds(R.drawable.user_white_small, 0, 0, 0);
        guestQuantity.setText(String.valueOf(hotelSearch.getGuestQuantity()));

        dateSearch = (TextView) getView().findViewById(R.id.dateFilter);
        dateSearch.setTextColor(Color.WHITE);
        dateSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.calendar_white, 0, 0, 0);
        dateSearch.setText(DateFormater.formatDateToShowInMonthAndDate(hotelSearch.getCheckInDate()) + " - " +
                DateFormater.formatDateToShowInMonthAndDate(hotelSearch.getCheckoutDate()));

        hotelPrice = (TextView) getView().findViewById(R.id.hotel_price);
        hotelPrice.setText("$" + hotel.getPrice() / 100);
    }
}
