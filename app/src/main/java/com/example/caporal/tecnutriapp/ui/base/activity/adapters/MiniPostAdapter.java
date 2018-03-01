package com.example.caporal.tecnutriapp.ui.base.activity.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.caporal.tecnutriapp.R;
import com.example.caporal.tecnutriapp.domain.entity.MiniPost;
import com.example.caporal.tecnutriapp.ui.base.activity.listeners.OnMiniPostItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caporal on 01/03/18.
 */

public class MiniPostAdapter extends RecyclerView.Adapter<MiniPostAdapter.ViewHolder> {

    private Context context;
    private List<MiniPost> miniPostList;
    private OnMiniPostItemClickListener onMiniPostItemClickListener;

    public MiniPostAdapter(Context context, OnMiniPostItemClickListener onMiniPostItemClickListener) {
        this.context = context;
        this.miniPostList = new ArrayList<>();
        this.onMiniPostItemClickListener = onMiniPostItemClickListener;
    }

    @Override
    public MiniPostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mini_post, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MiniPostAdapter.ViewHolder holder, int position) {
        final MiniPost miniPost = miniPostList.get(position);

        Picasso.with(holder.miniPostImageView.getContext())
                .load(miniPost.getImage()).placeholder(R.drawable.ic_restaurant_black_24dp)
                .into(holder.miniPostImageView);

        holder.miniPostImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMiniPostItemClickListener.onMiniPostItemClick(miniPost);
            }
        });
    }

    @Override
    public int getItemCount() {
        return miniPostList.size();
    }

    public void addMiniPostList(List<MiniPost> miniPosts, boolean clear){
        if(clear){
            miniPostList.clear();
        }
        miniPostList.addAll(miniPosts);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.mini_post_image)
        ImageView miniPostImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
