package com.mastercard.travel.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.mastercard.travel.R;
import com.mastercard.travel.activity.FlightCabinClassActivity;
import com.mastercard.travel.activity.FlightDetailActivity;
import com.mastercard.travel.activity.FlightFindLocationActivity;
import com.mastercard.travel.activity.FlightListActivity;
import com.mastercard.travel.model.CabinClass;
import com.mastercard.travel.model.FlightFilter;
import com.mastercard.travel.model.FlightSearch;
import com.mastercard.travel.util.DateFormater;
import com.mastercard.travel.view.AmountView;
import com.mastercard.travel.view.TwoLinesTextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by emi91_000 on 05/02/2015.
 */
public class FlightFindFragment extends BaseFragment {
    private static final int REQUEST_FROM_LOCATION = 6;
    private static final int REQUEST_CABIN_CLASS = 8;
    private static final int REQUEST_TO_LOCATION = 7;
    private FlightSearch flightSearch;
    private FlightFilter flightFilter;
    private TwoLinesTextView fromView;
    private TwoLinesTextView toView;
    private AmountView passengers;
    private TwoLinesTextView cabinClass;
    private TwoLinesTextView departView;
    private TwoLinesTextView returnView;

    private DatePickerDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flightSearch = new FlightSearch();
        flightSearch.setCabinClass(CabinClass.ECONOMY);
        Calendar calendar = Calendar.getInstance();
        flightSearch.setDepartDate(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        flightSearch.setReturnDate(calendar.getTime());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find_flight, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initControls();

        setListeners();

        getView().findViewById(R.id.searchFlights).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromView.getSubttile() == null || fromView.getSubttile().equalsIgnoreCase("Select Location") || fromView.getSubttile().trim().isEmpty() || toView.getSubttile() == null || toView.getSubttile().equalsIgnoreCase("Select Location") || toView.getSubttile().trim().isEmpty()) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity()).
                            setTitle(R.string.required_fields_required_title).
                            setMessage(R.string.required_fields_required).
                            setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    Dialog dialog = alert.create();
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);

                    dialog.show();
                } else {
                    flightSearch.setPassengersQuantity(passengers.getAmount());
                    flightSearch.setFromAirport(fromView.getSubttile());
                    flightSearch.setToAirport(toView.getSubttile());
                    FlightListActivity.startActivity(getActivity(), flightSearch, flightFilter);
                }
            }
        });
    }

    private void initControls() {

        fromView = (TwoLinesTextView) getView().findViewById(R.id.from_location);

        toView = (TwoLinesTextView) getView().findViewById(R.id.to_location);

        Calendar calendar = Calendar.getInstance();

        departView = (TwoLinesTextView) getView().findViewById(R.id.depart_date);
        departView.setSubtitle(DateFormater.getDateFormat(calendar, Calendar.SHORT));

        calendar.add(Calendar.DAY_OF_MONTH, 1);

        returnView = (TwoLinesTextView) getView().findViewById(R.id.return_date);
        returnView.setSubtitle(DateFormater.getDateFormat(calendar, Calendar.SHORT));
        cabinClass = (TwoLinesTextView) getView().findViewById(R.id.cabin_class);
        cabinClass.setSubtitle(flightSearch.getCabinClass().getNameResId());
        passengers = (AmountView) getView().findViewById(R.id.passengers);
    }

    private void setListeners() {

        fromView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FlightFindLocationActivity.class);

                getActivity().startActivityForResult(intent, REQUEST_FROM_LOCATION);
            }
        });

        toView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FlightFindLocationActivity.class);

                getActivity().startActivityForResult(intent, REQUEST_TO_LOCATION);
            }
        });

        departView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar checkInTime = Calendar.getInstance();
                checkInTime.setTime(flightSearch.getDepartDate());

                selectDate((TwoLinesTextView) v, checkInTime);
            }
        });

        returnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar checkOutTime = Calendar.getInstance();
                checkOutTime.setTime(flightSearch.getReturnDate());

                selectDate((TwoLinesTextView) v, checkOutTime);
            }
        });

        cabinClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlightCabinClassActivity.startActivityForResult(getActivity(), REQUEST_CABIN_CLASS, flightSearch);
            }
        });
    }

    private void selectDate(final TwoLinesTextView dateView, Calendar selectTime) {

        dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                dialog.setTitle(dateView.getTitle());

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);

                if (dateView == departView) {
                    setCheckInDate(calendar);
                } else {
                    setCheckOutDate(calendar);
                }

            }
        }, selectTime.get(Calendar.YEAR),
                selectTime.get(Calendar.MONTH),
                selectTime.get(Calendar.DAY_OF_MONTH)) {
            @Override
            public void setTitle(CharSequence title) {
                super.setTitle(dateView.getTitle());
            }
        };

        dialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis() - 1000);

        dialog.setCanceledOnTouchOutside(false);

        dialog.setCancelable(false);

        dialog.getDatePicker().setMinDate(new Date().getTime());
        dialog.show();
    }

    private void setCheckInDate(Calendar checkInDate) {

        flightSearch.setDepartDate(checkInDate.getTime());

        departView.setSubtitle(DateFormater.getDateFormat(checkInDate, Calendar.SHORT));

        checkInDate.add(Calendar.DAY_OF_MONTH, 1);
        flightSearch.setReturnDate(checkInDate.getTime());
        returnView.setSubtitle(DateFormater.getDateFormat(checkInDate, Calendar.SHORT));
    }

    private void setCheckOutDate(Calendar checkOutDate) {

        if (checkOutDate.getTime().after(flightSearch.getDepartDate()))
            flightSearch.setReturnDate(checkOutDate.getTime());
        else {
            checkOutDate.setTime(flightSearch.getReturnDate());

            Toast.makeText(getActivity(), "Date after", Toast.LENGTH_SHORT).show();
        }

        returnView.setSubtitle(DateFormater.getDateFormat(checkOutDate, Calendar.SHORT));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_FROM_LOCATION && resultCode == Activity.RESULT_OK) {
            String location = data.getExtras().getString("CityName");
            if (location != null && !location.isEmpty()) {
                fromView.setSubtitle(location);
            }
        } else {
            if (REQUEST_CABIN_CLASS == requestCode && resultCode == Activity.RESULT_OK) {
                flightSearch = (FlightSearch) data.getExtras().getSerializable(FlightDetailActivity.FLIGHT_SEARCH_EXTRA);
                if (flightSearch != null && flightSearch.getCabinClass() != null) {
                    cabinClass.setSubtitle(flightSearch.getCabinClass().getNameResId());
                }
            } else if (requestCode == REQUEST_TO_LOCATION && resultCode == Activity.RESULT_OK) {
                String location = data.getExtras().getString("CityName");
                if (location != null && !location.isEmpty()) {
                    toView.setSubtitle(location);
                }
            }
            {

            }
        }
    }
}
