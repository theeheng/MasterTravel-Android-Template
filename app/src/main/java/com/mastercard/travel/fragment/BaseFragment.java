package com.mastercard.travel.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.anypresence.masterpass_android_library.MPManager;
import com.anypresence.masterpass_android_library.activities.MPLightBox;
import com.anypresence.masterpass_android_library.dto.Order;
import com.anypresence.masterpass_android_library.dto.PairCheckoutResponse;
import com.anypresence.masterpass_android_library.dto.WebViewOptions;
import com.anypresence.masterpass_android_library.interfaces.IManager;
import com.anypresence.masterpass_android_library.interfaces.OnCompleteCallback;
import com.anypresence.masterpass_android_library.interfaces.ViewController;
import com.anypresence.sdk.master_travel_ecomm.models.User;
import com.mastercard.travel.Application;
import com.mastercard.travel.Constants;
import com.mastercard.travel.R;
import com.mastercard.travel.activity.BaseActivity;
import com.mastercard.travel.util.MPECommerceManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by diego.rotondale on 9/30/2014.
 */
public abstract class BaseFragment extends Fragment implements IManager, ViewController {
    protected String LOG_TAG = this.getClass().getName();
    protected ProgressDialog mConnectionProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mConnectionProgressDialog = new ProgressDialog(getActivity());
        mConnectionProgressDialog.setMessage(getString(R.string.please_wait));
        mConnectionProgressDialog.setCancelable(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    protected void showProgress() {
        mConnectionProgressDialog.setMessage(getString(R.string.please_wait));
        showOnUiThread();
    }

    private void showOnUiThread() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mConnectionProgressDialog.show();
            }
        });
    }

    public void showProgress(String message) {
        mConnectionProgressDialog.setMessage(message);
        showOnUiThread();
    }

    protected void dismissProgress() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mConnectionProgressDialog.dismiss();
            }
        });
    }

    public MPECommerceManager getMPECommerceManager() {
        MPECommerceManager mpeCommerceManager = MPECommerceManager.getInstance();
        mpeCommerceManager.setDelegate(Application.getInstance());
        return mpeCommerceManager;
    }

    public MPManager getMCLibrary() {
        MPManager mpManager = MPManager.getInstance();
        mpManager.setDelegate(this);
        return mpManager;
    }

    @Override
    public String getServerAddress() {
        return Constants.BACKEND_URL;
    }

    @Override
    public Boolean isAppPaired() {
        User user = Application.getInstance().getUser();
        return user.getIsPaired();
    }

    @Override
    public List<String> getSupportedDataTypes() {
        List<String> list = new ArrayList<String>();
        list.add(MPManager.DATA_TYPE_PROFILE);
        list.add(MPManager.DATA_TYPE_ADDRESS);
        list.add(MPManager.DATA_TYPE_CARD);
        return list;
    }

    @Override
    public List<String> getSupportedCardTypes() {
        List<String> list = new ArrayList<String>();
        list.add(MPManager.CARD_TYPE_MAESTRO);
        list.add(MPManager.CARD_TYPE_AMEX);
        list.add(MPManager.CARD_TYPE_DISCOVER);
        list.add(MPManager.CARD_TYPE_MASTERCARD);
        return list;
    }


    @Override
    public void pairingDidComplete(Boolean success, Throwable error) {
        Log.d(LOG_TAG, success ? "ConnectedMasterPass" : "MasterPassConnectionCancelled");
        dismissProgress();
        setPairStatus(success);
    }

    @Override
    public void checkoutDidComplete(Boolean success, Throwable error) {
        Log.d(LOG_TAG, success ? "MasterPassCheckoutComplete" : "MasterPassCheckoutCancelled");
        dismissProgress();
    }


    @Override
    public void preCheckoutDidComplete(Boolean success, PairCheckoutResponse data, Throwable error) {
        Log.d(LOG_TAG, success ? "MasterPassPreCheckoutComplete" : "MasterPassPreCheckoutCancelled");
        dismissProgress();
        setPairStatus(success);
        setPairCheckout(data);
    }


    @Override
    public void pairCheckoutDidComplete(Boolean success, Throwable error) {
        setPairStatus(success);
        dismissProgress();
    }

    @Override
    public void manualCheckoutDidComplete(Boolean success, Throwable error) {
        dismissProgress();
    }

    @Override
    public void resetUserPairing() {
        setPairStatus(false);
    }

    //ViewController
    @Override
    public void presentViewController(Activity activity, Boolean animated, WebViewOptions options) {
        dismissProgress();
        Intent intent = new Intent(getActivity(), activity.getClass());
        Bundle bundle = new Bundle();
        bundle.putString(com.anypresence.masterpass_android_library.Constants.X_SESSION_ID, getXSessionId());
        bundle.putSerializable(com.anypresence.masterpass_android_library.Constants.OPTIONS_PARAMETER, options);
        intent.putExtras(bundle);
        startActivityForResult(intent, options.type.value);
    }

    @Override
    public void dismissViewControllerAnimated(boolean animated, OnCompleteCallback callback) {

    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void runOnUiThread(Runnable runnable) {
        getActivity().runOnUiThread(runnable);
    }

    @Override
    public String getXSessionId() {
        User user = Application.getInstance().getUser();
        return user.getXSessionId();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean status = resultCode == Activity.RESULT_OK;
        Serializable result = null;
        if (data != null)
            result = data.getSerializableExtra(com.anypresence.masterpass_android_library.Constants.LIGHT_BOX_EXTRA);
        if (requestCode == MPLightBox.MPLightBoxType.MPLightBoxTypeConnect.value) {
            getMCLibrary().pairingViewDidCompletePairing(this, status, null);
        }
        if (requestCode == MPLightBox.MPLightBoxType.MPLightBoxTypeCheckout.value) {
            getMCLibrary().lightBoxDidCompleteCheckout(this, status, null);
        }
        if (requestCode == MPLightBox.MPLightBoxType.MPLightBoxTypePreCheckout.value) {
            getMCLibrary().lightBoxDidCompletePreCheckout(this, status, (PairCheckoutResponse) result, null);
        }
    }

    public void setPairStatus(Boolean success) {
        User user = Application.getInstance().getUser();
        user.setIsPaired(success);
    }

    public Toolbar getToolbar() {
        return ((BaseActivity) getActivity()).getToolbar();
    }

    public void setPairCheckout(PairCheckoutResponse pairCheckout) {
        Application application = Application.getInstance();
        application.setPairCheckout(pairCheckout);
        Order order = application.getOrder();
        if (pairCheckout != null && pairCheckout.checkout != null) {
            order.transactionId = pairCheckout.checkout.transactionId;
            order.preCheckoutTransactionId = pairCheckout.checkout.preCheckoutTransactionId;
        }
    }
}
