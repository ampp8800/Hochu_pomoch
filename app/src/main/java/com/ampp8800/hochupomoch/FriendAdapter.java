package com.ampp8800.hochupomoch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendListItemViewHolder> {

    private final LayoutInflater inflater;
    private final List<ListItem> friendListItems;
    private final Context context;

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
        holder.imageFriendView.setImageResource(friend.getImageResource());
        holder.nameView.setText(friend.getName());
    }

    @Override
    public int getItemCount() {
        return friendListItems.size();
    }

    public class FriendListItemViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageFriendView;
        final TextView nameView;


        FriendListItemViewHolder(View view) {
            super(view);
            imageFriendView = view.findViewById(R.id.image_friend);
            nameView = view.findViewById(R.id.name_friend);

        }
    }
}