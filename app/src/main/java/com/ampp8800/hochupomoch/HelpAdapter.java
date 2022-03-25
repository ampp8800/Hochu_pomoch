package com.ampp8800.hochupomoch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Help> helps;

    HelpAdapter(Context context, List<Help> helps) {
        this.helps = helps;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public HelpAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.help_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HelpAdapter.ViewHolder holder, int position) {
        Help help = helps.get(position);
        holder.imageHelpView.setImageResource(help.getImageHelpResource());
        holder.nameView.setText(help.getName());
    }

    @Override
    public int getItemCount() {
        return helps.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageHelpView;
        final TextView nameView;
        ViewHolder(View view){
            super(view);
            imageHelpView = view.findViewById(R.id.imageHelp);
            nameView = view.findViewById(R.id.name);
        }
    }
}
