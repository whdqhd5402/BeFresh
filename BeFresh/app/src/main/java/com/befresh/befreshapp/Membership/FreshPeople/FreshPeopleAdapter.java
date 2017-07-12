package com.befresh.befreshapp.Membership.FreshPeople;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.befresh.befreshapp.R;

import java.util.ArrayList;

/**
 * Created by student on 2017-06-29.
 */

public class FreshPeopleAdapter extends RecyclerView.Adapter<FreshPeopleVH> {

    Context context;
    ArrayList<FreshPeopleItem> items = new ArrayList<>();

    public FreshPeopleAdapter(Context context, ArrayList<FreshPeopleItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public FreshPeopleVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_item_fresh_people, parent, false);
        return new FreshPeopleVH(view);
    }

    @Override
    public void onBindViewHolder(FreshPeopleVH holder, int position) {
        holder.img.setImageResource(items.get(position).img);
        holder.deliveryDate.setText(items.get(position).deliveryDate);
        holder.deliveryMenu.setText(items.get(position).deliveryMenu);
        holder.tag.setText(items.get(position).tag);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
