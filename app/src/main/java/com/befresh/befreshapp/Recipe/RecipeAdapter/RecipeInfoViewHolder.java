package com.befresh.befreshapp.Recipe.RecipeAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.befresh.befreshapp.R;

/**
 * Created by idongsu on 2017. 6. 27..
 */

public class RecipeInfoViewHolder extends RecyclerView.ViewHolder
{
    ImageView image;
    TextView title;

    public RecipeInfoViewHolder(View itemView){

        super(itemView);
        image = (ImageView)itemView.findViewById(R.id.image);
        title = (TextView) itemView.findViewById(R.id.title);
    }
}
