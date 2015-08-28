package com.mastercard.travel.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by emi91_000 on 13/02/2015.
 */
public class HotelSearch implements Serializable {
    private String cityName;
    private int guestQuantity;
    private int roomQuantity;
    private Date checkInDate;
    private Date checkoutDate;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getGuestQuantity() {
        return guestQuantity;
    }

    public void setGuestQuantity(int guestQuantity) {
        this.guestQuantity = guestQuantity;
    }

    public int getRoomQuantity() {
        return roomQuantity;
    }

    public void setRoomQuantity(int roomQuantity) {
        this.roomQuantity = roomQuantity;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
}
