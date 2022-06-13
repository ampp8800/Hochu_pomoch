package com.ampp8800.hochupomoch.data;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.ui.EventItem;
import com.ampp8800.hochupomoch.ui.SearchType;

import java.util.ArrayList;
import java.util.List;

public class EventsRepository {
    private static EventsRepository eventsRepository;
    private List<EventItem> events = new ArrayList<>();

    private EventsRepository() {
        events.add(new EventItem("Благотворительный фонд Алины", "Сбор средств"));
        events.add(new EventItem("Во имя жизни", "Волонтерство"));
        events.add(new EventItem("Благотворительный фонд Панина", "Работа в питомнике"));
        events.add(new EventItem("Детские домики", "Опека"));
        events.add(new EventItem("Мозайка счастья", "Сбор средств"));
    }

    @NonNull
    public static EventsRepository getInstance() {
        if (eventsRepository == null) {
            eventsRepository = new EventsRepository();
        }
        return eventsRepository;
    }

    public List<EventItem> getListOfEvents() {
        return events;
    }

    public List<EventItem> getListOfEvents(@NonNull String searchQuery, @NonNull SearchType currentSearchType) {
        List<EventItem> result = new ArrayList<>();
        // Если пустой запрос
        if (searchQuery.isEmpty()) {
            result.addAll(events);
        }
        // Если не пустой запрос
        else {
            String queryWords[] = searchQuery.split(" ");
            // Перебираем все элементы репозитория
            for (EventItem itemFromSearchArea : events) {
                String event;
                switch (currentSearchType) {
                    case EVENT:
                        event = itemFromSearchArea.getName();
                        break;
                    case ORGANIZATION:
                        event = itemFromSearchArea.getOrganization();
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
                // Перебираем все слова в запросе
                for (String queryWord : queryWords) {
                    if (event.toLowerCase().contains(queryWord.toLowerCase())) {
                        result.add(itemFromSearchArea);
                        break;
                    }
                }
            }
        }
        return result;
    }

}
