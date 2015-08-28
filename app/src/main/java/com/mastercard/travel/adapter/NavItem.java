package com.mastercard.travel.adapter;

import android.content.Intent;

import com.mastercard.travel.R;
import com.mastercard.travel.activity.BaseActivity;
import com.mastercard.travel.activity.HomeActivity;
import com.mastercard.travel.activity.ProfileActivity;

/**
 * Created by emi91_000 on 06/02/2015.
 */
public enum NavItem {
    HOME(R.drawable.home, R.string.drawer_home) {
        public void goNextScreen(BaseActivity activity) {
            activity.startActivity(new Intent(activity, HomeActivity.class));
        }
    }, MY_PROFILE(R.drawable.user_white_big, R.string.drawer_my_profile) {
        public void goNextScreen(BaseActivity activity) {
            activity.startActivity(new Intent(activity, ProfileActivity.class));
        }
    };

    private int resTextId;
    private int resImageId;

    private NavItem(int resImageId, int resTextId) {
        this.resTextId = resTextId;
        this.resImageId = resImageId;
    }

    public int getResTextId() {
        return resTextId;
    }

    public void setResTextId(int resTextId) {
        this.resTextId = resTextId;
    }

    public int getResImageId() {
        return resImageId;
    }

    public void setResImageId(int resImageId) {
        this.resImageId = resImageId;
    }

    public void goNextScreen(BaseActivity activity) {

    }
}
