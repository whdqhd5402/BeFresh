package com.befresh.befreshapp.Recipe.RecipeAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.befresh.befreshapp.R;

/**
 * Created by idongsu on 2017. 7. 2..
 */

public class RecipeDetail2ViewHolder extends RecyclerView.ViewHolder
{
    ImageView image;
    TextView title;
    TextView content;

    public RecipeDetail2ViewHolder(View itemView){

        super(itemView);
        image = (ImageView)itemView.findViewById(R.id.card_image);
        title = (TextView) itemView.findViewById(R.id.card_title);
        content = (TextView) itemView.findViewById(R.id.card_content);
    }
}
