package com.bkset.vutuan.wisun.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by vutuan on 02/05/2018.
 */

public class CustomAdapterViewPager extends FragmentPagerAdapter {
    private Context context;
    private ArrayList<Fragment> list;

    public CustomAdapterViewPager(FragmentManager fm, Context context, ArrayList<Fragment> list) {
        super(fm);
        this.context = context;
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
