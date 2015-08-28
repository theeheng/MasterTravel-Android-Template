package com.mastercard.travel.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.anypresence.sdk.master_travel_ecomm.models.Hotel;
import com.mastercard.travel.R;
import com.mastercard.travel.activity.HotelDetailActivity;
import com.mastercard.travel.activity.HotelListActivity;
import com.mastercard.travel.model.HotelFilter;
import com.mastercard.travel.model.HotelSearch;
import com.mastercard.travel.model.PriceFilter;
import com.mastercard.travel.model.StarsFilter;
import com.mastercard.travel.view.WrapContentHeightViewPager;

import java.util.List;


/**
 * Created by emi91_000 on 05/02/2015.
 */
public class HotelMapFragment extends BaseFragment {

    private HotelFilter filter = new HotelFilter(PriceFilter.ANY, StarsFilter.ANY);
    private HotelSearch hotelSearch;
    private List<Hotel> hotels;
    private WrapContentHeightViewPager viewPager;
    private FragmentPagerAdapter adapter;
    private boolean isSliderVisible;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hotel_map, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().findViewById(R.id.map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hotels != null && !isSliderVisible) {
                    viewPager = (WrapContentHeightViewPager) getView().findViewById(R.id.hotel_view_pager);
                    viewPager.setAdapter(new HotelAdapter(getChildFragmentManager(), hotels, hotelSearch, getActivity()));
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.get_in_to_up);
                    viewPager.setVisibility(View.VISIBLE);
                    viewPager.startAnimation(animation);
                    isSliderVisible = true;
                }
            }
        });
        hotels = (List<Hotel>) getArguments().getSerializable(HotelDetailActivity.HOTEL_EXTRA);
        hotelSearch = (HotelSearch) getArguments().getSerializable(HotelFindFragment.HOTEL_SEARCH_EXTRA);
        filter = (HotelFilter) getArguments().getSerializable(HotelDetailActivity.HOTEL_FILTER_EXTRA);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.hotel_map_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ab_list:
                ((HotelListActivity) getActivity()).showList(hotelSearch, hotels, filter);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void hideSlider() {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.get_in_to_down);
        viewPager.startAnimation(animation);
        viewPager.setVisibility(View.GONE);
        isSliderVisible = false;
    }

    public static class HotelAdapter extends FragmentPagerAdapter {
        private List<Hotel> hotels;
        private HotelSearch hotelSearch;
        private Context context;

        public HotelAdapter(FragmentManager fm, List<Hotel> hotels, HotelSearch hotelSearch, Context context) {
            super(fm);
            this.hotels = hotels;
            this.hotelSearch = hotelSearch;
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(HotelDetailActivity.HOTEL_EXTRA, hotels.get(position));
            bundle.putSerializable(HotelDetailActivity.HOTEL_SEARCH_EXTRA, hotelSearch);
            return HotelMapSliderItemFragment.instantiate(context, HotelMapSliderItemFragment.class.getName(), bundle);
        }

        @Override
        public int getCount() {
            return hotels.size();
        }
    }


}
