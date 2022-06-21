package com.ampp8800.hochupomoch.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ampp8800.hochupomoch.R;
import com.ampp8800.hochupomoch.data.ListItem;
import com.ampp8800.hochupomoch.data.OnItemClickListener;

import java.util.ArrayList;

public class HelpFragment extends Fragment {

    private ArrayList<ListItem> helps = new ArrayList<ListItem>();


    public static HelpFragment newInstance() {
        return new HelpFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @Nullable Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.activity_help, container, false);
        Context context = view.getContext();

        // начальная инициализация списка
        setInitialData();
        RecyclerView recyclerView = requireActivity().findViewById(R.id.helps_list);
        recyclerView.setLayoutManager(new GridLayoutManager(requireActivity().getApplicationContext(), 2));
        // добавление тоста
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void invoke(String name) {
                Toast toast = Toast.makeText(context, name, Toast.LENGTH_SHORT);
                toast.show();
            }
        };
        // создаем адаптер
        HelpAdapter adapter = new HelpAdapter(context, helps, onItemClickListener);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
        //логика работы нижней панели навигации
        BottomNavigationLogic.initializeBottomBar((View) requireActivity().findViewById(R.id.bottom_navigation));

        return view;
    }

    private void setInitialData() {
        helps.add(new ListItem((String) getText(R.string.children), R.drawable.children));
        helps.add(new ListItem((String) getText(R.string.adults), R.drawable.adults));
        helps.add(new ListItem((String) getText(R.string.elderly), R.drawable.elderly));
        helps.add(new ListItem((String) getText(R.string.animals), R.drawable.animals));
        helps.add(new ListItem((String) getText(R.string.events), R.drawable.events));
    }

    public void setUpAppBar(ActionBar actionBar) {
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((TextView) getActivity().findViewById(R.id.tv_toolbar_name)).setText(R.string.help);
    }

}
