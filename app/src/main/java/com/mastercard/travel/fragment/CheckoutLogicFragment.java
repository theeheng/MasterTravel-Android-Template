package com.mastercard.travel.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.anypresence.masterpass_android_library.dto.CreditCard;
import com.anypresence.masterpass_android_library.dto.Order;
import com.anypresence.masterpass_android_library.dto.PairCheckoutResponse;
import com.anypresence.masterpass_android_library.dto.PreCheckoutResponse;
import com.anypresence.masterpass_android_library.dto.Wallet;
import com.anypresence.masterpass_android_library.interfaces.FutureCallback;
import com.mastercard.travel.Application;
import com.mastercard.travel.Constants;
import com.mastercard.travel.R;
import com.mastercard.travel.activity.FlightDetailActivity;
import com.mastercard.travel.activity.FlightSuccessActivity;
import com.mastercard.travel.activity.HotelSuccessActivity;
import com.mastercard.travel.adapter.CardAdapter;
import com.mastercard.travel.util.CreditCardUtil;
import com.mastercard.travel.view.EmptyLoadingView;

import java.util.List;

/**
 * Created by diego.rotondale on 26/02/2015.
 */
public class CheckoutLogicFragment extends BaseFragment {

    private ListView cardList;
    private FutureCallback<PreCheckoutResponse> preCheckoutCallback = new FutureCallback<PreCheckoutResponse>() {
        @Override
        public void onSuccess(PreCheckoutResponse preCheckoutResponse) {
            CheckoutLogicFragment.this.setPairStatus(true);
            Application.getInstance().setPreCheckout(preCheckoutResponse);
            Order order = Application.getInstance().getOrder();
            order.preCheckoutTransactionId = preCheckoutResponse.walletInfo.preCheckoutTransactionId;
            order.walletInfo = preCheckoutResponse.walletInfo;
            order.card = preCheckoutResponse.getDefaultCard();
            order.shippingAddress = preCheckoutResponse.getDefaultAddress();
            order.transactionId = preCheckoutResponse.transactionId;
            showCardData(preCheckoutResponse.cards, preCheckoutResponse.walletInfo);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pay.setVisibility(View.VISIBLE);
                }
            });
        }

        @Override
        public void onFailure(Throwable throwable) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    emptyView.setErrorView();
                    pay.setVisibility(View.GONE);
                }
            });
        }
    };
    private EmptyLoadingView emptyView;
    private View pay;
    private CardAdapter cardAdapter;
    private View confirmBook;
    private View cardPair;
    private View cardSelection;
    private View cardAdd;
    private View or;
    private AdapterView.OnItemClickListener onCardClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            cardAdapter.setCardPositionChecked(position);
        }
    };

    private void showCardData(final List<CreditCard> cards, final Wallet walletInfo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cardAdapter.setWalletInfo(walletInfo);
                cardAdapter.addAll(cards);
                emptyView.setEmptyView();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_checkout_logic, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initControls();

        pay = getView().findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order order = Application.getInstance().getOrder();
                if (isAppPaired()) {
                    order.card = cardAdapter.getCardSelected();
                    showProgress(getString(R.string.processing));
                    getMCLibrary().returnCheckout(order, (BaseFragment) getParentFragment());
                } else {
                    showProgress(getString(R.string.please_wait));
                    getMCLibrary().pairCheckoutForOrder(order, (BaseFragment) getParentFragment());
                }
            }
        });

        confirmBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress(getString(R.string.processing));
                getMCLibrary().completePairCheckoutForOrder(Application.getInstance().getOrder(), (BaseFragment) getParentFragment());
            }
        });

        if (isAppPaired()) {
            pay.setVisibility(View.GONE);
            getMCLibrary().preCheckout(this, preCheckoutCallback);
        }
    }

    private void initControls() {
        cardSelection = getView().findViewById(R.id.card_selection);
        cardAdd = getView().findViewById(R.id.cart_add);
        or = getView().findViewById(R.id.or);
        if (isAppPaired()) {
            cardList = (ListView) getView().findViewById(R.id.cards);
            emptyView = (EmptyLoadingView) getView().findViewById(R.id.empty_loading_view);
            emptyView.hideLogo();
            emptyView.setErrorResId(R.string.empty_cards);
            emptyView.setErrorResId(R.string.error_cards);
            cardList.setEmptyView(emptyView);
            cardAdapter = new CardAdapter(getActivity());
            cardList.setAdapter(cardAdapter);
            cardList.setOnItemClickListener(onCardClickListener);
            cardSelection.setVisibility(View.VISIBLE);
            cardAdd.setVisibility(View.GONE);
            or.setVisibility(View.GONE);
        } else {
            cardSelection.setVisibility(View.GONE);
            or.setVisibility(View.VISIBLE);
            cardAdd.setVisibility(View.VISIBLE);
        }
        confirmBook = getView().findViewById(R.id.cart_confirm_book);
        confirmBook.setVisibility(View.GONE);
        cardPair = getView().findViewById(R.id.card_pair);
        cardPair.setVisibility(View.GONE);
    }

    @Override
    public void preCheckoutDidComplete(Boolean success, PairCheckoutResponse data, Throwable error) {
        dismissProgress();
        if (success) {
            confirmBook.setVisibility(View.VISIBLE);
            cardPair.setVisibility(View.VISIBLE);
            cardSelection.setVisibility(View.GONE);
            or.setVisibility(View.GONE);
            cardAdd.setVisibility(View.GONE);
            pay.setVisibility(View.GONE);
            getView().findViewById(R.id.card_rb).setVisibility(View.GONE);
            getView().findViewById(R.id.card_masterpass).setVisibility(View.VISIBLE);
            CreditCard card = data.checkout.card;
            if (card != null) {
                Order order = Application.getInstance().getOrder();
                order.card = card;
                ((TextView) getView().findViewById(R.id.card_last_number)).setText(card.lastFour);
                int cardImage = CreditCardUtil.cardImageForCardType(card.brandId, card.lastFour);
                ((ImageView) getView().findViewById(R.id.card_image)).setImageResource(cardImage);
            }
        }
    }

    @Override
    public void checkoutDidComplete(Boolean success, Throwable error) {
        super.checkoutDidComplete(success, error);
        if (success) {
            Bundle bundle = getArguments();
            Intent intent = null;
            if(bundle.getSerializable(FlightDetailActivity.FLIGHT_EXTRA)!=null){
                intent = new Intent(getActivity(), FlightSuccessActivity.class);
            }else{
                intent = new Intent(getActivity(), HotelSuccessActivity.class);
            }
            intent.putExtra(Constants.MASTERPASS, true);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    public void pairCheckoutDidComplete(Boolean success, Throwable error) {
        super.pairCheckoutDidComplete(success, error);
        if (success) {
            Bundle bundle = getArguments();
            Intent intent = null;
            if(bundle.getSerializable(FlightDetailActivity.FLIGHT_EXTRA)!=null){
                intent = new Intent(getActivity(), FlightSuccessActivity.class);
            }else{
                intent = new Intent(getActivity(), HotelSuccessActivity.class);
            }
            intent.putExtra(Constants.MASTERPASS, false);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    public void manualCheckoutDidComplete(Boolean success, Throwable error) {
        super.manualCheckoutDidComplete(success,error);
        if (success) {
            Bundle bundle = getArguments();
            Intent intent = null;
            if(bundle.getSerializable(FlightDetailActivity.FLIGHT_EXTRA)!=null){
                intent = new Intent(getActivity(), FlightSuccessActivity.class);
            }else{
                intent = new Intent(getActivity(), HotelSuccessActivity.class);
            }
            intent.putExtra(Constants.MASTERPASS, false);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
