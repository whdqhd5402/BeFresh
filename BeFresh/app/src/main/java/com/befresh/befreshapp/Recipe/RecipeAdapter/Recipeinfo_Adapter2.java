package com.befresh.befreshapp.Recipe.RecipeAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.befresh.befreshapp.R;
import com.befresh.befreshapp.Recipe.RecipeModel.getRecipeContent;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by idongsu on 2017. 6. 27..
 */

public class Recipeinfo_Adapter2 extends RecyclerView.Adapter<RecipeDetail2ViewHolder>
{
    Context context;
    ArrayList<getRecipeContent.cardInfo2> card;
    View.OnTouchListener clickListener;
    public Recipeinfo_Adapter2(Context context, ArrayList<getRecipeContent.cardInfo2> card,View.OnTouchListener clickListener)
    {
        this.context = context;
        this.card = card;
        this.clickListener = clickListener;
    }
    @Override
    public RecipeDetail2ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reciptdetail2_item, parent, false);
        RecipeDetail2ViewHolder ingreviewholder = new RecipeDetail2ViewHolder(view); // 뷰홀더를 통해 메모리 할당
        view.setOnTouchListener(clickListener);
        return ingreviewholder;

    }

    @Override
    public void onBindViewHolder(RecipeDetail2ViewHolder holder, int position)
    {
        Glide.with(context).load(card.get(position).image).into(holder.image);
        holder.title.setText(card.get(position).title);
        holder.content.setText(card.get(position).content);
    }

      @Override
    public int getItemCount()
      {
        return card.size();
    }
}
