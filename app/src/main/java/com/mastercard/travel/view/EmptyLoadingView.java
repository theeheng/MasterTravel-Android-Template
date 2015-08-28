package com.mastercard.travel.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mastercard.travel.R;


/**
 * Created by diego.rotondale on 1/26/2015.
 * Copyright (c) 2015 AnyPresence, Inc. All rights reserved.
 */
public class EmptyLoadingView extends RelativeLayout {

    private Activity context;
    private int loadingResId = R.string.loading_products;
    private int emptyResId = R.string.empty_products;
    private int errorResId = R.string.error_products;
    private boolean showLogo = true;

    public EmptyLoadingView(Context context) {
        super(context);
        init(context);
    }

    public EmptyLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EmptyLoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.context = (Activity) context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_empty_loading, this);
        if (!isInEditMode()) {
            createHolder(this);
            setLoadingView();
        }
    }

    private void createHolder(View view) {
        EmptyLoadingHolder holder = new EmptyLoadingHolder();
        holder.progress = view.findViewById(R.id.empty_progress);
        holder.emptyText = (TextView) view.findViewById(R.id.empty_text);
        holder.logo = view.findViewById(R.id.empty_logo);
        holder.separator = view.findViewById(R.id.separator);
        view.setTag(holder);
    }

    private void setLoadingView() {
        setText(loadingResId, true);
    }

    public void setEmptyView() {
        setText(emptyResId, false);
    }

    public void setErrorView() {
        setText(errorResId, false);
    }

    private void setText(final int textResource, final Boolean progressVisibility) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EmptyLoadingHolder holder = (EmptyLoadingHolder) EmptyLoadingView.this.getTag();
                if (!progressVisibility)
                    holder.progress.setVisibility(View.GONE);
                holder.emptyText.setText(textResource);
            }
        });
    }

    public void hideLogo() {
        EmptyLoadingHolder holder = (EmptyLoadingHolder) EmptyLoadingView.this.getTag();
        holder.logo.setVisibility(View.GONE);
        holder.separator.setVisibility(View.GONE);
    }

    public int getLoadingResId() {
        return loadingResId;
    }

    public void setLoadingResId(int loadingResId) {
        this.loadingResId = loadingResId;
    }

    public int getEmptyResId() {
        return emptyResId;
    }

    public void setEmptyResId(int emptyResId) {
        this.emptyResId = emptyResId;
    }

    public int getErrorResId() {
        return errorResId;
    }

    public void setErrorResId(int errorResId) {
        this.errorResId = errorResId;
    }

    public class EmptyLoadingHolder {
        public View progress;
        public TextView emptyText;
        public View logo;
        public View separator;
    }
}
