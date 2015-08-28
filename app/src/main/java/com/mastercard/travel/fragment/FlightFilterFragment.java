package com.mastercard.travel.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.mastercard.travel.R;
import com.mastercard.travel.model.FlightFilter;
import com.mastercard.travel.model.PriceFilter;
import com.mastercard.travel.view.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emi91_000 on 05/02/2015.
 */
public class FlightFilterFragment extends BaseFragment {
    public static final String FLIGHT_FILTER_EXTRA = "flightFilterExtra";
    private FlightFilter filter;
    private List<CheckBox> checkBoxes = new ArrayList<CheckBox>();

    private RadioButton anyPriceRadioButton;
    private RadioButton price1RadioButton;
    private RadioButton price2RadioButton;
    private RadioButton price3RadioButton;
    private RadioGroup priceRadioGroup;

    private EditText propertyName;
    private ViewGroup airlinesContainer;
    private SeekBar nonStopFligthsOnly;
    private RangeSeekBar outboundTakeoffTime;
    private RangeSeekBar returnTakeoffTime;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flight_filter, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        filter = (FlightFilter) getArguments().getSerializable(FLIGHT_FILTER_EXTRA);
        nonStopFligthsOnly = (SeekBar) view.findViewById(R.id.non_stop_flights_switch);
        nonStopFligthsOnly.setMax(1);
        outboundTakeoffTime = (RangeSeekBar) view.findViewById(R.id.outbound_takeoff_time);
        outboundTakeoffTime.setMinText("08:00");
        outboundTakeoffTime.setMaxText("21:00");
        returnTakeoffTime = (RangeSeekBar) view.findViewById(R.id.return_take_off_time);
        returnTakeoffTime.setMinText("20:00");
        returnTakeoffTime.setMaxText("23:59");

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
        airlinesContainer = (ViewGroup) view.findViewById(R.id.airlines_container);

        view.findViewById(R.id.doneFilter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getViewValues();
                done();
            }
        });

        initAirlines();

        initRadioButtons();

    }

    private void initAirlines() {
        if (filter.getAirlines() != null && !filter.getAirlines().isEmpty()) {
            airlinesContainer.setVisibility(View.VISIBLE);
            CheckBox checkBox = null;
            for (String airline : filter.getAirlines()) {
                checkBox = new CheckBox(getActivity());
                checkBoxes.add(checkBox);
                checkBox.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                checkBox.setTag(airline);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            for (CheckBox checkBox1 : checkBoxes) {
                                if (checkBox1 != buttonView) {
                                    checkBox1.setChecked(false);
                                    filter.setSelectedAirline((String) buttonView.getTag());
                                }
                            }
                        } else {
                            filter.setSelectedAirline("");
                        }
                    }
                });
                if (airline.equals(filter.getSelectedAirline())) {
                    checkBox.setChecked(true);
                }
                checkBox.setText(airline);
                airlinesContainer.addView(checkBox);
            }
        }

    }

    private void getViewValues() {

        int radioButtonID = priceRadioGroup.getCheckedRadioButtonId();
        View priceRadioButtonChecked = priceRadioGroup.findViewById(radioButtonID);
        filter.setPriceFilter((PriceFilter) priceRadioButtonChecked.getTag());

        filter.setNonStopFlightsOnly(nonStopFligthsOnly.getProgress());
    }

    private void done() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FLIGHT_FILTER_EXTRA, filter);
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
    }

}
