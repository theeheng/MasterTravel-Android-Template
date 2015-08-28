package com.mastercard.travel;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.anypresence.APSetup;
import com.anypresence.masterpass_android_library.dto.Order;
import com.anypresence.masterpass_android_library.dto.PairCheckoutResponse;
import com.anypresence.masterpass_android_library.dto.PreCheckoutResponse;
import com.anypresence.rails_droid.ObjectId;
import com.anypresence.sdk.config.Config;
import com.anypresence.sdk.master_travel_ecomm.models.OrderHeader;
import com.anypresence.sdk.master_travel_ecomm.models.User;
import com.mastercard.travel.fragment.BaseFragment;
import com.mastercard.travel.util.IECommerceManager;
import com.mastercard.travel.volley.LruBitmapCache;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by diego.rotondale on 9/30/2014.
 */
public class Application extends android.app.Application implements IECommerceManager {
    private static Application instance;
    private Map<Integer, Stack<BaseFragment>> fragments = new HashMap<Integer, Stack<BaseFragment>>();
    private Map<String, String> sectorMap = new HashMap<String, String>();
    private User user;
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;
    private OrderHeader orderHeader;
    private Order order = new Order();
    private PreCheckoutResponse preCheckout;
    private PairCheckoutResponse pairCheckout;

    public static Application getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        fragments.put(0, new Stack<BaseFragment>());
        fragments.put(1, new Stack<BaseFragment>());
        fragments.put(2, new Stack<BaseFragment>());
        APSetup.setupOrm(getApplicationContext());
        APSetup.setup();
        Config.getInstance().setBaseUrl(Constants.BACKEND_URL);
        Config.getInstance().setAppUrl(Constants.BACKEND_URL + "/api/" + Constants.VERSION);
        Config.getInstance().setAuthUrl(Constants.BACKEND_URL + "/auth/password/callback");
        Config.getInstance().setDeauthUrl(Constants.BACKEND_URL + "/auth/signout");
        Config.getInstance().setStrictQueryFieldCheck(false);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Stack<BaseFragment> getFragmentsIn(int position) {
        return fragments.get(position);
    }

    public boolean backStack(int mNavigationSelected, int position) {
        Stack<BaseFragment> stack = fragments.get(mNavigationSelected);
        if (stack.size() != position + 1) {
            stack.pop();
            return true;
        }
        return false;
    }

    public void backStack(int mNavigationSelected) {
        Stack<BaseFragment> stack = fragments.get(mNavigationSelected);
        stack.pop();
    }

    public Map<String, String> getSectorMap() {
        return sectorMap;
    }

    @Override
    public OrderHeader getOrderHeader() {
        return orderHeader;
    }

    @Override
    public void setOrderHeader(OrderHeader orderHeader) {
        this.orderHeader = orderHeader;
        order = new Order();
        if (orderHeader != null) {
            ObjectId orderHeaderId = orderHeader.getId();
            if (orderHeaderId != null)
                order.orderNumber = orderHeaderId.toString();
        }
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public PreCheckoutResponse getPreCheckout() {
        return preCheckout;
    }

    public void setPreCheckout(PreCheckoutResponse preCheckout) {
        this.preCheckout = preCheckout;
    }

    public PairCheckoutResponse getPairCheckout() {
        return pairCheckout;
    }

    public void setPairCheckout(PairCheckoutResponse pairCheckout) {
        this.pairCheckout = pairCheckout;
    }
}
