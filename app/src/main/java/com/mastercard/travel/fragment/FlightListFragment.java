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
import com.anypresence.sdk.master_travel_ecomm.models.Flight;
import com.mastercard.travel.R;
import com.mastercard.travel.activity.FlightDetailActivity;
import com.mastercard.travel.activity.FlightFilterActivity;
import com.mastercard.travel.activity.FlightSortActivity;
import com.mastercard.travel.adapter.FlightAdapter;
import com.mastercard.travel.model.FlightFilter;
import com.mastercard.travel.model.FlightSearch;
import com.mastercard.travel.model.FlightSort;
import com.mastercard.travel.model.PriceFilter;
import com.mastercard.travel.model.StarsFilter;
import com.mastercard.travel.view.EmptyLoadingView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by emi91_000 on 05/02/2015.
 */
public class FlightListFragment extends BaseFragment {
    //Params
    private final static String CITY_NAME_PARAM = "city";
    private final static String PRICE_INDICATOR_PARAM = "price_indicator";
    private final static String STAR_RATING_PARAM = "star_rating";
    private final static String PROPERTY_NAME_PARAM = "name";
    private static final int REQUEST_SORT = 5;
    private static final int REQUEST_FILTER = 6;
    private RecyclerView recyclerView;
    private View listHeader;
    private FlightAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EmptyLoadingView emptyLoadingView;
    private FlightFilter filter = new FlightFilter(PriceFilter.ANY, StarsFilter.ANY);
    private FlightSort flightSort = FlightSort.PRICE;
    private FlightSearch flightSearch;
    private List<Flight> flights;
    private TextView emptyListLabel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flight_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        flightSearch = (FlightSearch) getArguments().getSerializable(FlightDetailActivity.FLIGHT_SEARCH_EXTRA);
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        emptyListLabel = (TextView) view.findViewById(R.id.emptyListLabel);
        setHasOptionsMenu(true);
        listHeader = view.findViewById(R.id.list_header);
        recyclerView.setHasFixedSize(true);
        emptyLoadingView = (EmptyLoadingView) getView().findViewById(R.id.emptyLoadingView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        searchFlightsInBackground();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.list_menu, menu);
        menu.removeItem(R.id.ab_map);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        Bundle bundle = null;
        switch (item.getItemId()) {
            case R.id.ab_filter:
                intent = new Intent(getActivity(), FlightFilterActivity.class);
                bundle = new Bundle();
                bundle.putSerializable(FlightFilterFragment.FLIGHT_FILTER_EXTRA, filter);
                intent.putExtras(bundle);
                getActivity().startActivityForResult(intent, REQUEST_FILTER);
                break;
            case R.id.ab_sort:
                intent = new Intent(getActivity(), FlightSortActivity.class);
                bundle = new Bundle();
                bundle.putSerializable(FlightSortFragment.FLIGHT_SORT_EXTRA, flightSort);
                intent.putExtras(bundle);
                getActivity().startActivityForResult(intent, REQUEST_SORT);
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
                    flightSort = (FlightSort) data.getSerializableExtra(FlightSortFragment.FLIGHT_SORT_EXTRA);
                    sortFlights();
                }

            } else if (requestCode == REQUEST_FILTER) {
                filter = (FlightFilter) data.getSerializableExtra(FlightFilterFragment.FLIGHT_FILTER_EXTRA);
                searchFlightsInBackground();
            }
        }
    }

    private boolean sortChange(Intent data) {
        return (FlightSort) data.getSerializableExtra(FlightSortFragment.FLIGHT_SORT_EXTRA) != flightSort;
    }

    private void sortFlights() {
        //TODO SORTS
//        Collections.sort(flights,flightSort.getComparator());
        refreshFlightsInAdapter();
    }

    private Set<String> getAirlines(List<Flight> flights) {
        Set<String> airlines = new HashSet<>();
        for (Flight flight : flights) {
            airlines.add(flight.getOutboundAirline());
            airlines.add(flight.getReturnAirline());
        }
        return airlines;
    }

    public void searchFlightsInBackground() {
        emptyLoadingView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        emptyListLabel.setVisibility(View.GONE);
        Map<String, String> params = new HashMap<String, String>();
        configureParams(params);

        Flight.queryInBackground(AirportCode.Scopes.EXACT_MATCH, params, new IAPFutureCallback<List<Flight>>() {
            @Override
            public void finished(List<Flight> flights, Throwable throwable) {
                Log.e(LOG_TAG, throwable.toString());
            }

            @Override
            public void onSuccess(final List<Flight> flights) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        FlightListFragment.this.flights = flights;
                        sortFlights();
                        emptyLoadingView.setVisibility(View.GONE);
                        if (flights != null && !flights.isEmpty()) {
                            recyclerView.setVisibility(View.VISIBLE);
                            listHeader.setVisibility(View.VISIBLE);
                            emptyListLabel.setVisibility(View.GONE);
                            ((TextView) getToolbar().findViewById(R.id.subtitle)).setText(getActivity().getString(R.string.flight_found, flights.size()));
                        } else {
                            listHeader.setVisibility(View.GONE);
                            ((TextView) getToolbar().findViewById(R.id.subtitle)).setText(R.string.no_flight_found);
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
        if (filter.getSelectedAirline() != null && !filter.getSelectedAirline().isEmpty()) {
            params.put("outbound_airline", filter.getSelectedAirline());
        }
    }

    public void refreshFlightsInAdapter() {
        adapter = new FlightAdapter(flights, flightSearch, getActivity());

        filter.setAirlines(getAirlines(flights));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new FlightAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Flight flight) {
                FlightDetailActivity.startActivity(getActivity(), flight, flightSearch);
            }
        });
    }
}
