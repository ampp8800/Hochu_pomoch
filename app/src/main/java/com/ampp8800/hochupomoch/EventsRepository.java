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
            newEventsList();
        }
        return eventsRepository;
    }

    public static List<EventItem> getEvents() {
        return events;
    }

    private static void newEventsList() {
        events.add(new EventItem("Благотворительный фонд Алины", "Сбор средств"));
        events.add(new EventItem("Во имя жизни", "Волонтерство"));
        events.add(new EventItem("Благотворительный фонд Панина", "Работа в питомнике"));
        events.add(new EventItem("Детские домики", "Опека"));
        events.add(new EventItem("Мозайка счастья", "Сбор средств"));
    }

}
