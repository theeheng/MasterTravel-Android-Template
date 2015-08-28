package com.mastercard.travel.model;

import com.mastercard.travel.R;

import java.io.Serializable;

/**
 * Created by emi91_000 on 19/02/2015.
 */
public enum CabinClass implements Serializable {
    ECONOMY(R.string.economy), PREMIUM_ECONOMY(R.string.premium_economy), BUSINESS(R.string.business), FIRST_CLASS(R.string.first_class);

    private int name;

    private CabinClass(int name) {
        this.name = name;
    }

    public int getNameResId() {
        return name;
    }
}
