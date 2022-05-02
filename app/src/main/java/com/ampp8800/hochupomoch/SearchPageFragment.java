package com.ampp8800.hochupomoch;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchPageFragment extends Fragment {
    private int pageNumber;
    private ArrayList<String> searches = new ArrayList<>();
    private Context context;
    private EventsRepository eventsRepository = EventsRepository.getInstance();


    public static SearchPageFragment newInstance(int page) {
        SearchPageFragment fragment = new SearchPageFragment();
        Bundle args = new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pageNumber = getArguments().getInt("num");
        } else {
            pageNumber = 1;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_search_page, container, false);
        context = result.getContext();
        // начальная инициализация списка
        setInitialData();
        RecyclerView recyclerView = result.findViewById(R.id.searches_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        // добавление тоста
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void invoke(String name) {
                Toast toast = Toast.makeText(context, name, Toast.LENGTH_SHORT);
                toast.show();
            }
        };
        SearchListAdapter adapter = new SearchListAdapter(context, searches, onItemClickListener);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
        return result;
    }

    private void setInitialData() {
        for (int i = 0; i < eventsRepository.getEvents().size(); i++) {
            searches.add(eventsRepository.getEvents().get(i).getOrganization());
        }
    }

}