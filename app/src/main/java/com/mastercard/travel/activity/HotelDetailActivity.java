package com.mastercard.travel.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.anypresence.sdk.master_travel_ecomm.models.Hotel;
import com.mastercard.travel.R;
import com.mastercard.travel.fragment.BaseFragment;
import com.mastercard.travel.fragment.HotelDetailFragment;
import com.mastercard.travel.model.HotelSearch;

public class HotelDetailActivity extends BaseActivityWithNavigationDrawerAndBack {
    public final static String HOTEL_EXTRA = "hotelExtra";
    public final static String HOTEL_FILTER_EXTRA = "hotelFilter";
    public final static String HOTEL_SEARCH_EXTRA = "hotelSearch";

    public static void startActivity(Context context, Hotel hotel, HotelSearch hotelSearch) {
        Intent intent = new Intent(context, HotelDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(HOTEL_EXTRA, hotel);
        bundle.putSerializable(HOTEL_SEARCH_EXTRA, hotelSearch);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public Class<? extends BaseFragment> getFragmentClass() {
        return HotelDetailFragment.class;
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.detail;
    }

}
