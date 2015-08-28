package com.mastercard.travel.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.RatingBar;
import android.widget.TextView;

import com.anypresence.masterpass_android_library.dto.PairCheckoutResponse;
import com.anypresence.sdk.master_travel_ecomm.models.Hotel;
import com.mastercard.travel.Constants;
import com.mastercard.travel.R;
import com.mastercard.travel.activity.FlightSuccessActivity;
import com.mastercard.travel.activity.HotelDetailActivity;
import com.mastercard.travel.activity.HotelSuccessActivity;
import com.mastercard.travel.model.HotelSearch;
import com.mastercard.travel.util.DateFormater;

/**
 * Created by emi91_000 on 05/02/2015.
 */
public class HotelCheckoutFragment extends BaseFragment {

    private Hotel hotel;
    private HotelSearch hotelSearch;

    private TextView hotelName;
    private RatingBar hotelRating;

    private TextView guestQuantity;
    private TextView roomsQuantity;

    private TextView dateSearch;

    private TextView hotelPrice;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hotel_checkout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        hotel = (Hotel) getArguments().getSerializable(HotelDetailActivity.HOTEL_EXTRA);
        hotelSearch = (HotelSearch) getArguments().getSerializable(HotelDetailActivity.HOTEL_SEARCH_EXTRA);

        BaseFragment fragment = new CheckoutLogicFragment();
        Bundle bundle = getArguments();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = this
                    .getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.hotel_checkout_logic, fragment,
                            fragment.getClass().getName());
            fragmentTransaction.commit();
        }
        initControls();
    }

    private void initControls() {

        hotelName = (TextView) getView().findViewById(R.id.hotel_name);
        hotelName.setText(hotel.getName());

        hotelRating = (RatingBar) getView().findViewById(R.id.hotel_rating);
        hotelRating.setEnabled(false);
        hotelRating.setProgress(hotel.getStarRating());

        roomsQuantity = (TextView) getView().findViewById(R.id.room_or_cabin_class_quantity);
        roomsQuantity.setText(String.valueOf(hotelSearch.getRoomQuantity()) + " Room");

        guestQuantity = (TextView) getView().findViewById(R.id.users_quantity);
        guestQuantity.setText(String.valueOf(hotelSearch.getGuestQuantity()));

        dateSearch = (TextView) getView().findViewById(R.id.dateFilter);
        dateSearch.setText(DateFormater.formatDateToShowInMonthAndDate(hotelSearch.getCheckInDate()) + " - " +
                DateFormater.formatDateToShowInMonthAndDate(hotelSearch.getCheckoutDate()));

        ((ViewStub) getView().findViewById(R.id.stub)).inflate();

        getView().findViewById(R.id.taxes_text).setVisibility(View.GONE);

        hotelPrice = (TextView) getView().findViewById(R.id.hotel_price);
        hotelPrice.setText("$" + hotel.getPrice() / 100);
    }

    @Override
    public void preCheckoutDidComplete(Boolean success, PairCheckoutResponse data, Throwable error) {
        super.preCheckoutDidComplete(success, data, error);
        if (success) {
            CheckoutLogicFragment checkoutLogicFragment = (CheckoutLogicFragment) getChildFragmentManager().findFragmentById(R.id.hotel_checkout_logic);
            if (checkoutLogicFragment != null) {
                checkoutLogicFragment.preCheckoutDidComplete(success, data, error);
            }
        }
    }

    @Override
    public void checkoutDidComplete(Boolean success, Throwable error) {
        super.checkoutDidComplete(success, error);
        if (success) {
            Bundle bundle = getArguments();
            Intent intent = new Intent(getActivity(), HotelSuccessActivity.class);
            intent.putExtra(Constants.MASTERPASS, true);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    public void pairCheckoutDidComplete(Boolean success, Throwable error) {
        super.pairCheckoutDidComplete(success, error);
        if (success) {
            Bundle bundle = getArguments();
            Intent intent = new Intent(getActivity(), HotelSuccessActivity.class);
            intent.putExtra(Constants.MASTERPASS, false);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    public void manualCheckoutDidComplete(Boolean success, Throwable error) {
        super.manualCheckoutDidComplete(success,error);
        if (success) {
            Bundle bundle = getArguments();
            Intent intent = new Intent(getActivity(), HotelSuccessActivity.class);
            intent.putExtra(Constants.MASTERPASS, false);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}