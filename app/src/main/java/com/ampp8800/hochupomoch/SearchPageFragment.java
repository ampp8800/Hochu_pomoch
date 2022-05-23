package com.ampp8800.hochupomoch;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchPageFragment extends Fragment {
    private int pageNumber;
    private ArrayList<String> searches = new ArrayList<>();
    private Context context;
    private SearchListAdapter searchListAdapter;
    private View view;
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
            PageFragmentViewModel pageFragmentViewModel = new ViewModelProvider(this).get(PageFragmentViewModel.class);
        } else {
            pageNumber = 1;
            PageFragmentViewModel pageFragmentViewModel = new ViewModelProvider(this).get(PageFragmentViewModel.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_search_page, container, false);
        view = result;
        context = result.getContext();
        // начальная инициализация списка
        setDataInFragmentBody();
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

    public void setDataInFragmentBody() {
        for (int i = 0; i < eventsRepository.getEvents().size(); i++) {
            if (pageNumber == 0) {
                searches.add(eventsRepository.getEvents().get(i).getName());
            } else {
                searches.add(eventsRepository.getEvents().get(i).getOrganization());
            }
        }
    }

    public int setDataInFragmentBody(@NonNull String searchQuery) {
        searches.removeAll(searches);
        // Если пустой запрос
        if (searchQuery.equals("")) {
            setDataInFragmentBody();
        }
        // Если не пустой запрос
        else {
            String queryWords[] = searchQuery.split(" ");
            // Перебираем все элементы репозитория
            for (EventItem eventItem : eventsRepository.getEvents()) {
                String eventWords[];
                boolean isStopCycle = false;
                String event;
                if (pageNumber == 0) {
                    event = eventItem.getName();
                } else {
                    event = eventItem.getOrganization();
                }
                eventWords = event.split(" ");
                // Перебираем все слова в элементе репозитория
                for (String eventWord : eventWords) {
                    // Перебираем все слова в запросе
                    for (String queryWord : queryWords) {
                        if (wordComparison(queryWord, eventWord)) {
                            searches.add(event);
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
        return searches.size();
    }

    public void updatePageFragment(String searchQery) {
        int eventsFound = setDataInFragmentBody(searchQery);
        searchListAdapter.notifyDataSetChanged();
        ((TextView) view.findViewById(R.id.tv_keywords)).setText(extractionOfKeywords(searchQery));
        ((TextView) view.findViewById(R.id.tv_searching_result)).setText(getString(R.string.searching_result) + " " + eventsFound);
    }

    private boolean wordComparison(@NonNull String desired, @NonNull String original) {
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

    private String extractionOfKeywords(String keywords) {
        String result = getString(R.string.keywords) + " ";
        String str[] = keywords.split(" ");
        for (String word : str) {
            result += word + ", ";
        }
        result = result.substring(0, (result.length() - 2));
        return result;
    }

}