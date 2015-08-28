package com.mastercard.travel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emi91_000 on 14/02/2015.
 */
public class DetailFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;

    private List<String> titles;

    public DetailFragmentPagerAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        fragments = new ArrayList<>();
        this.titles = titles;
    }

    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
