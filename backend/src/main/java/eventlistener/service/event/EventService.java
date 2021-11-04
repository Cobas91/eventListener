package eventlistener.service.event;

import eventlistener.model.event.ResponseEvent;
import eventlistener.model.notificationUser.NotificationUser;
import eventlistener.model.event.Event;
import eventlistener.repo.EventRepo;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class EventService {

    private final EventRepo eventRepo;

    private final EventResponseMapper mapper;

    public EventService(EventRepo eventRepo, EventResponseMapper mapper) {
        this.eventRepo = eventRepo;
        this.mapper = mapper;

    }

    public List<ResponseEvent> getAllEvents() {
        return mapper.mapResponseEvents(eventRepo.findAll());
    }

    public Event addEvent(Event eventToAdd) {
        return eventRepo.save(eventToAdd);
    }

    public List<Event> getAllEventsFromUser(String userId) {
        return eventRepo.findAllByNotificationUserContains(userId);
    }


    public boolean eventsExist(List<String> listenEvents) {
        List<Event> listOfEvents = (List<Event>) eventRepo.findAllById(listenEvents);
        return listOfEvents.size() >= listenEvents.size();
    }

    public void addUserToEvents(List<String> listenEvents, NotificationUser notificationUser) {
        for (String eventId : listenEvents) {
            Event eventToEdit = getEventById(eventId);
            List<String> userList = eventToEdit.getNotificationUser();
            userList.add(notificationUser.getId());
            eventRepo.save(eventToEdit);

        }
    }

    public Event getEventById(String eventId){
        return eventRepo.findById(eventId).orElseThrow(() -> new NoSuchElementException("No event found with ID: " + eventId));
    }
}
