package com.mastercard.travel.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mastercard.travel.R;
import com.mastercard.travel.model.HotelSort;

/**
 * Created by emi91_000 on 05/02/2015.
 */
public class HotelSortFragment extends BaseFragment {
    public static final String HOTEL_SORT_EXTRA = "hotelSortExtra";

    private TextView price;
    private TextView distance;
    private TextView stars;
    private TextView averageReviews;
    private HotelSort hotelSort;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hotel_sort, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        price = (TextView) view.findViewById(R.id.price);
        distance = (TextView) view.findViewById(R.id.distance_from_city_center);
        stars = (TextView) view.findViewById(R.id.industry_stars);
        averageReviews = (TextView) view.findViewById(R.id.average_review);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markAndSort(v);
            }
        };
        price.setOnClickListener(listener);
        distance.setOnClickListener(listener);
        stars.setOnClickListener(listener);
        averageReviews.setOnClickListener(listener);
        if (getArguments().getSerializable(HOTEL_SORT_EXTRA) != null) {
            hotelSort = (HotelSort) getArguments().getSerializable(HOTEL_SORT_EXTRA);
            switch (hotelSort) {
                case PRICE:
                    setSelected(price);
                    break;
                case AVERAGE_REVIEW:
                    setSelected(averageReviews);
                    break;
                case DISTANCE_FROM_CITY_CENTER:
                    setSelected(distance);
                    break;
                case INDUSTRY_STARS:
                    setSelected(stars);
                    break;
                default:
                    break;
            }
        }

    }

    public void setSelected(View view) {
        if (price == view) hotelSort = HotelSort.PRICE;
        if (distance == view) hotelSort = HotelSort.DISTANCE_FROM_CITY_CENTER;
        if (stars == view) hotelSort = HotelSort.INDUSTRY_STARS;
        if (averageReviews == view) hotelSort = HotelSort.AVERAGE_REVIEW;
        price.setCompoundDrawablesWithIntrinsicBounds(0, 0, view == price ? R.drawable.check : 0, 0);
        distance.setCompoundDrawablesWithIntrinsicBounds(0, 0, view == distance ? R.drawable.check : 0, 0);
        stars.setCompoundDrawablesWithIntrinsicBounds(0, 0, view == stars ? R.drawable.check : 0, 0);
        averageReviews.setCompoundDrawablesWithIntrinsicBounds(0, 0, averageReviews == view ? R.drawable.check : 0, 0);

    }

    public void markAndSort(View view) {
        setSelected(view);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(HOTEL_SORT_EXTRA, hotelSort);
        intent.putExtras(bundle);
        getActivity().setIntent(intent);
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

}
