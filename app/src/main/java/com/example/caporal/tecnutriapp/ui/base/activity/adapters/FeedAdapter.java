package com.example.caporal.tecnutriapp.ui.base.activity.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.caporal.tecnutriapp.R;
import com.example.caporal.tecnutriapp.domain.entity.Card;
import com.example.caporal.tecnutriapp.domain.entity.Profile;
import com.example.caporal.tecnutriapp.ui.base.activity.listeners.OnItemProfileClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by caporal on 22/02/18.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder> {

    private Context context;
    private List<Card> cardList;
    private OnItemProfileClickListener onItemProfileClickListener;

    public FeedAdapter(Context context, OnItemProfileClickListener onItemProfileClickListener) {
        this.context = context;
        this.cardList = new ArrayList<>();
        this.onItemProfileClickListener = onItemProfileClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Card card = cardList.get(position);
        final Profile profile = card.getProfile();

        holder.personNameTextView.setText(profile.getName());
        holder.personGoalTextView.setText(profile.getGeneral_goal());
        holder.postTimeTextView.setText(card.getDate());
        holder.energyTextView.setText(String.valueOf(card.getEnergy()));
        holder.likeButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);

        Picasso.with(holder.postImageView.getContext())
                .load(card.getImage()).placeholder(R.drawable.ic_restaurant_black_24dp)
                .into(holder.postImageView);


        Picasso.with(holder.personImageView.getContext())
                .load(profile.getImage()).placeholder(R.drawable.ic_account_circle_black_24dp)
                .into(holder.personImageView);

        holder.cardHeaderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemProfileClickListener.onItemProfileClick(profile);
            }
        });

        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.likeButton.setImageResource(R.drawable.ic_favorite_red_24dp);
            }
        });
    }

    public void setCardListContent(List<Card> cards, boolean reload){
        if(reload) {
            cardList.clear();
        }
        cardList.addAll(cards);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.person_name)
        TextView personNameTextView;
        @BindView(R.id.person_goal)
        TextView personGoalTextView;
        @BindView(R.id.person_profile_image)
        CircleImageView personImageView;
        @BindView(R.id.card_header_layout)
        LinearLayout cardHeaderLayout;
        @BindView(R.id.post_photo)
        ImageView postImageView;
        @BindView(R.id.like_btn)
        ImageView likeButton;
        @BindView(R.id.energy_text)
        TextView energyTextView;
        @BindView(R.id.post_timestamp)
        TextView postTimeTextView;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
