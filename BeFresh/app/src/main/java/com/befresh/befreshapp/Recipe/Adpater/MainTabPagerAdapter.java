package com.befresh.befreshapp.Recipe.Adpater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.befresh.befreshapp.Recipe.MainLeftFragment;
import com.befresh.befreshapp.Recipe.MainRightFragment;

/**
 * Created by JongBong on 2017-06-25.
 */

public class MainTabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public MainTabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new MainLeftFragment();
            case 1:
                return new MainRightFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
