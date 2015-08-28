package com.mastercard.travel.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mastercard.travel.R;
import com.mastercard.travel.model.HotelFilter;
import com.mastercard.travel.model.PriceFilter;
import com.mastercard.travel.model.StarsFilter;

/**
 * Created by emi91_000 on 05/02/2015.
 */
public class HotelFilterFragment extends BaseFragment {
    public static final String HOTEL_FILTER_EXTRA = "filterExtra";
    private RadioButton anyStarRadioButton;
    private RadioButton oneStarRadioButton;
    private RadioButton twoStarRadioButton;
    private RadioButton threeStarRadioButton;
    private RadioButton fourStarRadioButton;
    private RadioButton fiveStarRadioButton;
    private RadioGroup starsRadioGroup;
    private HotelFilter filter;

    private RadioButton anyPriceRadioButton;
    private RadioButton price1RadioButton;
    private RadioButton price2RadioButton;
    private RadioButton price3RadioButton;
    private RadioGroup priceRadioGroup;

    private EditText propertyName;
    private ViewGroup propertyNamesContainer;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hotel_filter, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        filter = (HotelFilter) getArguments().getSerializable(HOTEL_FILTER_EXTRA);
        starsRadioGroup = (RadioGroup) view.findViewById(R.id.starsRadioGroup);
        anyStarRadioButton = (RadioButton) view.findViewById(R.id.anyStar);
        anyStarRadioButton.setTag(StarsFilter.ANY);
        oneStarRadioButton = (RadioButton) view.findViewById(R.id.oneStar);
        oneStarRadioButton.setTag(StarsFilter.ONE_STAR);
        twoStarRadioButton = (RadioButton) view.findViewById(R.id.twoStars);
        twoStarRadioButton.setTag(StarsFilter.TWO_STARS);
        threeStarRadioButton = (RadioButton) view.findViewById(R.id.threeStars);
        threeStarRadioButton.setTag(StarsFilter.THREE_STARS);
        fourStarRadioButton = (RadioButton) view.findViewById(R.id.fourStars);
        fourStarRadioButton.setTag(StarsFilter.FOUR_STARS);
        fiveStarRadioButton = (RadioButton) view.findViewById(R.id.fiveStars);
        fiveStarRadioButton.setTag(StarsFilter.FIVE_STARS);

        priceRadioGroup = (RadioGroup) view.findViewById(R.id.priceRadioGroup);
        anyPriceRadioButton = (RadioButton) view.findViewById(R.id.anyPrice);
        anyPriceRadioButton.setTag(PriceFilter.ANY);
        price1RadioButton = (RadioButton) view.findViewById(R.id.price1);
        price1RadioButton.setTag(PriceFilter.PRICE1);
        price2RadioButton = (RadioButton) view.findViewById(R.id.price2);
        price2RadioButton.setTag(PriceFilter.PRICE2);
        price3RadioButton = (RadioButton) view.findViewById(R.id.price3);
        price3RadioButton.setTag(PriceFilter.PRICE3);

        propertyName = (EditText) view.findViewById(R.id.property_name);
        propertyNamesContainer = (ViewGroup) view.findViewById(R.id.airlines_container);

        view.findViewById(R.id.doneFilter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getViewValues();
                done();
            }
        });

        initpropertyTypes();

        initRadioButtons();

    }

    private void initpropertyTypes() {
        if (filter.getPropertyTypes() != null && !filter.getPropertyTypes().isEmpty()) {
            propertyNamesContainer.setVisibility(View.VISIBLE);
            CheckBox checkBox = null;
            for (String propertyType : filter.getPropertyTypes()) {
                checkBox = new CheckBox(getActivity());
                checkBox.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                checkBox.setText(propertyType);
                propertyNamesContainer.addView(checkBox);
            }
        }

    }

    private void getViewValues() {
        int radioButtonID = starsRadioGroup.getCheckedRadioButtonId();
        View starRadioButtonChecked = starsRadioGroup.findViewById(radioButtonID);
        filter.setStarFilter((StarsFilter) starRadioButtonChecked.getTag());

        radioButtonID = priceRadioGroup.getCheckedRadioButtonId();
        View priceRadioButtonChecked = priceRadioGroup.findViewById(radioButtonID);
        filter.setPriceFilter((PriceFilter) priceRadioButtonChecked.getTag());

        filter.setPropertyNameFilter(propertyName.getText().toString());
    }

    private void done() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(HOTEL_FILTER_EXTRA, filter);
        intent.putExtras(bundle);
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    private void initRadioButtons() {
        switch (filter.getPriceFilter()) {
            case ANY:
                anyPriceRadioButton.setChecked(true);
                break;
            case PRICE1:
                price1RadioButton.setChecked(true);
                break;
            case PRICE2:
                price2RadioButton.setChecked(true);
                break;
            case PRICE3:
                price3RadioButton.setChecked(true);
                break;
        }

        switch (filter.getStarFilter()) {
            case ANY:
                anyStarRadioButton.setChecked(true);
                break;
            case ONE_STAR:
                oneStarRadioButton.setChecked(true);
                break;
            case TWO_STARS:
                twoStarRadioButton.setChecked(true);
                break;
            case THREE_STARS:
                threeStarRadioButton.setChecked(true);
                break;
            case FOUR_STARS:
                fourStarRadioButton.setChecked(true);
                break;
            case FIVE_STARS:
                fiveStarRadioButton.setChecked(true);
                break;
        }
        propertyName.setText(filter.getPropertyNameFilter());
    }

}
