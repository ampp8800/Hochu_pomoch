package com.ampp8800.hochupomoch.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ampp8800.hochupomoch.data.ListItem;
import com.ampp8800.hochupomoch.R;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendListItemViewHolder> {

    final LayoutInflater inflater;
    final List<ListItem> friendListItems;

    FriendAdapter(@NonNull Context context, @NonNull List<ListItem> friendListItems) {
        this.friendListItems = friendListItems;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    @NonNull
    public FriendListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.friend_item, parent, false);
        return new FriendListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendListItemViewHolder holder, int position) {
        holder.bind(friendListItems.get(position));
    }

    @Override
    public int getItemCount() {
        return friendListItems.size();
    }

}