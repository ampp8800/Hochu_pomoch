package com.ampp8800.hochupomoch.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ampp8800.hochupomoch.R;
import com.ampp8800.hochupomoch.data.EventsRepository;

import java.util.List;

public class SearchPageFragment extends Fragment {
    private String searchQery;
    private SearchListAdapter searchListAdapter;
    private View view;
    private EventsRepository eventsRepository = EventsRepository.getInstance();
    private SearchType currentSearchType;
    private final String EVENT_LIST_BY_NAME = "eventListByName";
    private static final String PAGE_NAME ="pageName";


    public static SearchPageFragment newInstance(SearchType page) {
        SearchPageFragment fragment = new SearchPageFragment();
        Bundle args = new Bundle();
        args.putSerializable(PAGE_NAME, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch ((SearchType) getArguments().getSerializable(PAGE_NAME)) {
            case EVENT:
                currentSearchType = SearchType.EVENT;
                break;
            case ORGANIZATION:
                currentSearchType = SearchType.ORGANIZATION;
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_page, container, false);
        searchListAdapter = new SearchListAdapter(view.getContext(), currentSearchType);
        if (savedInstanceState != null) {
            // загрузка предыдущего отображения
            updatePageFragment(savedInstanceState.getString(EVENT_LIST_BY_NAME, ""));
        }
        // начальная инициализация списка
        RecyclerView recyclerView = view.findViewById(R.id.searches_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(searchListAdapter);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle onState) {
        super.onSaveInstanceState(onState);
        onState.putString(EVENT_LIST_BY_NAME, searchQery);
    }

    public void updatePageFragment(String searchQery) {
        this.searchQery = searchQery;
        List<EventItem> eventsFound = EventsRepository.getInstance().getListOfEvents(searchQery, currentSearchType);
        searchListAdapter.setSearchListItems(eventsFound);
        searchListAdapter.notifyDataSetChanged();
        insertSearchDescription(extractKeywords(searchQery),
                getString(R.string.searching_result) + " " + eventsFound.size());
    }

    private String extractKeywords(String keywords) {
        String result = getString(R.string.keywords) + " ";
        String str[] = keywords.split(" ");
        for (String word : str) {
            result += word + ", ";
        }
        result = result.substring(0, (result.length() - 2));
        return result;
    }

    private void insertSearchDescription(String searchQery, String searchingResult) {
        ((TextView) view.findViewById(R.id.tv_keywords)).setText(searchQery);
        ((TextView) view.findViewById(R.id.tv_searching_result)).setText(searchingResult);
    }

}