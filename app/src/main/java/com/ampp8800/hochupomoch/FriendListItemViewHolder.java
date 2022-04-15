package com.ampp8800.hochupomoch;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


public class FriendListItemViewHolder extends RecyclerView.ViewHolder {
    final ImageView imageFriendView;
    final TextView nameView;
    private static View view;


    FriendListItemViewHolder(View view) {
        super(view);
        this.view = view;
        imageFriendView = view.findViewById(R.id.civ_friend);
        nameView = view.findViewById(R.id.tv_name_friend);
    }

    public int targetImageViewFromUrl(@NonNull String imageViewURL, @NonNull Context context) {
        Glide
                .with(context)
                .load(imageViewURL)
                .into((ImageView) view.findViewById(R.id.civ_friend));
        return R.drawable.ic_no_photo;
    }

    public void bind(List<ListItem> friendListItems, int position, Context context) {
        ListItem friend = friendListItems.get(position);
        imageFriendView.setImageResource(targetImageViewFromUrl(friend.getImageViewURL(), context));
        nameView.setText(friend.getName());
    }

}
