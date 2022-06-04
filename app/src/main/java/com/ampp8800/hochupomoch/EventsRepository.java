package com.ampp8800.hochupomoch;

import androidx.annotation.NonNull;

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

    public static List<EventItem> getListOfEvents(@NonNull String searchQuery, int pageNumber) {
        List<EventItem> result = new ArrayList<>();
        // Если пустой запрос
        if (searchQuery.equals("")) {
            result.addAll(events);
        }
        // Если не пустой запрос
        else {
            String queryWords[] = searchQuery.split(" ");
            // Перебираем все элементы репозитория
            for (EventItem eventItem : events) {
                String event;
                if (pageNumber == 0) {
                    event = eventItem.getName();
                } else {
                    event = eventItem.getOrganization();
                }
                // Перебираем все слова в запросе
                for (String queryWord : queryWords) {
                    if (event.toLowerCase().contains(queryWord.toLowerCase())) {
                        result.add(eventItem);
                        break;
                    }
                }
            }
        }
        return result;
    }

}
