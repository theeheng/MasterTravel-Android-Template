package com.mastercard.travel.util;

import android.util.Log;

import com.anypresence.rails_droid.IAPFutureCallback;
import com.anypresence.sdk.master_travel_ecomm.models.Flight;
import com.anypresence.sdk.master_travel_ecomm.models.Hotel;
import com.anypresence.sdk.master_travel_ecomm.models.OrderDetail;
import com.anypresence.sdk.master_travel_ecomm.models.OrderHeader;
import com.anypresence.sdk.master_travel_ecomm.models.User;
import com.mastercard.travel.model.FlightSearch;
import com.mastercard.travel.model.HotelSearch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by diego.rotondale on 1/28/2015.
 * Copyright (c) 2015 AnyPresence, Inc. All rights reserved.
 */
public class MPECommerceManager {

    private static MPECommerceManager instance;
    private static String LOG_TAG = MPECommerceManager.class.getSimpleName();
    private IECommerceManager delegate;

    public static MPECommerceManager getInstance() {
        if (instance == null)
            instance = new MPECommerceManager();
        return instance;
    }

    public void setDelegate(IECommerceManager delegate) {
        this.delegate = delegate;
    }

    private void getCurrentCart(final OrderHeader orderHeader, final IAPFutureCallback<List<OrderDetail>> callback) {
        if (orderHeader != null && orderHeader.getId() != null) {
            getCartItems(orderHeader.getId().toString(), callback);
        } else {
            IAPFutureCallback<List<OrderHeader>> orderHeaderCallback = new IAPFutureCallback<List<OrderHeader>>() {
                @Override
                public void finished(List<OrderHeader> orderHeaders, Throwable throwable) {
                    callback.finished(null, throwable);
                }

                @Override
                public void onSuccess(List<OrderHeader> orderHeaders) {
                    if (!orderHeaders.isEmpty()) {
                        OrderHeader orderHeaderFound = orderHeaders.get(0);
                        delegate.setOrderHeader(orderHeaderFound);
                        // Just kidding, there is a cart. Proceed as normal.
                        getCartItems(orderHeaderFound.getId().toString(), callback);
                    } else {
                        // There is not a cart, create one
                        final OrderHeader newHeader = new OrderHeader();
                        User user = delegate.getUser();
                        newHeader.setUserId(user.getId().toString());
                        IAPFutureCallback<?> saveCallback = new IAPFutureCallback<Object>() {
                            @Override
                            public void finished(Object o, Throwable throwable) {
                                Log.e(LOG_TAG, throwable.toString());
                            }

                            @Override
                            public void onSuccess(Object o) {
                                Log.d(LOG_TAG, "OrderHeader created");
                                getCurrentCart(newHeader, callback);
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                Log.e(LOG_TAG, throwable.toString());
                            }
                        };
                        newHeader.saveInBackground(saveCallback);
                    }
                }

                @Override
                public void onFailure(Throwable throwable) {
                    callback.onFailure(throwable);
                }
            };
             /*
             * Possible race condition here if we try to get the cart
             * header a bunch of times. If we figure that there is no cart,
             * we start a mutex lock on the singleton instance, and check again.
             * If we are still sure that there is no cart, we will create one.
             */
            OrderHeader.queryInBackground(OrderHeader.Scopes.MY_OPEN_ORDERS, orderHeaderCallback);
        }
    }

    public void getCurrentCart(IAPFutureCallback<List<OrderDetail>> callback) {
        getCurrentCart(delegate.getOrderHeader(), callback);
    }

    private void getCartItems(String headerId, IAPFutureCallback<List<OrderDetail>> orderDetailCallback) {
        if (headerId == null) {
            Log.e(LOG_TAG, "Error: HeaderId is null");
            return;
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("order_header_id", headerId);
        OrderDetail.queryInBackground(OrderDetail.Scopes.EXACT_MATCH, params, orderDetailCallback);
    }

    public void addHotelToCart(final Hotel hotel, final HotelSearch hotelSearch, final IAPFutureCallback<List<OrderDetail>> callback) {
        IAPFutureCallback<List<OrderDetail>> currentCartCallback = new IAPFutureCallback<List<OrderDetail>>() {
            @Override
            public void finished(List<OrderDetail> orderDetails, Throwable throwable) {
                callback.finished(orderDetails, throwable);
            }

            @Override
            public void onSuccess(final List<OrderDetail> orderDetails) {
                OrderDetail newOrderDetail = new OrderDetail();
                newOrderDetail.setOrderHeaderId(delegate.getOrderHeader().getId().toString());
                newOrderDetail.setHotelId(hotel.getId().toString());
                newOrderDetail.setQuantity(1);
                newOrderDetail.setHotelCheckInDate(hotelSearch.getCheckInDate());
                newOrderDetail.setHotelCheckOutDate(hotelSearch.getCheckoutDate());
                newOrderDetail.setHotelNumGuests(hotelSearch.getGuestQuantity());
                newOrderDetail.setHotelNumRooms(hotelSearch.getRoomQuantity());
                newOrderDetail.setHotelId(hotel.getId().toString());

                IAPFutureCallback<?> saveCallback = new IAPFutureCallback<Object>() {
                    @Override
                    public void finished(Object o, Throwable throwable) {
                        Log.e(LOG_TAG, throwable.toString());
                        callback.finished(null, throwable);
                    }

                    @Override
                    public void onSuccess(Object o) {
                        Log.d(LOG_TAG, "Adding Hotel to Cart: " + hotel.getId().toString());
                        IAPFutureCallback<OrderHeader> updateCallback = new IAPFutureCallback<OrderHeader>() {
                            @Override
                            public void finished(OrderHeader orderHeader, Throwable throwable) {
                                callback.onSuccess(orderDetails);
                            }

                            @Override
                            public void onSuccess(OrderHeader orderHeader) {
                                callback.onSuccess(orderDetails);
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                callback.onSuccess(orderDetails);
                            }
                        };
                        updateCurrentCartHeader(updateCallback);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e(LOG_TAG, throwable.toString());
                        callback.onFailure(throwable);
                    }
                };
                newOrderDetail.saveInBackground(saveCallback);
            }

            @Override
            public void onFailure(Throwable throwable) {
                callback.onFailure(throwable);
            }
        };
        getCurrentCart(delegate.getOrderHeader(), currentCartCallback);
    }

    public void addFlightToCart(final Flight flight, final FlightSearch flightSearch, final String cabinClass, final IAPFutureCallback<List<OrderDetail>> callback) {
        IAPFutureCallback<List<OrderDetail>> currentCartCallback = new IAPFutureCallback<List<OrderDetail>>() {
            @Override
            public void finished(List<OrderDetail> orderDetails, Throwable throwable) {
                callback.finished(orderDetails, throwable);
            }

            @Override
            public void onSuccess(final List<OrderDetail> orderDetails) {
                OrderDetail newOrderDetail = new OrderDetail();
                newOrderDetail.setOrderHeaderId(delegate.getOrderHeader().getId().toString());
                newOrderDetail.setFlightId(flight.getId().toString());
                newOrderDetail.setQuantity(1);
                newOrderDetail.setFlightCabinClass(cabinClass);
                newOrderDetail.setFlightNumPassengers(flightSearch.getPassengersQuantity());
                newOrderDetail.setFlightOutboundDate(flightSearch.getDepartDate());
                newOrderDetail.setFlightReturnDate(flightSearch.getReturnDate());

                IAPFutureCallback<?> saveCallback = new IAPFutureCallback<Object>() {
                    @Override
                    public void finished(Object o, Throwable throwable) {
                        Log.e(LOG_TAG, throwable.toString());
                        callback.finished(null, throwable);
                    }

                    @Override
                    public void onSuccess(Object o) {
                        Log.d(LOG_TAG, "Adding Flight to Cart: " + flight.getId().toString());
                        IAPFutureCallback<OrderHeader> updateCallback = new IAPFutureCallback<OrderHeader>() {
                            @Override
                            public void finished(OrderHeader orderHeader, Throwable throwable) {
                                callback.onSuccess(orderDetails);
                            }

                            @Override
                            public void onSuccess(OrderHeader orderHeader) {
                                callback.onSuccess(orderDetails);
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                callback.onSuccess(orderDetails);
                            }
                        };
                        updateCurrentCartHeader(updateCallback);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e(LOG_TAG, throwable.toString());
                        callback.onFailure(throwable);
                    }
                };
                newOrderDetail.saveInBackground(saveCallback);
            }

            @Override
            public void onFailure(Throwable throwable) {
                callback.onFailure(throwable);
            }
        };
        getCurrentCart(delegate.getOrderHeader(), currentCartCallback);
    }

    public void getCartQuantity(final IAPFutureCallback<Integer> callback) {
        IAPFutureCallback<List<OrderDetail>> currentCartCallback = new IAPFutureCallback<List<OrderDetail>>() {
            @Override
            public void finished(List<OrderDetail> orderDetails, Throwable throwable) {
                Log.e(LOG_TAG, throwable.toString());
                callback.finished(null, throwable);
            }

            @Override
            public void onSuccess(List<OrderDetail> orderDetails) {
                Integer totalQuantity = 0;
                for (OrderDetail orderDetail : orderDetails) {
                    totalQuantity += orderDetail.getQuantity();
                }
                callback.onSuccess(totalQuantity);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(LOG_TAG, throwable.toString());
                callback.onFailure(new Throwable());
            }
        };
        getCurrentCart(delegate.getOrderHeader(), currentCartCallback);
    }

    public void updateCurrentCartHeader(final IAPFutureCallback<OrderHeader> orderHeader) {
        IAPFutureCallback<List<OrderHeader>> orderHeaderCallback = new IAPFutureCallback<List<OrderHeader>>() {
            @Override
            public void finished(List<OrderHeader> orderHeaders, Throwable throwable) {
                Log.e(LOG_TAG, throwable.toString());
                delegate.setOrderHeader(null);
                orderHeader.finished(null, throwable);
            }

            @Override
            public void onSuccess(List<OrderHeader> orderHeaders) {
                if (!orderHeaders.isEmpty()) {
                    OrderHeader orderHeaderFound = orderHeaders.get(0);
                    delegate.setOrderHeader(orderHeaderFound);
                    orderHeader.onSuccess(orderHeaderFound);
                } else {
                    Log.d(LOG_TAG, "Error: No Cart Headers Exist For User");
                    orderHeader.onFailure(new Throwable());
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(LOG_TAG, throwable.toString());
                delegate.setOrderHeader(null);
                orderHeader.onFailure(throwable);
            }
        };
        OrderHeader.queryInBackground(OrderHeader.Scopes.MY_OPEN_ORDERS, orderHeaderCallback);
    }
}
