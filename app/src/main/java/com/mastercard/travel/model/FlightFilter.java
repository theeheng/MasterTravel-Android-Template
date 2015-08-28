package com.mastercard.travel.model;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by emi91_000 on 13/02/2015.
 */
public class FlightFilter implements Serializable {
    private PriceFilter priceFilter;
    private StarsFilter starFilter;
    private boolean nonStopFlightsOnly;
    private String selectedAirline;
    private Set<String> airlines;

    public FlightFilter() {
    }

    public FlightFilter(PriceFilter priceFilter, StarsFilter starFilter) {
        this.priceFilter = priceFilter;
        this.starFilter = starFilter;
    }

    public StarsFilter getStarFilter() {
        return starFilter;
    }

    public void setStarFilter(StarsFilter starFilter) {
        this.starFilter = starFilter;
    }

    public String getSelectedAirline() {
        return selectedAirline;
    }

    public void setSelectedAirline(String selectedAirline) {
        this.selectedAirline = selectedAirline;
    }

    public PriceFilter getPriceFilter() {
        return priceFilter;
    }

    public void setPriceFilter(PriceFilter priceFilter) {
        this.priceFilter = priceFilter;
    }

    public Set<String> getAirlines() {
        return airlines;
    }

    public void setAirlines(Set<String> airlines) {
        this.airlines = airlines;
    }

    public boolean isNonStopFlightsOnly() {
        return nonStopFlightsOnly;
    }

    public void setNonStopFlightsOnly(int flag) {
        this.nonStopFlightsOnly = flag == 1;
    }
}
