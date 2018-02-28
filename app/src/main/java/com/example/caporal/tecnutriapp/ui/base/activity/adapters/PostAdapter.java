package com.example.caporal.tecnutriapp.ui.base.activity.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.caporal.tecnutriapp.R;
import com.example.caporal.tecnutriapp.domain.entity.Food;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(PostAdapter.MyViewHolder holder, int position) {
        Food food = foodList.get(position);

        holder.foodDescriptionTextView.setText(food.getDescription());
        holder.foodAmountTextView.setText(String.valueOf(food.getAmount()));
        holder.foodMeasureTextView.setText(food.getMeasure());
        holder.foodGramsTextView.setText(String.format(
                context.getString(R.string.food_grams_mask),
                String.valueOf(food.getWeight())));
        holder.energyTextView.setText(String.valueOf(food.getEnergy()));
        holder.carbohydrateTextView.setText(String.valueOf(food.getCarbohydrate()));
        holder.proteinTextView.setText(String.valueOf(food.getProtein()));
        holder.fatTextView.setText(String.valueOf(food.getFat()));

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

        @BindView(R.id.food_description_textview)
        TextView foodDescriptionTextView;
        @BindView(R.id.food_amount_textview)
        TextView foodAmountTextView;
        @BindView(R.id.food_measure_textview)
        TextView foodMeasureTextView;
        @BindView(R.id.food_grams_textview)
        TextView foodGramsTextView;
        @BindView(R.id.energy_text_view)
        TextView energyTextView;
        @BindView(R.id.carbohydrate_text_view)
        TextView carbohydrateTextView;
        @BindView(R.id.protein_text_view)
        TextView proteinTextView;
        @BindView(R.id.fat_text_view)
        TextView fatTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
