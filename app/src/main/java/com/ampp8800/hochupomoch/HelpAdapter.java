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

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.HelpListItemViewHolder> {

    private final LayoutInflater inflater;
    private final List<HelpListItem> helpListItems;
    private final Context context;
    private final OnHelpItemClickListner onHelpItemClickListner;

    HelpAdapter(@NonNull Context context, @NonNull List<HelpListItem> helpListItems, @NonNull OnHelpItemClickListner onHelpItemClickListner) {
        this.helpListItems = helpListItems;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.onHelpItemClickListner = onHelpItemClickListner;
    }

    @Override
    public HelpListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.help_item, parent, false);
        return new HelpListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HelpListItemViewHolder holder, int position) {
        HelpListItem help = helpListItems.get(position);
        holder.imageHelpView.setImageResource(help.getImageHelpResource());
        holder.nameView.setText(help.getName());
    }

    @Override
    public int getItemCount() {
        return helpListItems.size();
    }

    public class HelpListItemViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageHelpView;
        final TextView nameView;


        HelpListItemViewHolder(View view) {
            super(view);
            imageHelpView = view.findViewById(R.id.imageHelp);
            nameView = view.findViewById(R.id.name);
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                        onHelpItemClickListner.invoke(helpListItems.get(getAdapterPosition()).getName());
                }
            });
        }
    }
}
