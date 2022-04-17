package com.ampp8800.hochupomoch;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


public class FriendListItemViewHolder extends RecyclerView.ViewHolder {
    final ImageView imageFriendView;
    final TextView nameView;
    private static View view;


    FriendListItemViewHolder(@NonNull View view) {
        super(view);
        this.view = view;
        imageFriendView = view.findViewById(R.id.civ_friend);
        nameView = view.findViewById(R.id.tv_name_friend);
    }

    public int targetImageViewFromUrl(@NonNull String imageViewURL) {
        Glide
                .with(view.getContext())
                .load(imageViewURL)
                .into((ImageView) view.findViewById(R.id.civ_friend));
        return R.drawable.ic_no_photo;
    }

    public void bind(@NonNull ListItem friend) {
        imageFriendView.setImageResource(targetImageViewFromUrl(friend.getImageViewURL()));
        nameView.setText(friend.getName());
    }

}
