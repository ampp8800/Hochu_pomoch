package com.ampp8800.hochupomoch.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ampp8800.hochupomoch.R;
import com.ampp8800.hochupomoch.data.ListItem;
import com.ampp8800.hochupomoch.data.OnItemClickListener;

import java.util.ArrayList;

public class HelpFragment extends Fragment {

    private final ArrayList<ListItem> categories = new ArrayList<ListItem>();

    @NonNull
    public static HelpFragment newInstance() {
        return new HelpFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        setUpAppBar(((AppCompatActivity) getActivity()).getSupportActionBar());
        setInitialData();
        initializeListOfCategories(view, view.getContext());
        return view;
    }

    private void setInitialData() {
        if (categories.isEmpty()) {
            addCategoriesItem(R.string.children, R.drawable.children);
            addCategoriesItem(R.string.adults, R.drawable.adults);
            addCategoriesItem(R.string.elderly, R.drawable.elderly);
            addCategoriesItem(R.string.animals, R.drawable.animals);
            addCategoriesItem(R.string.events, R.drawable.events);
        }
    }

    private void addCategoriesItem(@StringRes int text, @DrawableRes int icon) {
        categories.add(new ListItem((String) getText(text), icon));
    }

    private void setUpAppBar(ActionBar actionBar) {
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((TextView) getActivity().findViewById(R.id.tv_toolbar_name)).setText(R.string.help);
    }

    private void initializeListOfCategories(@NonNull View view, @NonNull Context context) {
        RecyclerView recyclerView = view.findViewById(R.id.helps_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void invoke(String name) {
                Toast toast = Toast.makeText(context, name, Toast.LENGTH_SHORT);
                toast.show();
            }
        };
        HelpAdapter adapter = new HelpAdapter(context, categories, onItemClickListener);
        recyclerView.setAdapter(adapter);
    }
}
