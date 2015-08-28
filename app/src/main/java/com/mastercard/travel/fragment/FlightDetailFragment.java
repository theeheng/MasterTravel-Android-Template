package com.mastercard.travel.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.anypresence.rails_droid.IAPFutureCallback;
import com.anypresence.sdk.master_travel_ecomm.models.Flight;
import com.anypresence.sdk.master_travel_ecomm.models.OrderDetail;
import com.mastercard.travel.R;
import com.mastercard.travel.activity.FlightCheckoutActivity;
import com.mastercard.travel.activity.FlightDetailActivity;
import com.mastercard.travel.adapter.DetailFragmentPagerAdapter;
import com.mastercard.travel.model.FlightSearch;
import com.mastercard.travel.util.DateFormater;
import com.mastercard.travel.util.ImageUtil;
import com.mastercard.travel.view.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emi91_000 on 05/02/2015.
 */
public class FlightDetailFragment extends BaseFragment {

    private Flight flight;
    private FlightSearch flightSearch;

    private NetworkImageView flightImage;
    private TextView fromAirportCode;
    private TextView toAirportCode;

    private TextView passengersQuantity;
    private TextView cabinClass;

    private TextView dateSearch;

    private SlidingTabLayout tabLayout;
    private ViewPager detailsPager;

    private TextView flightPrice;

    private IAPFutureCallback<List<OrderDetail>> addFlightCallback = new IAPFutureCallback<List<OrderDetail>>() {
        @Override
        public void finished(List<OrderDetail> orderDetails, Throwable throwable) {
            FlightDetailFragment.this.dismissProgress();
        }

        @Override
        public void onSuccess(List<OrderDetail> orderDetails) {
            FlightDetailFragment.this.dismissProgress();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Bundle bundle = getArguments();
                    Intent intent = new Intent(getActivity(), FlightCheckoutActivity.class);
                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);
                }
            });
        }

        @Override
        public void onFailure(Throwable throwable) {
            FlightDetailFragment.this.dismissProgress();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flight_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        flight = (Flight) getArguments().getSerializable(FlightDetailActivity.FLIGHT_EXTRA);
        flightSearch = (FlightSearch) getArguments().getSerializable(FlightDetailActivity.FLIGHT_SEARCH_EXTRA);

        initControls();

        getView().findViewById(R.id.book_now).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress(getString(R.string.processing_reservation));
                getMPECommerceManager().addFlightToCart(flight, flightSearch, getString(flightSearch.getCabinClass().getNameResId()), addFlightCallback);
            }
        });
    }

    private void initControls() {

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        flightImage = (NetworkImageView) getView().findViewById(R.id.hotel_image);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                (int) (metrics.heightPixels * 0.3));

        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lp.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.hotel_image_padding);

        flightImage.setLayoutParams(lp);

        ImageUtil.setImageUrl(flightImage, "http://upload.wikimedia.org/wikipedia/commons/3/31/Liverpool_Airport_Interior.jpg",getActivity());

        fromAirportCode = (TextView) getView().findViewById(R.id.from_airport_code);
        fromAirportCode.setText(flight.getOriginationAirportCode());

        toAirportCode = (TextView) getView().findViewById(R.id.to_airport_code);
        toAirportCode.setText(flight.getDestinationAirportCode());

        passengersQuantity = (TextView) getView().findViewById(R.id.passengers_quantity);
        passengersQuantity.setText(String.valueOf(flightSearch.getPassengersQuantity()));

        cabinClass = (TextView) getView().findViewById(R.id.cabin_class);
        cabinClass.setText(flightSearch.getCabinClass().getNameResId());

        dateSearch = (TextView) getView().findViewById(R.id.dateFilter);
        dateSearch.setText(DateFormater.formatDateToShowInMonthAndDate(flightSearch.getDepartDate()) + " - " +
                DateFormater.formatDateToShowInMonthAndDate(flightSearch.getReturnDate()));

        tabLayout = (SlidingTabLayout) getView().findViewById(R.id.tab_layout);
        tabLayout.setDistributeEvenly(true);
        tabLayout.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);

        detailsPager = (ViewPager) getView().findViewById(R.id.pager);

        List<String> titles = new ArrayList<>();

        titles.add("Depart");
        titles.add("Return");

        DetailFragmentPagerAdapter adapter = new DetailFragmentPagerAdapter(getChildFragmentManager(), titles);
        adapter.addFragment(DetailDepartFlightFragment.newInstance(flight, flightSearch));
        adapter.addFragment(DetailReturnFlightFragment.newInstance(flight, flightSearch));

        detailsPager.setAdapter(adapter);

        tabLayout.setViewPager(detailsPager);

        flightPrice = (TextView) getView().findViewById(R.id.flight_price);
        flightPrice.setText("$" + flight.getPrice() / 100);
    }
}
