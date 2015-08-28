package com.mastercard.travel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anypresence.sdk.master_travel_ecomm.models.Hotel;
import com.mastercard.travel.R;
import com.mastercard.travel.activity.HotelDetailActivity;
import com.mastercard.travel.activity.HotelListActivity;
import com.mastercard.travel.adapter.HotelFillInformation;
import com.mastercard.travel.adapter.HotelViewHolder;
import com.mastercard.travel.model.HotelSearch;

/**
 * Created by Emiliano on 14/02/2015.
 */
public class HotelMapSliderItemFragment extends BaseFragment {
    private Hotel hotel;
    private HotelSearch hotelSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.hotel_slider_item, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hotel = (Hotel) getArguments().getSerializable(HotelDetailActivity.HOTEL_EXTRA);
        hotelSearch = (HotelSearch) getArguments().getSerializable(HotelFindFragment.HOTEL_SEARCH_EXTRA);
        ViewGroup hotelItem = (ViewGroup) getView().findViewById(R.id.hotel_item);
        HotelViewHolder hotelViewHolder = new HotelViewHolder(hotelItem);
        HotelFillInformation.fillHotelView(hotelViewHolder, hotel,getActivity());
        hotelItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HotelDetailActivity.startActivity(getActivity(), hotel, hotelSearch);
            }
        });

        getView().findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HotelListActivity) getActivity()).hideSlider();
            }
        });
    }


}

