package com.befresh.befreshapp.Recipe.RecipeAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.befresh.befreshapp.R;

/**
 * Created by idongsu on 2017. 6. 28..
 */

public class ReviewDetailViewHolder extends RecyclerView.ViewHolder
{
    TextView writer;
    RatingBar ratingBar;
    TextView content;

    public ReviewDetailViewHolder(View itemView){

        super(itemView);
        content = (TextView) itemView.findViewById(R.id.detail_review_content);
        writer = (TextView) itemView.findViewById(R.id.detail_review_writer);
        ratingBar = (RatingBar) itemView.findViewById(R.id.detail_review_rating);
    }
}
