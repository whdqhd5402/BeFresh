package com.befresh.befreshapp.Recipe.RecipeAdapter;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.befresh.befreshapp.Recipe.PreViewFragment;
import com.befresh.befreshapp.Recipe.PreViewFragment2;
import com.befresh.befreshapp.Recipe.RecipeModel.getRecipeContent;

import java.util.ArrayList;


/**
 * Created by pc on 2017-02-26.
 */

public class RecipeViewPagerAdapter extends FragmentStatePagerAdapter
{
    // Count number of tabs
    private int tabCount;
    public Activity activity;
    private ArrayList<getRecipeContent.urlInfo> preview_image;
    PreViewFragment rank1;
    public RecipeViewPagerAdapter(FragmentManager fm, int tabCount, Activity activity, ArrayList<getRecipeContent.urlInfo> preview_image)
    {
        super(fm);
        this.tabCount = tabCount;
        this.activity = activity;
        this.preview_image = preview_image;
    }



    @Override
    public android.support.v4.app.Fragment getItem(int position)
    {

        // Returning the current tabs
        switch (position)
        {
            case 0:
                PreViewFragment rank1 = new PreViewFragment();
                Bundle image = new Bundle();
                image.putString("image",preview_image.get(0).url);
                rank1.setArguments(image);
                return rank1;
            case 1:
                PreViewFragment2 rank2 = new PreViewFragment2();
                Bundle image2 = new Bundle();
                image2.putString("image2",preview_image.get(1).url);
                rank2.setArguments(image2);
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
