package com.mastercard.travel.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mastercard.travel.R;
import com.mastercard.travel.adapter.NavItem;
import com.mastercard.travel.adapter.NavItemAdapter;

/**
 * Created by emi91_000 on 03/02/2015.
 */
public abstract class BaseActivityWithNavigationDrawerWithToggle extends BaseActivityWithNavigationDrawer {
    protected ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_side_bar_drawer_layout);
        listView = (ListView) findViewById(R.id.activity_side_bar_list_view);
        createToolBarWithBack();
        setupSideBar();
        initNavItemList();
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_navigation_drawer;
    }

    protected void setupSideBar() {
        super.setupSideBar();

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
//                drawerToggle.setDrawerIndicatorEnabled(true);
                supportInvalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
//                drawerToggle.setDrawerIndicatorEnabled(false);
                supportInvalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }else{
            switch (item.getItemId()) {
                case android.R.id.home:
                    onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}
