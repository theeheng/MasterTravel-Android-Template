package com.mastercard.travel.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;

import com.anypresence.rails_droid.IAPFutureCallback;
import com.anypresence.sdk.master_travel_ecomm.models.City;
import com.mastercard.travel.R;
import com.mastercard.travel.activity.FlightFindActivity;
import com.mastercard.travel.activity.HotelFindActivity;
import com.mastercard.travel.adapter.CityAdapter;
import com.mastercard.travel.view.EmptyLoadingView;

import java.util.List;

/**
 * Created by emi91_000 on 05/02/2015.
 */
public class HomeFragment extends BaseFragment {
    private ListView list;
    private CityAdapter adapter;
    private EmptyLoadingView emptyLoadingView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, null, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = (ListView) view.findViewById(R.id.list);
        emptyLoadingView = (EmptyLoadingView) getView().findViewById(R.id.emptyLoadingView);
        list.setEmptyView(emptyLoadingView);
        view.findViewById(R.id.goToFindFlight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), FlightFindActivity.class));
            }
        });

        view.findViewById(R.id.goToFindeHotel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), HotelFindActivity.class));
            }
        });

        City.queryInBackground(City.Scopes.ALL, new IAPFutureCallback<List<City>>() {
            @Override
            public void finished(List<City> cities, final Throwable throwable) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(LOG_TAG, throwable.toString());
                        emptyLoadingView.setErrorView();
                    }
                });
            }

            @Override
            public void onSuccess(final List<City> cities) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new CityAdapter(getActivity(), cities);

                        LayoutAnimationController controller
                                = AnimationUtils.loadLayoutAnimation(
                                getActivity(), R.anim.layout_controller_animation);
                        list.setLayoutAnimation(controller);
                        list.setAdapter(adapter);
                        emptyLoadingView.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onFailure(final Throwable throwable) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(LOG_TAG, throwable.toString());
//                        emptyLoadingView.setErrorView();
                    }
                });

            }
        });
    }

}
