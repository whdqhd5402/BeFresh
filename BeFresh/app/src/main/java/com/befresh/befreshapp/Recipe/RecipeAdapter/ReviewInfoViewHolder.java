package com.befresh.befreshapp.Recipe.RecipeAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.befresh.befreshapp.R;

import static com.befresh.befreshapp.R.id.comment_content;

/**
 * Created by idongsu on 2017. 7. 2..
 */

public class ReviewInfoViewHolder extends RecyclerView.ViewHolder
{
    TextView writer;
    TextView content;
    RatingBar ratingBar;
    public ReviewInfoViewHolder(View itemView)
    {
        super(itemView);

        writer = (TextView) itemView.findViewById(R.id.comment_email);
        content = (TextView) itemView.findViewById(comment_content);

    }
}
