package com.befresh.befreshapp.Recipe.Adpater;


import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.befresh.befreshapp.Recipe.MainLeftFragment;
import com.befresh.befreshapp.Recipe.MainRightFragment;


/**
 * Created by pc on 2017-02-26.
 */

public class HomePagerAdapter extends FragmentStatePagerAdapter
{
    // Count number of tabs
    private int tabCount;
    public Activity activity;
    public int kind;

    public HomePagerAdapter(FragmentManager fm, int tabCount, Activity activity,int kind)
    {
        super(fm);
        this.tabCount = tabCount;
        this.activity = activity;
        this.kind = kind;
    }



    @Override
    public android.support.v4.app.Fragment getItem(int position)
    {
        // Returning the current tabs

        switch (position)
        {
            case 0:
                MainLeftFragment rank1 = new MainLeftFragment();
                return rank1;
            case 1:
                MainRightFragment rank2 = new MainRightFragment();
                return rank2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public int getItemPosition(Object object)
    {
        return super.getItemPosition(object);
    }


}
