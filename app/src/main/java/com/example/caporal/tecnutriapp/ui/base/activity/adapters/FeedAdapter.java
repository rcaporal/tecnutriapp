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
import com.example.caporal.tecnutriapp.domain.entity.LikeEvent;
import com.example.caporal.tecnutriapp.domain.entity.Profile;
import com.example.caporal.tecnutriapp.domain.repository.LikePersistenceRepository;
import com.example.caporal.tecnutriapp.ui.base.activity.listeners.OnItemProfileClickListener;
import com.example.caporal.tecnutriapp.ui.base.activity.listeners.OnPostBodyClickListener;
import com.example.caporal.tecnutriapp.utils.DateUtils;
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
    private OnPostBodyClickListener onPostBodyClickListener;
    private String[] mealTypeArray;

    public FeedAdapter(Context context, OnItemProfileClickListener onItemProfileClickListener,
                       OnPostBodyClickListener onPostBodyClickListener) {
        this.context = context;
        this.cardList = new ArrayList<>();
        this.onItemProfileClickListener = onItemProfileClickListener;
        this.onPostBodyClickListener = onPostBodyClickListener;
        this.mealTypeArray = context.getResources().getStringArray(R.array.meal_type_array);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Card card = cardList.get(position);
        final Profile profile = card.getProfile();
        LikeEvent likeEvent = LikePersistenceRepository.getIsLikedByHash(card.getFeedHash());

        if(profile.getName() != null) {
            holder.personNameTextView.setText(profile.getName());
        }

        if(profile.getGeneralGoal() != null) {
            holder.personGoalTextView.setVisibility(View.VISIBLE);
            holder.personGoalTextView.setText(profile.getGeneralGoal());
        }else {
            holder.personGoalTextView.setVisibility(View.GONE);
        }

        if(card.getEnergy() != 0.0f) {
            holder.energyTextView.setVisibility(View.VISIBLE);
            holder.energyTextView.setText(String.format(context.getString(R.string.kcal_mask), String.valueOf(card.getEnergy())));
        }else {
            holder.energyTextView.setVisibility(View.GONE);
        }

        holder.postTimeTextView.setText(DateUtils.getDateFormated(card.getDate()));
        holder.mealTypeTextView.setText(mealTypeArray[card.getMealType()]);

        if(likeEvent != null){
            card.setLiked(likeEvent.isLiked());
        }else {
            card.setLiked(false);
        }

        if(card.isLiked()) {
            holder.likeButton.setImageResource(R.drawable.ic_favorite_red_24dp);
        }else {
            holder.likeButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);
        }

        Picasso.with(holder.postImageView.getContext())
                .load(card.getImage()).placeholder(R.drawable.ic_restaurant_black_24dp)
                .into(holder.postImageView);


        Picasso.with(holder.personImageView.getContext())
                .load(profile.getImage()).placeholder(R.drawable.ic_account_circle_black_24dp)
                .into(holder.personImageView);

        holder.cardHeaderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemProfileClickListener.onItemProfileClick(profile, card);
            }
        });

        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(card.isLiked()) {
                    card.setLiked(false);
                    LikePersistenceRepository.saveOrUpdate(new LikeEvent(card.getFeedHash(), card.isLiked()));
                    holder.likeButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);

                }else {
                    card.setLiked(true);
                    LikePersistenceRepository.saveOrUpdate(new LikeEvent(card.getFeedHash(), card.isLiked()));
                    holder.likeButton.setImageResource(R.drawable.ic_favorite_red_24dp);
                }
            }
        });

        holder.postBodyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPostBodyClickListener.onPostBodyClick(card);
            }
        });
    }

    public void addCardsToListContent(List<Card> cards, boolean clear){
        if(clear){
            cardList.clear();
        }
        cardList.addAll(cards);
        notifyDataSetChanged();
    }

    public List<Card> getCardsList(){
        return this.cardList;
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
        @BindView(R.id.post_body_layout)
        LinearLayout postBodyLayout;
        @BindView(R.id.meal_tipe_text_view)
        TextView mealTypeTextView;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
