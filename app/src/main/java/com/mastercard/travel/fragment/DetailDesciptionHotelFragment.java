package com.mastercard.travel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anypresence.sdk.master_travel_ecomm.models.Hotel;
import com.mastercard.travel.R;
import com.mastercard.travel.activity.HotelDetailActivity;
import com.mastercard.travel.view.TwoLinesTextView;

/**
 * Created by emi91_000 on 14/02/2015.
 */
public class DetailDesciptionHotelFragment extends Fragment {

    private TwoLinesTextView addressView;
    private TwoLinesTextView amenitiesView;

    private Hotel hotel;

    public static DetailDesciptionHotelFragment newInstance(Hotel hotel) {
        DetailDesciptionHotelFragment frag = new DetailDesciptionHotelFragment();

        Bundle bun = new Bundle();
        bun.putSerializable(HotelDetailActivity.HOTEL_EXTRA, hotel);

        frag.setArguments(bun);

        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hotel = (Hotel) getArguments().getSerializable(HotelDetailActivity.HOTEL_EXTRA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hotel_detail_description, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initControls();
    }

    private void initControls() {

        addressView = (TwoLinesTextView) getView().findViewById(R.id.hotel_address);
        addressView.setSubtitle(hotel.getAddress());

        amenitiesView = (TwoLinesTextView) getView().findViewById(R.id.hotel_amenities);

        StringBuilder amenities = new StringBuilder();

        for (Object amenitie : hotel.getAmenities()) {
            amenities.append(amenitie + "\n");
        }

        amenitiesView.setSubtitle(amenities.toString());
    }
}
