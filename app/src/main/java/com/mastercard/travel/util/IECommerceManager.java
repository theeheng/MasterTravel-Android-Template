package com.mastercard.travel.util;


import com.anypresence.sdk.master_travel_ecomm.models.OrderHeader;
import com.anypresence.sdk.master_travel_ecomm.models.User;

/**
 * Created by diego.rotondale on 29/01/2015.
 * Copyright (c) 2015 AnyPresence, Inc. All rights reserved.
 */
public interface IECommerceManager {
    User getUser();

    OrderHeader getOrderHeader();

    void setOrderHeader(OrderHeader orderHeaderFound);
}
