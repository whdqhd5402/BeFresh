package com.befresh.befreshapp.Recipe;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.befresh.befreshapp.Navigationmain.MasterFragment;
import com.befresh.befreshapp.Navigationmain.NavigationActivity;
import com.befresh.befreshapp.R;
import com.befresh.befreshapp.Recipe.Adpater.HomePagerAdapter;

import static com.befresh.befreshapp.Navigationmain.NavigationActivity.kind1;

/**
 * Created by idongsu on 2017. 6. 26..
 */
public class MainFragment extends MasterFragment
{
    private NavigationActivity mContext;
    public LinearLayout dialog;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private HomePagerAdapter homePagerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mContext = (NavigationActivity)getMasterActivity();
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        tabLayout = (TabLayout)view.findViewById(R.id.hometablayout);
        viewPager = (ViewPager)view.findViewById(R.id.homeviewpager);
        dialog = (LinearLayout)view.findViewById(R.id.dialog);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.main_left_pager));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.main_right_pager));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);




            if(kind1 == 0) {
                viewPager.setCurrentItem(0);
            }
            else {
                viewPager.setCurrentItem(1);
            }


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager.setCurrentItem(tab.getPosition());
                kind1 = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });

        homePagerAdapter = new HomePagerAdapter(getFragmentManager(), tabLayout.getTabCount(), getActivity(), kind1);
        homePagerAdapter.notifyDataSetChanged();
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(homePagerAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        return view;
    }


    @Override
    public void onResume()
    {
        super.onResume();
        if(kind1 == 0) {
            viewPager.setCurrentItem(0);
        }
        else {
            viewPager.setCurrentItem(1);
        }
    }
}
