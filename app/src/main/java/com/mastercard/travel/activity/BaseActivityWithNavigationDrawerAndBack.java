package com.mastercard.travel.activity;

import android.view.MenuItem;

import android.os.Bundle;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.mastercard.travel.R;

/**
 * Created by emi91_000 on 03/02/2015.
 */
public abstract class BaseActivityWithNavigationDrawerAndBack extends BaseActivityWithNavigationDrawer {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createToolBarWithBack();
    }

    @Override
    protected void setupSideBar() {
        super.setupSideBar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return true;
    }
}
