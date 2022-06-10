package com.ampp8800.hochupomoch.data;

import androidx.annotation.NonNull;

import com.ampp8800.hochupomoch.ui.EventItem;

import java.util.ArrayList;
import java.util.List;

public class EventsRepository {
    private static EventsRepository eventsRepository;
    private static List<EventItem> events = new ArrayList<>();

    private EventsRepository() {
    }

    @NonNull
    public static EventsRepository getInstance() {
        if (eventsRepository == null) {
            eventsRepository = new EventsRepository();
            events.add(new EventItem("Благотворительный фонд Алины", "Сбор средств"));
            events.add(new EventItem("Во имя жизни", "Волонтерство"));
            events.add(new EventItem("Благотворительный фонд Панина", "Работа в питомнике"));
            events.add(new EventItem("Детские домики", "Опека"));
            events.add(new EventItem("Мозайка счастья", "Сбор средств"));
        }
        return eventsRepository;
    }

    public static List<EventItem> getListOfEvents() {
        return events;
    }

    public static List<EventItem> getListOfEvents(@NonNull String searchQuery, SearchType currentSearchType) {
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
                if (currentSearchType == SearchType.EVENT) {
                    event = itemFromSearchArea.getName();
                } else {
                    event = itemFromSearchArea.getOrganization();
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
