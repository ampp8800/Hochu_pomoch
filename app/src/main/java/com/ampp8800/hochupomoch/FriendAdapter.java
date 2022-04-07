package com.ampp8800.hochupomoch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendListItemViewHolder> {

    private final LayoutInflater inflater;
    private final List<ProfileRepository.FriendListItem> friendListItems;
    private final Context context;
    private View view;

    FriendAdapter(@NonNull Context context, @NonNull List<ProfileRepository.FriendListItem> friendListItems) {
        this.friendListItems = friendListItems;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public FriendListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.friend_item, parent, false);
        return new FriendListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendListItemViewHolder holder, int position) {
        ProfileRepository.FriendListItem friend = friendListItems.get(position);
        holder.imageFriendView.setImageResource(targetImageViewFromUrl(friend.getImageViewURL()));
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
            imageFriendView = view.findViewById(R.id.civ__friend);
            nameView = view.findViewById(R.id.tv_name_friend);
        }
    }

    private int targetImageViewFromUrl(@NonNull String imageViewURL) {
        Glide
                .with(context)
                .load(imageViewURL)
                .into((ImageView) view.findViewById(R.id.civ__friend));
        return R.drawable.avatar_frend;
    }
}