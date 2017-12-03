package com.befresh.befreshapp.Recipe.RecipeAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.befresh.befreshapp.R;
import com.befresh.befreshapp.Recipe.RecipeModel.getRecipeContent;

import java.util.ArrayList;

/**
 * Created by idongsu on 2017. 6. 27..
 */

public class RecipeComment_Adapter extends RecyclerView.Adapter<ReviewInfoViewHolder>
{
    ArrayList<getRecipeContent.ReviewInfo> reviewinfo = new ArrayList<>();
    Context context;
    public RecipeComment_Adapter(Context context, ArrayList<getRecipeContent.ReviewInfo> reviewinfo)
    {
        this.context = context;
        this.reviewinfo = reviewinfo;
    }


    @Override
    public ReviewInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment, parent, false);
        ReviewInfoViewHolder ingreviewholder = new ReviewInfoViewHolder(view); // 뷰홀더를 통해 메모리 할당
        return ingreviewholder;
    }

    @Override
    public void onBindViewHolder(ReviewInfoViewHolder holder, int position)
    {
        holder.writer.setText(reviewinfo.get(position).writer);
        holder.content.setText(reviewinfo.get(position).content);
//        holder.ratingBar.setRating((reviewinfo.get(position).score));
    }


    @Override
    public int getItemCount()
    {
        return reviewinfo.size();
    }
}
