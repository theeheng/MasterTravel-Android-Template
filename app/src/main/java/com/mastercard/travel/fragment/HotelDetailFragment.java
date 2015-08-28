package com.mastercard.travel.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.anypresence.rails_droid.IAPFutureCallback;
import com.anypresence.sdk.master_travel_ecomm.models.Hotel;
import com.anypresence.sdk.master_travel_ecomm.models.OrderDetail;
import com.mastercard.travel.R;
import com.mastercard.travel.activity.HotelCheckoutActivity;
import com.mastercard.travel.activity.HotelDetailActivity;
import com.mastercard.travel.adapter.DetailFragmentPagerAdapter;
import com.mastercard.travel.model.HotelSearch;
import com.mastercard.travel.util.DateFormater;
import com.mastercard.travel.util.ImageUtil;
import com.mastercard.travel.view.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emi91_000 on 05/02/2015.
 */
public class HotelDetailFragment extends BaseFragment {

    private Hotel hotel;
    private HotelSearch hotelSearch;

    private NetworkImageView hotelImage;
    private TextView hotelName;
    private RatingBar hotelRating;

    private TextView guestQuantity;
    private TextView roomsQuantity;

    private TextView dateSearch;

    private SlidingTabLayout tabLayout;
    private ViewPager detailsPager;

    private TextView hotelPrice;
    private IAPFutureCallback<List<OrderDetail>> addHotelCallback = new IAPFutureCallback<List<OrderDetail>>() {
        @Override
        public void finished(List<OrderDetail> orderDetails, Throwable throwable) {
            HotelDetailFragment.this.dismissProgress();
        }

        @Override
        public void onSuccess(List<OrderDetail> orderDetails) {
            HotelDetailFragment.this.dismissProgress();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Bundle bundle = getArguments();
                    Intent intent = new Intent(getActivity(), HotelCheckoutActivity.class);
                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);
                }
            });
        }

        @Override
        public void onFailure(Throwable throwable) {
            HotelDetailFragment.this.dismissProgress();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hotel_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hotel = (Hotel) getArguments().getSerializable(HotelDetailActivity.HOTEL_EXTRA);
        hotelSearch = (HotelSearch) getArguments().getSerializable(HotelDetailActivity.HOTEL_SEARCH_EXTRA);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initControls();

        getView().findViewById(R.id.book_now).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress(getString(R.string.processing_reservation));
                getMPECommerceManager().addHotelToCart(hotel, hotelSearch, addHotelCallback);
            }
        });
    }

    private void initControls() {

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        hotelImage = (NetworkImageView) getView().findViewById(R.id.hotel_image);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                (int) (metrics.heightPixels * 0.3));

        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lp.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.hotel_image_padding);

        hotelImage.setLayoutParams(lp);

        ImageUtil.setImageUrl(hotelImage, "http://media.staticontent.com/media/pictures/55b39863-5577-49da-b8b2-7b913d8d33d0/800x600",getActivity());

        hotelName = (TextView) getView().findViewById(R.id.hotel_name);
        hotelName.setText(hotel.getName());

        hotelRating = (RatingBar) getView().findViewById(R.id.hotel_rating);
        hotelRating.setEnabled(false);
        hotelRating.setProgress(hotel.getStarRating());

        roomsQuantity = (TextView) getView().findViewById(R.id.room_or_cabin_class_quantity);
        roomsQuantity.setText(String.valueOf(hotelSearch.getRoomQuantity()));

        guestQuantity = (TextView) getView().findViewById(R.id.users_quantity);
        guestQuantity.setText(String.valueOf(hotelSearch.getGuestQuantity()));

        dateSearch = (TextView) getView().findViewById(R.id.dateFilter);
        dateSearch.setText(DateFormater.formatDateToShowInMonthAndDate(hotelSearch.getCheckInDate()) + " - " +
                DateFormater.formatDateToShowInMonthAndDate(hotelSearch.getCheckoutDate()));

        tabLayout = (SlidingTabLayout) getView().findViewById(R.id.tab_layout);
        tabLayout.setDistributeEvenly(true);
        tabLayout.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);

        detailsPager = (ViewPager) getView().findViewById(R.id.pager);

        List<String> titles = new ArrayList<>();

        titles.add("Description");
        titles.add(hotel.getNumberOfReviews() + " Reviews");

        DetailFragmentPagerAdapter adapter = new DetailFragmentPagerAdapter(getChildFragmentManager(), titles);
        adapter.addFragment(DetailDesciptionHotelFragment.newInstance(hotel));
        adapter.addFragment(DetailReviewHotelFragment.newInstance(hotel));

        detailsPager.setAdapter(adapter);

        tabLayout.setViewPager(detailsPager);

        hotelPrice = (TextView) getView().findViewById(R.id.hotel_price);
        hotelPrice.setText("$" + hotel.getPrice() / 100);
    }
}
