package com.mastercard.travel.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.TextView;

import com.anypresence.rails_droid.IAPFutureCallback;
import com.anypresence.sdk.master_travel_ecomm.models.AirportCode;
import com.mastercard.travel.R;
import com.mastercard.travel.view.EmptyLoadingView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ignacio on 16/02/2015.
 */
public class FlightFindLocationFragment extends BaseFragment implements Filterable {

    private List<AirportCode> cities;

    private List<AirportCode> filterList;

    private SearchView searchView;

    private RecyclerView locationList;

    private Button done;

    private Filter cityFilter;

    private EmptyLoadingView emptyLoadingView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flight_find_location, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initControls();

        setListeners();

        AirportCode.queryInBackground(AirportCode.Scopes.ALL, new IAPFutureCallback<List<AirportCode>>() {
            @Override
            public void finished(List<AirportCode> airportCodes, Throwable throwable) {
                Log.e(LOG_TAG, throwable.toString());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        emptyLoadingView.setErrorView();
                    }
                });
            }

            @Override
            public void onSuccess(List<AirportCode> airportCodes) {
                FlightFindLocationFragment.this.cities = airportCodes;
                filterList = cities;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        emptyLoadingView.setVisibility(View.GONE);
                        locationList.setHasFixedSize(true);
                        locationList.setAdapter(new CitiesAdapter());

                    }
                });
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(LOG_TAG, throwable.toString());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        emptyLoadingView.setErrorView();
                    }
                });
            }
        });
    }

    private void initControls() {
        emptyLoadingView = (EmptyLoadingView) getView().findViewById(R.id.emptyLoadingView);
        locationList = (RecyclerView) getView().findViewById(R.id.location_list);
        locationList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        searchView = (SearchView) getView().findViewById(R.id.search);

        int searchPlateId = getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchPlate = (EditText) searchView.findViewById(searchPlateId);
        searchPlate.setTextColor(Color.BLACK);

        done = (Button) getView().findViewById(R.id.done_location);
    }

    private void setListeners() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                getFilter().filter(newText);
                return true;
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("CityName", searchView.getQuery().toString());
                intent.putExtras(bundle);
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
            }
        });
    }

    public void onCitySelected(String cityName) {
        searchView.setQuery(cityName, true);
        searchView.setIconified(false);
    }

    @Override
    public Filter getFilter() {
        if (cityFilter == null)
            cityFilter = new CityFilter();

        return cityFilter;
    }

    private class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CityHolder> {

        @Override
        public CitiesAdapter.CityHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new CityHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_city, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(CitiesAdapter.CityHolder viewHolder, int i) {
            viewHolder.cityName.setText(filterList.get(i).getAirportName() + " (" + filterList.get(i).getCode() + ")");
        }

        @Override
        public int getItemCount() {
            return filterList.size();
        }

        public class CityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView cityName;

            public CityHolder(View itemView) {
                super(itemView);

                cityName = (TextView) itemView.findViewById(R.id.city_name);
                cityName.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

                onCitySelected(cityName.getText().toString());
            }
        }
    }

    private class CityFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = cities;
                results.count = cities.size();
            } else {
                List<AirportCode> nPlanetList = new ArrayList<AirportCode>();

                for (AirportCode p : cities) {
                    if (p.getAirportName().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        nPlanetList.add(p);
                }

                results.values = nPlanetList;
                results.count = nPlanetList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if (results.count == 0)
                locationList.getAdapter().notifyDataSetChanged();
            else {
                filterList = (List<AirportCode>) results.values;
                locationList.getAdapter().notifyDataSetChanged();
            }

        }

    }
}
