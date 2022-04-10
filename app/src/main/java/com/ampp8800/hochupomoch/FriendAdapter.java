package com.ampp8800.hochupomoch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendListItemViewHolder> {

    final LayoutInflater inflater;
    final List<ListItem> friendListItems;
    final Context context;

    FriendAdapter(@NonNull Context context, @NonNull List<ListItem> friendListItems) {
        this.friendListItems = friendListItems;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public FriendListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.friend_item, parent, false);
        return new FriendListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendListItemViewHolder holder, int position) {
        ListItem friend = friendListItems.get(position);
        holder.imageFriendView.setImageResource(FriendListItemViewHolder.targetImageViewFromUrl(friend.getImageViewURL(), context));
        holder.nameView.setText(friend.getName());
    }

    @Override
    public int getItemCount() {
        return friendListItems.size();
    }

}