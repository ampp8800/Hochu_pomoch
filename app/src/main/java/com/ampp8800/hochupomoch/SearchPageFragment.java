package com.ampp8800.hochupomoch;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SearchPageFragment extends Fragment {
    private int pageNumber;
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
        } else {
            pageNumber = 1;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_page, container, false);
        setRetainInstance(true);
        if (savedInstanceState == null) {
            EventsRepository.setDataInListOfEvents(pageNumber);
        } else {
            // загрузка предыдущего отображения
            if (pageNumber == 0) {
//                EventsRepository.setDataInListOfEvents(savedInstanceState.getString("eventsList0", ""), pageNumber);
                insertSearchDescription(savedInstanceState.getString("keywords0", ""),
                        savedInstanceState.getString("searchingResult0", ""));

            } else {
//                EventsRepository.setDataInListOfEvents(savedInstanceState.getString("eventsList1", ""), pageNumber);
                insertSearchDescription(savedInstanceState.getString("keywords1", ""),
                        savedInstanceState.getString("searchingResult1", ""));
            }
        }
        // начальная инициализация списка
        searchListAdapter = new SearchListAdapter(view.getContext());
        RecyclerView recyclerView = view.findViewById(R.id.searches_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(searchListAdapter);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle onState) {
        super.onSaveInstanceState(onState);
        String keywords = ((TextView) view.findViewById(R.id.tv_keywords)).getText().toString();
        String searchingResult = ((TextView) view.findViewById(R.id.tv_searching_result)).getText().toString();
//        String eventsList = null;
//        for (String word : EventsRepository.getReturnedList()) {
//            eventsList += word;
//        }
        if (pageNumber == 0) {
            onState.putString("keywords0", keywords);
            onState.putString("searchingResult0", searchingResult);
//            onState.putString("eventsList0", eventsList);
        } else {
            onState.putString("keywords1", keywords);
            onState.putString("searchingResult1", searchingResult);
//            onState.putString("eventsList1", eventsList);
        }
    }

    public void updatePageFragment(String searchQery) {
        int eventsFound = EventsRepository.updateListOfEvents(searchQery, pageNumber);
        searchListAdapter.notifyDataSetChanged();
        insertSearchDescription(extractKeywords(searchQery),
                getString(R.string.searching_result) + " " + eventsFound);
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