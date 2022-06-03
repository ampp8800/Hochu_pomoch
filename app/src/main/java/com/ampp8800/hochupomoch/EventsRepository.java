package com.ampp8800.hochupomoch;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class EventsRepository {
    private static EventsRepository eventsRepository;
    private static List<EventItem> events = new ArrayList<>();
    private static List<String> returnedList = new ArrayList<>();

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

    public static List<String> getReturnedList() {
        return returnedList;
    }

    public static int updateListOfEvents(String searchQery, int pageNumber) {
        int eventsFound = setDataInListOfEvents(searchQery, pageNumber);
        return eventsFound;
    }

    public static void setDataInListOfEvents(@NonNull int pageNumber) {
        returnedList.removeAll(returnedList);
        for (int i = 0; i < events.size(); i++) {
            if (pageNumber == 0) {
                returnedList.add(events.get(i).getName());
            } else {
                returnedList.add(events.get(i).getOrganization());
            }
        }
    }

    public static int setDataInListOfEvents(@NonNull String searchQuery, @NonNull int pageNumber) {
        returnedList.removeAll(returnedList);
        // Если пустой запрос
        if (searchQuery.equals("")) {
            setDataInListOfEvents(pageNumber);
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
                        returnedList.add(event);
                        break;
                    }
                }
            }
        }
        return returnedList.size();
    }

}
