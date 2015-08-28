package com.mastercard.travel.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.anypresence.sdk.master_travel_ecomm.models.Hotel;
import com.mastercard.travel.R;
import com.mastercard.travel.fragment.BaseFragment;
import com.mastercard.travel.fragment.HotelFindFragment;
import com.mastercard.travel.fragment.HotelListFragment;
import com.mastercard.travel.fragment.HotelMapFragment;
import com.mastercard.travel.model.HotelFilter;
import com.mastercard.travel.model.HotelSearch;
import com.mastercard.travel.util.DateFormater;

import java.util.List;

public class HotelListActivity extends BaseActivityWithNavigationDrawerAndBack {

    @Override
    public Class<? extends BaseFragment> getFragmentClass() {
        return HotelListFragment.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleAndSubtitle(R.string.find, 0);
        HotelSearch hotelSearch = (HotelSearch) getIntent().getSerializableExtra(HotelFindFragment.HOTEL_SEARCH_EXTRA);
        ((TextView) findViewById(R.id.dateFilter)).setText(DateFormater.formatDateToShowInMonthAndDate(hotelSearch.getCheckInDate()) + " - " + DateFormater.formatDateToShowInMonthAndDate(hotelSearch.getCheckoutDate()));
        ((TextView) findViewById(R.id.users_quantity)).setText(Integer.toString(hotelSearch.getGuestQuantity()));
        ((TextView) findViewById(R.id.room_or_cabin_class_quantity)).setText(getResources().getQuantityString(R.plurals.rooms, hotelSearch.getRoomQuantity(), hotelSearch.getRoomQuantity()));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_hotel_list;
    }

    public Bundle createBundle(HotelSearch hotelSearch, List<Hotel> hotel, HotelFilter filter) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(HotelDetailActivity.HOTEL_EXTRA, (java.io.Serializable) hotel);
        bundle.putSerializable(HotelDetailActivity.HOTEL_SEARCH_EXTRA, hotelSearch);
        bundle.putSerializable(HotelDetailActivity.HOTEL_FILTER_EXTRA, filter);
        return bundle;
    }

    public void showMap(HotelSearch hotelSearch, List<Hotel> hotel, HotelFilter filter) {
        replaceWithFlipAnimation(HotelMapFragment.class, createBundle(hotelSearch, hotel, filter));
    }

    public void showList(HotelSearch hotelSearch, List<Hotel> hotel, HotelFilter filter) {
        replaceWithFlipAnimation(HotelListFragment.class, createBundle(hotelSearch, hotel, filter));
    }

    public void hideSlider() {
        ((HotelMapFragment) getSupportFragmentManager().findFragmentByTag(HotelMapFragment.class.getName())).hideSlider();
    }
}
