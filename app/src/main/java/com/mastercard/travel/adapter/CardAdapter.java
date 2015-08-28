package com.mastercard.travel.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.anypresence.masterpass_android_library.dto.CreditCard;
import com.anypresence.masterpass_android_library.dto.Wallet;
import com.mastercard.travel.Application;
import com.mastercard.travel.R;
import com.mastercard.travel.util.CreditCardUtil;
import com.mastercard.travel.view.LogoImage;

/**
 * Created by diego.rotondale on 25/02/2015.
 */
public class CardAdapter extends ArrayAdapter<CreditCard> {

    private Context context;

    private int cardPositionChecked = 0;
    private Wallet walletInfo;

    public CardAdapter(Context context) {
        super(context, R.layout.item_card);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CardItemHolder holder;
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            holder = new CardItemHolder();
            view = inflater.inflate(R.layout.item_card, parent, false);
            holder.rb = (RadioButton) view.findViewById(R.id.card_rb);
            holder.card = (ImageView) view.findViewById(R.id.card_image);
            holder.lastNumber = (TextView) view.findViewById(R.id.card_last_number);
            holder.logoContainer = view.findViewById(R.id.card_logo_container);
            holder.logoMasterPass = (LogoImage) view.findViewById(R.id.card_logo_masterpass);
            holder.logoPartner = (LogoImage) view.findViewById(R.id.card_logo_partner);
            holder.masterPass = view.findViewById(R.id.card_masterpass);
            view.setTag(holder);
        } else {
            holder = (CardItemHolder) view.getTag();
        }
        CreditCard item = getItem(position);
        holder.rb.setChecked(position == cardPositionChecked);
        holder.card.setImageResource(CreditCardUtil.cardImageForCardType(item.brandId, item.lastFour));
        holder.lastNumber.setText(item.lastFour);
        holder.logoContainer.setVisibility(walletInfo != null ? View.VISIBLE : View.GONE);
        if (walletInfo != null) {
            ImageLoader imageLoader = Application.getInstance().getImageLoader();
            holder.logoMasterPass.setImageUrl(walletInfo.masterpassLogoUrl, imageLoader);
            holder.logoPartner.setImageUrl(walletInfo.walletPartnerLogoUrl, imageLoader);
        }
        return view;
    }

    public Wallet getWalletInfo() {
        return walletInfo;
    }

    public void setWalletInfo(Wallet walletInfo) {
        this.walletInfo = walletInfo;
    }


    public CreditCard getCardSelected() {
        return getItem(cardPositionChecked);
    }

    public void setCardPositionChecked(int cardPositionChecked) {
        this.cardPositionChecked = cardPositionChecked;
        this.notifyDataSetChanged();
    }

    private static class CardItemHolder {
        RadioButton rb;
        ImageView card;
        TextView lastNumber;
        LogoImage logoMasterPass;
        LogoImage logoPartner;
        View logoContainer;
        View masterPass;
    }
}
