package com.befresh.befreshapp.Community.MyRecipe;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.befresh.befreshapp.R;

/**
 * Created by JongBong on 2017-06-28.
 */

public class MyRecipeVH extends RecyclerView.ViewHolder {

    int id;
    ImageView img;
    TextView title;
    CheckBox like;

    public MyRecipeVH(View itemView) {
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.img_item_my_recipe);
        title = (TextView) itemView.findViewById(R.id.title_item_my_recipe);
        like = (CheckBox) itemView.findViewById(R.id.like_my_recipe);
    }
}
