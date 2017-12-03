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

public class Recipeinfo_Adapter extends RecyclerView.Adapter<RecipeInfoViewHolder>
{
    ArrayList<getRecipeContent.cardInfo2> card = new ArrayList<>();
    Context context;
    public Recipeinfo_Adapter(Context context,ArrayList<getRecipeContent.cardInfo2> card)
    {
        this.context = context;
        this.card = card;
    }
    @Override
    public RecipeInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.preview_fragment, parent, false);
        RecipeInfoViewHolder ingreviewholder = new RecipeInfoViewHolder(view); // 뷰홀더를 통해 메모리 할당
        return ingreviewholder;

    }

    @Override
    public void onBindViewHolder(RecipeInfoViewHolder holder, int position)
    {
        // 아래 에러
        if(card.size() > 0) {
            Glide.with(context).load(card.get(position).image).into(holder.image);

            holder.title.setText(card.get(position).title);
        }
    }

      @Override
    public int getItemCount()
      {
        return card.size();
    }
}
