package com.mastercard.travel.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anypresence.rails_droid.IAPFutureCallback;
import com.anypresence.sdk.master_travel_ecomm.models.AirportCode;
import com.anypresence.sdk.master_travel_ecomm.models.Hotel;
import com.mastercard.travel.R;
import com.mastercard.travel.activity.HotelDetailActivity;
import com.mastercard.travel.activity.HotelFilterActivity;
import com.mastercard.travel.activity.HotelListActivity;
import com.mastercard.travel.activity.HotelSortActivity;
import com.mastercard.travel.adapter.HotelAdapter;
import com.mastercard.travel.model.HotelFilter;
import com.mastercard.travel.model.HotelSearch;
import com.mastercard.travel.model.HotelSort;
import com.mastercard.travel.model.PriceFilter;
import com.mastercard.travel.model.StarsFilter;
import com.mastercard.travel.view.EmptyLoadingView;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by emi91_000 on 05/02/2015.
 */
public class HotelListFragment extends BaseFragment {
    //Params
    private final static String CITY_NAME_PARAM = "city";
    private final static String PRICE_INDICATOR_PARAM = "price_indicator";
    private final static String STAR_RATING_PARAM = "star_rating";
    private final static String PROPERTY_NAME_PARAM = "name";
    private final static String PROPERTY_TYPE_PARAM = "property_type";
    private static final int REQUEST_SORT = 5;
    private static final int REQUEST_FILTER = 6;
    private RecyclerView recyclerView;
    private HotelAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EmptyLoadingView emptyLoadingView;
    private HotelFilter filter = new HotelFilter(PriceFilter.ANY, StarsFilter.ANY);
    private HotelSort hotelSort = HotelSort.PRICE;
    private HotelSearch hotelSearch;
    private List<Hotel> hotels;
    private TextView emptyListLabel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hotel_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hotelSearch = (HotelSearch) getArguments().getSerializable(HotelFindFragment.HOTEL_SEARCH_EXTRA);
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        emptyListLabel = (TextView) view.findViewById(R.id.emptyListLabel);
        setHasOptionsMenu(true);
        recyclerView.setHasFixedSize(true);
        emptyLoadingView = (EmptyLoadingView) getView().findViewById(R.id.emptyLoadingView);
        if (isComingFromMap()) {
            this.hotels = (List<Hotel>) getArguments().getSerializable(HotelDetailActivity.HOTEL_EXTRA);
            this.filter = (HotelFilter) getArguments().getSerializable(HotelDetailActivity.HOTEL_FILTER_EXTRA);
            refreshHotelsInAdapter();
            emptyLoadingView.setVisibility(View.GONE);
        } else {
            searchHotelsInBackground();
        }
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private boolean isComingFromMap() {
        return getArguments().getSerializable(HotelDetailActivity.HOTEL_EXTRA) != null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        Bundle bundle = null;
        switch (item.getItemId()) {
            case R.id.ab_filter:
                intent = new Intent(getActivity(), HotelFilterActivity.class);
                bundle = new Bundle();
                bundle.putSerializable(HotelFilterFragment.HOTEL_FILTER_EXTRA, filter);
                intent.putExtras(bundle);
                getActivity().startActivityForResult(intent, REQUEST_FILTER);
                break;
            case R.id.ab_sort:
                intent = new Intent(getActivity(), HotelSortActivity.class);
                bundle = new Bundle();
                bundle.putSerializable(HotelSortFragment.HOTEL_SORT_EXTRA, hotelSort);
                intent.putExtras(bundle);
                getActivity().startActivityForResult(intent, REQUEST_SORT);
                break;
            case R.id.ab_map:
                ((HotelListActivity) getActivity()).showMap(hotelSearch, hotels, filter);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_SORT) {
                if (sortChange(data)) {
                    hotelSort = (HotelSort) data.getSerializableExtra(HotelSortFragment.HOTEL_SORT_EXTRA);
                    sortHotels();
                }

            } else if (requestCode == REQUEST_FILTER) {
                filter = (HotelFilter) data.getSerializableExtra(HotelFilterFragment.HOTEL_FILTER_EXTRA);
                searchHotelsInBackground();
            }
        }
    }

    private boolean sortChange(Intent data) {
        return (HotelSort) data.getSerializableExtra(HotelSortFragment.HOTEL_SORT_EXTRA) != hotelSort;
    }

    private void sortHotels() {
        Collections.sort(hotels, hotelSort.getComparator());
        refreshHotelsInAdapter();
    }

    private Set<String> getPropertyTypes(List<Hotel> hotels) {
        Set<String> propertyTypes = new HashSet<>();
        for (Hotel hotel : hotels) {
            propertyTypes.add(hotel.getPropertyType());
        }
        return propertyTypes;
    }

    public void searchHotelsInBackground() {
        emptyLoadingView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        emptyListLabel.setVisibility(View.GONE);
        Map<String, String> params = new HashMap<String, String>();
        params.put(CITY_NAME_PARAM, hotelSearch.getCityName());

        configureParams(params);

        Hotel.queryInBackground(AirportCode.Scopes.EXACT_MATCH, params, new IAPFutureCallback<List<Hotel>>() {
            @Override
            public void finished(List<Hotel> hotels, Throwable throwable) {
                Log.e(LOG_TAG, throwable.toString());
            }

            @Override
            public void onSuccess(final List<Hotel> hotels) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        HotelListFragment.this.hotels = hotels;
                        sortHotels();
                        emptyLoadingView.setVisibility(View.GONE);
                        if (hotels != null && !hotels.isEmpty()) {
                            recyclerView.setVisibility(View.VISIBLE);
                            emptyListLabel.setVisibility(View.GONE);
                            ((TextView) getToolbar().findViewById(R.id.subtitle)).setText(getActivity().getString(R.string.hotel_found, hotels.size()));
                        } else {
                            ((TextView) getToolbar().findViewById(R.id.subtitle)).setText(R.string.no_hotel_found);
                            emptyListLabel.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(LOG_TAG, throwable.toString());
            }
        });
    }

    private void configureParams(Map<String, String> params) {
        if (filter.getStarFilter() != StarsFilter.ANY) {
            params.put(STAR_RATING_PARAM, Integer.toString(filter.getStarFilter().ordinal()));
        }
        if (filter.getPriceFilter() != PriceFilter.ANY) {
            params.put(PRICE_INDICATOR_PARAM, Integer.toString(filter.getPriceFilter().ordinal()));
        }
        if (filter.getPropertyNameFilter() != null && !filter.getPropertyNameFilter().equals("")) {
            params.put(PROPERTY_NAME_PARAM, filter.getPropertyNameFilter());
        }
    }

    public void refreshHotelsInAdapter() {
        adapter = new HotelAdapter(hotels,getActivity());
        filter.setPropertyTypes(getPropertyTypes(hotels));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new HotelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Hotel hotel) {
                HotelDetailActivity.startActivity(getActivity(), hotel, hotelSearch);
            }
        });
    }
}
