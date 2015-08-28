package com.mastercard.travel.model;


import com.anypresence.sdk.master_travel_ecomm.models.Hotel;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by emi91_000 on 12/02/2015.
 */
public enum FlightSort implements Serializable {
    PRICE {
        public Comparator getComparator() {
            return new Comparator<Hotel>() {
                @Override
                public int compare(Hotel hotel1, Hotel hotel2) {
                    return hotel1.getPrice() - hotel2.getPrice();
                }

                @Override
                public boolean equals(Object object) {
                    return false;
                }
            };
        }
    }, DEPARTURE_TAKE_OFF_TIME {
        public Comparator getComparator() {
            return new Comparator<Hotel>() {
                @Override
                public int compare(Hotel hotel1, Hotel hotel2) {
                    return hotel1.getAverageRating() - hotel2.getAverageRating();
                }

                @Override
                public boolean equals(Object object) {
                    return false;
                }
            };
        }
    }, DEPARTURE_LANDING_TIME {
        public Comparator getComparator() {
            return new Comparator<Hotel>() {
                @Override
                public int compare(Hotel hotel1, Hotel hotel2) {
                    return hotel1.getStarRating() - hotel2.getStarRating();
                }

                @Override
                public boolean equals(Object object) {
                    return false;
                }
            };
        }
    }, RETURN_TAKE_OFF_TIME {
        public Comparator getComparator() {
            return new Comparator<Hotel>() {
                @Override
                public int compare(Hotel hotel1, Hotel hotel2) {
                    Float diff = hotel1.getDistance() - hotel2.getDistance();
                    return diff < 0 ? -1 : 1;
                }

                @Override
                public boolean equals(Object object) {
                    return false;
                }
            };
        }
    }, RETURN_LANDING_TIME {
        public Comparator getComparator() {
            return new Comparator<Hotel>() {
                @Override
                public int compare(Hotel hotel1, Hotel hotel2) {
                    Float diff = hotel1.getDistance() - hotel2.getDistance();
                    return diff < 0 ? -1 : 1;
                }

                @Override
                public boolean equals(Object object) {
                    return false;
                }
            };
        }
    }, AIRLINE {
        public Comparator getComparator() {
            return new Comparator<Hotel>() {
                @Override
                public int compare(Hotel hotel1, Hotel hotel2) {
                    Float diff = hotel1.getDistance() - hotel2.getDistance();
                    return diff < 0 ? -1 : 1;
                }

                @Override
                public boolean equals(Object object) {
                    return false;
                }
            };
        }
    }, DURATION {
        public Comparator getComparator() {
            return new Comparator<Hotel>() {
                @Override
                public int compare(Hotel hotel1, Hotel hotel2) {
                    Float diff = hotel1.getDistance() - hotel2.getDistance();
                    return diff < 0 ? -1 : 1;
                }

                @Override
                public boolean equals(Object object) {
                    return false;
                }
            };
        }
    };

    public Comparator getComparator() {
        return null;
    }

}
