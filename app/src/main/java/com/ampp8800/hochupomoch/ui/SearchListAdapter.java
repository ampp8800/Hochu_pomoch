package com.ampp8800.hochupomoch.ui;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ampp8800.hochupomoch.R;
import com.ampp8800.hochupomoch.data.SearchType;
import com.ampp8800.hochupomoch.data.EventsRepository;

import java.util.List;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.SearchListItemViewHolder> {
    private final LayoutInflater inflater;
    private List<EventItem> searchListItems;
    private SearchType currentSearchType;

    SearchListAdapter(@NonNull Context context, SearchType currentSearchType) {
        searchListItems = EventsRepository.getListOfEvents();
        this.inflater = LayoutInflater.from(context);
        this.currentSearchType = currentSearchType;
    }

    public void setSearchListItems(List<EventItem> searchListItems) {
        this.searchListItems = searchListItems;
    }

    @Override
    @NonNull
    public SearchListAdapter.SearchListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.search_item, parent, false);
        return new SearchListAdapter.SearchListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchListAdapter.SearchListItemViewHolder holder, int position) {
        if (currentSearchType == SearchType.EVENT) {
            holder.nameView.setText(searchListItems.get(position).getName());
        } else {
            holder.nameView.setText(searchListItems.get(position).getOrganization());
        }
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
