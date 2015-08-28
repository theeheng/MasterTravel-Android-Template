package com.mastercard.travel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.anypresence.sdk.master_travel_ecomm.models.Hotel;
import com.mastercard.travel.R;
import com.mastercard.travel.activity.HotelDetailActivity;
import com.mastercard.travel.view.TwoLinesTextView;

/**
 * Created by emi91_000 on 14/02/2015.
 */
public class DetailReviewHotelFragment extends Fragment {

    private Hotel hotel;
    private TwoLinesTextView averageRatingText;
    private ProgressBar averageRating;
    private TwoLinesTextView serviceText;
    private ProgressBar serviceRating;
    private TwoLinesTextView cleanlinessText;
    private ProgressBar cleanlinessRating;


    public static DetailReviewHotelFragment newInstance(Hotel hotel) {
        DetailReviewHotelFragment frag = new DetailReviewHotelFragment();

        Bundle bun = new Bundle();
        bun.putSerializable(HotelDetailActivity.HOTEL_EXTRA, hotel);

        frag.setArguments(bun);

        return frag;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hotel = (Hotel) getArguments().getSerializable(HotelDetailActivity.HOTEL_EXTRA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hotel_detail_reviews, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initControls();
    }

    private void initControls() {

        averageRatingText = (TwoLinesTextView) getView().findViewById(R.id.rating_average_title);
        averageRatingText.setSubtitle(String.valueOf(hotel.getAverageRating()));

        averageRating = (ProgressBar) getView().findViewById(R.id.rating_average_progress);
        averageRating.setProgress(hotel.getAverageRating());

        serviceText = (TwoLinesTextView) getView().findViewById(R.id.service_text);
        serviceText.setSubtitle(String.valueOf(hotel.getAverageServiceRating()));

        serviceRating = (ProgressBar) getView().findViewById(R.id.service_rating);
        serviceRating.setProgress(hotel.getAverageServiceRating());

        cleanlinessText = (TwoLinesTextView) getView().findViewById(R.id.hotel_text);
        cleanlinessText.setSubtitle(String.valueOf(hotel.getAverageCleanlinessRating()));

        cleanlinessRating = (ProgressBar) getView().findViewById(R.id.hotel_rating);
        cleanlinessRating.setProgress(hotel.getAverageCleanlinessRating());
    }
}
