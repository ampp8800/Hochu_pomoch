package com.ampp8800.hochupomoch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.HelpListItemViewHolder> {

    private final LayoutInflater inflater;
    private final List<HelpListItem> helpListItems;
    private final Context context;

    HelpAdapter(Context context, List<HelpListItem> helpListItems) {
        this.helpListItems = helpListItems;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
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
                    int positionIndex = getAdapterPosition();
                    String name = helpListItems.get(positionIndex).getName();
                    Toast toast = Toast.makeText(context, name, Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
    }
}
