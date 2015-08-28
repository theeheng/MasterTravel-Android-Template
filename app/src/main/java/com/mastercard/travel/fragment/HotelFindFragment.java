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
import com.mastercard.travel.activity.HotelFindLocationActivity;
import com.mastercard.travel.activity.HotelListActivity;
import com.mastercard.travel.model.HotelSearch;
import com.mastercard.travel.util.DateFormater;
import com.mastercard.travel.view.AmountView;
import com.mastercard.travel.view.TwoLinesTextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by emi91_000 on 05/02/2015.
 */
public class HotelFindFragment extends BaseFragment {

    public static final String HOTEL_SEARCH_EXTRA = "hotelSearch";

    private static final int REQUEST_LOCATION = 6;

    private HotelSearch hotelSearch;

    private DatePickerDialog dialog;

    private TwoLinesTextView locationView;
    private TwoLinesTextView checkInView;
    private TwoLinesTextView checkOutView;
    private AmountView guestsView;
    private AmountView romsView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hotelSearch = new HotelSearch();
        Calendar calendar = Calendar.getInstance();
        hotelSearch.setCheckInDate(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        hotelSearch.setCheckoutDate(calendar.getTime());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find_hotel, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initControls();

        setListeners();

        view.findViewById(R.id.searchHotels).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locationView.getSubttile() != null && !locationView.getSubttile().equalsIgnoreCase("Select Location") && !locationView.getSubttile().isEmpty()) {
                    Intent intent = new Intent(getActivity(), HotelListActivity.class);
                    Bundle bundle = new Bundle();
                    hotelSearch.setCityName(locationView.getSubttile());
                    hotelSearch.setRoomQuantity(romsView.getAmount());
                    hotelSearch.setGuestQuantity(guestsView.getAmount());

                    bundle.putSerializable(HOTEL_SEARCH_EXTRA, hotelSearch);
                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);
                } else {
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
                }

            }
        });
    }

    private void initControls() {

        locationView = (TwoLinesTextView) getView().findViewById(R.id.location);

        romsView = (AmountView) getView().findViewById(R.id.rooms);
        guestsView = (AmountView) getView().findViewById(R.id.guests);

        Calendar calendar = Calendar.getInstance();

        checkInView = (TwoLinesTextView) getView().findViewById(R.id.check_in);
        checkInView.setSubtitle(DateFormater.getDateFormat(calendar, Calendar.SHORT));

        calendar.add(Calendar.DAY_OF_MONTH, 1);

        checkOutView = (TwoLinesTextView) getView().findViewById(R.id.check_out);
        checkOutView.setSubtitle(DateFormater.getDateFormat(calendar, Calendar.SHORT));
    }

    private void setListeners() {

        locationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HotelFindLocationActivity.class);

                getActivity().startActivityForResult(intent, REQUEST_LOCATION);
            }
        });

        checkInView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar checkInTime = Calendar.getInstance();
                checkInTime.setTime(hotelSearch.getCheckInDate());

                selectDate((TwoLinesTextView) v, checkInTime);
            }
        });

        checkOutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar checkOutTime = Calendar.getInstance();
                checkOutTime.setTime(hotelSearch.getCheckoutDate());

                selectDate((TwoLinesTextView) v, checkOutTime);
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

                if (dateView == checkInView) {
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

        hotelSearch.setCheckInDate(checkInDate.getTime());

        checkInView.setSubtitle(DateFormater.getDateFormat(checkInDate, Calendar.SHORT));

        checkInDate.add(Calendar.DAY_OF_MONTH, 1);
        hotelSearch.setCheckoutDate(checkInDate.getTime());
        checkOutView.setSubtitle(DateFormater.getDateFormat(checkInDate, Calendar.SHORT));
    }

    private void setCheckOutDate(Calendar checkOutDate) {

        if (checkOutDate.getTime().after(hotelSearch.getCheckInDate()))
            hotelSearch.setCheckoutDate(checkOutDate.getTime());
        else {
            checkOutDate.setTime(hotelSearch.getCheckoutDate());

            Toast.makeText(getActivity(), "Date after", Toast.LENGTH_SHORT).show();
        }

        checkOutView.setSubtitle(DateFormater.getDateFormat(checkOutDate, Calendar.SHORT));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_LOCATION && resultCode == Activity.RESULT_OK) {
            String location = data.getExtras().getString("CityName");
            if (location != null && !location.isEmpty()) {
                locationView.setSubtitle(location);
            }
        }
    }
}
