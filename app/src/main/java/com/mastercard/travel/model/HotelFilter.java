package com.mastercard.travel.model;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by emi91_000 on 13/02/2015.
 */
public class HotelFilter implements Serializable {
    private PriceFilter priceFilter;
    private StarsFilter starFilter;
    private String propertyNameFilter;
    private Set<String> selectedPropertyTypeFilters;
    private Set<String> propertyTypes;

    public HotelFilter() {
    }

    public HotelFilter(PriceFilter priceFilter, StarsFilter starFilter) {
        this.priceFilter = priceFilter;
        this.starFilter = starFilter;
    }

    public StarsFilter getStarFilter() {
        return starFilter;
    }

    public void setStarFilter(StarsFilter starFilter) {
        this.starFilter = starFilter;
    }

    public String getPropertyNameFilter() {
        return propertyNameFilter;
    }

    public void setPropertyNameFilter(String propertyNameFilter) {
        this.propertyNameFilter = propertyNameFilter;
    }

    public Set<String> getSelectedPropertyTypeFilters() {
        return selectedPropertyTypeFilters;
    }

    public void setSelectedPropertyTypeFilters(Set<String> selectedPropertyTypeFilters) {
        this.selectedPropertyTypeFilters = selectedPropertyTypeFilters;
    }

    public Set<String> getPropertyTypes() {
        return propertyTypes;
    }

    public void setPropertyTypes(Set<String> propertyTypes) {
        this.propertyTypes = propertyTypes;
    }

    public PriceFilter getPriceFilter() {
        return priceFilter;
    }

    public void setPriceFilter(PriceFilter priceFilter) {
        this.priceFilter = priceFilter;
    }
}
