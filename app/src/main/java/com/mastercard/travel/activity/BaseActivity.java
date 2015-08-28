package com.mastercard.travel.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mastercard.travel.Application;
import com.mastercard.travel.R;
import com.mastercard.travel.fragment.BaseFragment;
import com.mastercard.travel.util.MPECommerceManager;

/**
 * Created by emi91_000 on 03/02/2015.
 */
public abstract class BaseActivity extends ActionBarActivity {

    protected String LOG_TAG = this.getClass().getName();
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(getLayoutResId() != 0 ? getLayoutResId() : R.layout.activity_main);
        overridePendingTransition(R.anim.get_in, R.anim.get_out);
        if (this.getFragmentClass() != null) {

            BaseFragment fragment = null;
            try {
                fragment = this.getFragmentClass().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            Bundle bundle = this.getIntent().getExtras();
            if (bundle != null) {
                Log.e(this.getClass().toString(), "BUNDLE IS NOT NULL: " + bundle.keySet().toArray()[0]);
                Log.e(this.getClass().toString(), "BUNDLE VALUE: " + bundle.get("profile"));
                Log.e(this.getClass().toString(), "BUNDLE IS NOT NULL SIZE: " + bundle.size());
                fragment.setArguments(bundle);
            }

            if (savedInstanceState == null) {
                FragmentTransaction fragmentTransaction = this
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragment,
                                fragment.getClass().getName());
                this.commit(fragmentTransaction, false, fragment.getClass()
                        .getName());
            }
        }
        toolbar = (Toolbar) findViewById(R.id.material_toolbar);
    }

    protected void setTitleIfNeeded() {
        if (getActionBarTitle() != 0) {
            toolbar.findViewById(R.id.commonTitle).setVisibility(View.VISIBLE);
            ((TextView) toolbar.findViewById(R.id.commonTitle)).setText(getActionBarTitle());
            toolbar.findViewById(R.id.titleAndSubtitleContainer).setVisibility(View.GONE);
            toolbar.findViewById(R.id.iconInToolBar).setVisibility(View.GONE);
        }
    }

    protected void createToolBar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            setTitleIfNeeded();
            getSupportActionBar().setTitle(0);
        }
    }

    protected void createToolBarWithBack() {
        if (toolbar != null) {
            createToolBar();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
            getSupportActionBar().setHomeButtonEnabled(true);
            //getSupportActionBar().setDisplayShowTitleEnabled(true);
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            //getSupportActionBar().setDisplayShowCustomEnabled(true);
            setTitleIfNeeded();
        }
    }

    public void replaceFragment(Class<? extends BaseFragment> clazz) {
        replaceFragment(clazz, null);
    }

    public void replaceFragment(Class<? extends BaseFragment> clazz, Bundle args) {
        BaseFragment fragment = null;
        try {
            fragment = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (args != null) {
            fragment.setArguments(args);
        }

        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.container, fragment,
                        fragment.getClass().getName());
        this.commit(fragmentTransaction, false, fragment.getClass()
                .getName());

    }

    public void replaceWithFlipAnimation(Class<? extends BaseFragment> clazz, Bundle args) {
        BaseFragment fragment = null;
        try {
            fragment = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (args != null) {
            fragment.setArguments(args);
        }

        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager()
                .beginTransaction().setCustomAnimations(R.anim.card_flip_left_in,
                        R.anim.card_flip_left_out,
                        R.anim.card_flip_left_in,
                        R.anim.card_flip_left_out)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.container, fragment,
                        fragment.getClass().getName());
        this.commit(fragmentTransaction, false, fragment.getClass()
                .getName());
    }

    public abstract Class<? extends BaseFragment> getFragmentClass();

    protected BaseFragment getMainFragment() {
        return (BaseFragment) getSupportFragmentManager().findFragmentByTag(
                getFragmentClass().getName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (getMainFragment() != null)
            getMainFragment().onActivityResult(requestCode, resultCode, data);
    }

    protected void commit(FragmentTransaction fragmentTransaction,
                          boolean addToBackStack, String tag) {
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(tag);
        }
        fragmentTransaction.commit();
    }

    protected int getLayoutResId() {
        return 0;
    }

    protected int getActionBarTitle() {
        return 0;
    }


    public MPECommerceManager getMPECommerceManager() {
        MPECommerceManager mpeCommerceManager = MPECommerceManager.getInstance();
        mpeCommerceManager.setDelegate(Application.getInstance());
        return mpeCommerceManager;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.get_out_back);
    }

    public void setTitleAndSubtitle(int title, int subtitle) {
        ((TextView) toolbar.findViewById(R.id.title)).setText(R.string.find);
        ((ViewGroup) toolbar.findViewById(R.id.titleAndSubtitleContainer)).setVisibility(View.VISIBLE);
    }

    public void showTextIconInActionBar() {
        toolbar.findViewById(R.id.iconInToolBar).setVisibility(View.VISIBLE);
        toolbar.findViewById(R.id.launcher).setVisibility(View.GONE);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
