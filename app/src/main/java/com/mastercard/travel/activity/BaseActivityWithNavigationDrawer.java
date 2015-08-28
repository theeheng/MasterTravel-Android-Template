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
public abstract class BaseActivityWithNavigationDrawer extends BaseActivity {
    protected DrawerLayout drawerLayout;
    protected ListView listView;
    protected NavItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_side_bar_drawer_layout);
        listView = (ListView) findViewById(R.id.activity_side_bar_list_view);
        createToolBarWithBack();
        setupSideBar();
        initNavItemList();
    }

    protected void initNavItemList() {
        adapter = new NavItemAdapter(this, NavItem.values());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.getItem(position).goNextScreen(BaseActivityWithNavigationDrawer.this);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_navigation_drawer;
    }

    protected void setupSideBar() {
        if (toolbar != null) {
            drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);
//            toolbar.setNavigationIcon(R.drawable.ic_action_navigation_menu);
        }

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.START);
        } else {
            super.onBackPressed();
        }
    }
}
