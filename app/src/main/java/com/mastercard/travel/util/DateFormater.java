package com.mastercard.travel.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by emi91_000 on 14/02/2015.
 */
public class DateFormater {

    public static String getDateFormat(Calendar calendar, int format) {

        return calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.getDisplayName(Calendar.MONTH, format, Locale.US);
    }

    public static String getDateFormat(int year, int monthOfYear, int dayOfMonth, int format) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(year, monthOfYear, dayOfMonth);

        return getDateFormat(calendar, format);
    }

    public static String formatDateToShowInMonthAndDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd", Locale.US);
        return sdf.format(date);
    }

    public static String formatDateToShowInShortMonthAndDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd", Locale.US);
        return sdf.format(date);
    }

    public static String formatDateToShowInFlightList(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd',' yyyy ", Locale.US);
        return sdf.format(date);
    }
}
