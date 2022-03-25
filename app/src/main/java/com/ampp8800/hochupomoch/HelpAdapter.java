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

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.HelpAdapterViewHolder> {

    private final LayoutInflater inflater;
    private final List<HelpListItem> helpListItem;
    private final Context context;

    HelpAdapter(Context context, List<HelpListItem> helpListItem) {
        this.helpListItem = helpListItem;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public HelpAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.help_item, parent, false);
        return new HelpAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HelpAdapterViewHolder holder, int position) {
        HelpListItem help = helpListItem.get(position);
        holder.imageHelpView.setImageResource(help.getImageHelpResource());
        holder.nameView.setText(help.getName());
    }

    @Override
    public int getItemCount() {
        return helpListItem.size();
    }

    public class HelpAdapterViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageHelpView;
        final TextView nameView;

        HelpAdapterViewHolder(View view) {
            super(view);
            imageHelpView = view.findViewById(R.id.imageHelp);
            nameView = view.findViewById(R.id.name);
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int positionIndex = getAdapterPosition();
                    String name = helpListItem.get(positionIndex).getName();
                    Toast toast = Toast.makeText(context, name, Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
    }
}
