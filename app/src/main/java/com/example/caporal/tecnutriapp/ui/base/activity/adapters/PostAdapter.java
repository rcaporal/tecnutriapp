package com.example.caporal.tecnutriapp.ui.base.activity.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.caporal.tecnutriapp.domain.entity.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caporal on 27/02/18.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private List<Food> foodList;
    private Context context;

    public PostAdapter(Context context) {
        this.context = context;
        this.foodList = new ArrayList<>();
    }

    @Override
    public PostAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(PostAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public void setFoodList(List<Food> foods){
        foodList.clear();
        foodList.addAll(foods);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
