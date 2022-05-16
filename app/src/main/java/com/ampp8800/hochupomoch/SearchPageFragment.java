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
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchPageFragment extends Fragment {
    private int pageNumber;
    private ArrayList<String> searches = new ArrayList<>();
    private Context context;
    private SearchListAdapter searchListAdapter;
    private EventsRepository eventsRepository = EventsRepository.getInstance();

    public static SearchPageFragment newInstance(int page) {
        SearchPageFragment fragment = new SearchPageFragment();
        Bundle args = new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    public SearchListAdapter getSearchListAdapter() {
        return searchListAdapter;
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
        searchListAdapter = new SearchListAdapter(context, searches, onItemClickListener);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(searchListAdapter);
        return result;
    }

    public void setInitialData() {
        for (int i = 0; i < eventsRepository.getEvents().size(); i++) {
            searches.add(eventsRepository.getEvents().get(i).getOrganization());
        }
    }

    public void setInitialData(@NonNull String searchQuery, @NonNull int currentItem) {
        searches.removeAll(searches);
        // Если пустой запрос
        if (searchQuery.equals("")) {
            searches.removeAll(searches);
            for (int i = 0; i < eventsRepository.getEvents().size(); i++) {
                searches.add(eventsRepository.getEvents().get(i).getOrganization());
            }
        }
        // Если не пустой запрос
        else {
            String queryWords[] = searchQuery.split(" ");
            // Перебираем все элементы репозитория
            for (EventItem eventItem : eventsRepository.getEvents()) {
                String events[];
                boolean isStopCycle = false;
                if (currentItem == 0) {
                    events = eventItem.getName().split(" ");
                } else {
                    events = eventItem.getOrganization().split(" ");
                }
                // Перебираем все слова в элементе репозитория
                for (String event : events) {
                    // Перебираем все слова в запросе
                    for (String queryWord : queryWords) {
                        if (wordComparison(queryWord, event)) {
                            searches.add(eventItem.getOrganization());
                            isStopCycle = true;
                            break;
                        }
                    }
                    if (isStopCycle) {
                        break;
                    }
                }
            }
        }
    }

    public void updatePageFragment(String searchQery, int currentItem) {
        setInitialData(searchQery, currentItem);
        newInstance(currentItem).getSearchListAdapter().notifyDataSetChanged();
    }

    public boolean wordComparison(@NonNull String desired, @NonNull String original) {
        if (original.length() >= desired.length()) {
            for (int i = 0; i <= (original.length() - desired.length()); i++) {
                for (int j = 0; j <= (original.length() - desired.length() - i); j++) {
                    if (desired.equalsIgnoreCase(original.substring(j, (original.length() - i)))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}