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
    private SearchListAdapter searchListAdapter;


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
        setInitialData("");
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
        searchListAdapter = new SearchListAdapter(context, searches, onItemClickListener);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(searchListAdapter);
        return result;
    }

    public void setInitialData(String searchQuery) {
        searches.removeAll(searches);
        if (searchQuery.equals("")) {
            searches.removeAll(searches);
            for (int i = 0; i < eventsRepository.getEvents().size(); i++) {
                searches.add(eventsRepository.getEvents().get(i).getOrganization());
            }
        } else {
            String queryWords[] = searchQuery.split(" ");
            for (EventItem eventItem : eventsRepository.getEvents()) {
                for (String queryWord : queryWords) {


                    // ну, за работу!
                }
            }
        }
    }

    public void updatePageFragment(String searchQery) {
        setInitialData(searchQery);
        searchListAdapter.notifyDataSetChanged();
    }

    public boolean wordComparison(@NonNull String desired, @NonNull String original) {
        boolean result = false;
        for(int i = 0; i <= (original.length() - desired.length()); i++) {
            if (desired.equals(original.substring(0, (original.length() - i)))) {
                return true;
            } else if (desired.equals(original.substring(i, original.length()))) {
                return true;
            }
        }
        return false;
    }

}