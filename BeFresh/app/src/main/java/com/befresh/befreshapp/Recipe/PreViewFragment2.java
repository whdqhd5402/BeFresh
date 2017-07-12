package com.befresh.befreshapp.Recipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.befresh.befreshapp.Navigationmain.MasterFragment;
import com.befresh.befreshapp.Navigationmain.NavigationActivity;
import com.befresh.befreshapp.R;
import com.bumptech.glide.Glide;

/**
 * Created by idongsu on 2017. 6. 27..
 */

public class PreViewFragment2 extends MasterFragment
{
    private NavigationActivity mContext;
    ImageView imageview;
    String image;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.i("mytag","start home fragment");
        mContext = (NavigationActivity)getMasterActivity();
        View view = inflater.inflate(R.layout.preview_fragment2, container, false);
        imageview = (ImageView)view.findViewById(R.id.imageview);
        image = getArguments().getString("image2");
        Glide.with(mContext).load(image).into(imageview);
        return view;
    }




    @Override
    public void onResume() {
        super.onResume();

    }

}
