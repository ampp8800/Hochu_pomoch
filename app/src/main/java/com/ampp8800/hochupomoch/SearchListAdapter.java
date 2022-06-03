package com.ampp8800.hochupomoch;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.SearchListItemViewHolder> {
    private final LayoutInflater inflater;
    private List<String> searchListItems;

    SearchListAdapter(@NonNull Context context) {
        searchListItems = EventsRepository.getReturnedList();
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public SearchListAdapter.SearchListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.search_item, parent, false);
        return new SearchListAdapter.SearchListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchListAdapter.SearchListItemViewHolder holder, int position) {
        String search = searchListItems.get(position);
        holder.nameView.setText(search);
        if (position != (getItemCount() - 1)) {
            holder.itemView.setBackgroundResource(R.drawable.background_with_shadow_bottom);
        } else {
            holder.itemView.setBackgroundResource(R.color.white);
        }

    }

    @Override
    public int getItemCount() {
        return searchListItems.size();
    }

    public class SearchListItemViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView;

        SearchListItemViewHolder(@NonNull View view) {
            super(view);
            nameView = view.findViewById(R.id.tv_search_item_name);
        }
    }
}
