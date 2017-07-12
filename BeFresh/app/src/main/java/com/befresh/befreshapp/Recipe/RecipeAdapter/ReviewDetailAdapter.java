package com.befresh.befreshapp.Recipe.RecipeAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.befresh.befreshapp.R;
import com.befresh.befreshapp.Recipe.RecipeModel.getRecipeReview;

import java.util.ArrayList;

/**
 * Created by idongsu on 2017. 6. 28..
 */

public class ReviewDetailAdapter extends RecyclerView.Adapter<ReviewDetailViewHolder>
{
    Context context;
    ArrayList<getRecipeReview.RecipeReview> review = new ArrayList<>();
    public ReviewDetailAdapter(Context context, ArrayList<getRecipeReview.RecipeReview> review)
    {
        this.context = context;
        this.review = review;

    }

    @Override
    public ReviewDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_detail_item, parent, false);
        ReviewDetailViewHolder ingreviewholder = new ReviewDetailViewHolder(view); // 뷰홀더를 통해 메모리 할당
        return ingreviewholder;

    }

    @Override
    public void onBindViewHolder(ReviewDetailViewHolder holder, int position)
    {
        holder.content.setText(review.get(position).content);
        holder.writer.setText(review.get(position).writer);
        holder.ratingBar.setRating(review.get(position).score);
    }

    @Override
    public int getItemCount() {
        return review.size();
    }
}
