package eventlistener.service.event;

import eventlistener.model.event.ResponseEvent;

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

    public void addUserToEvents(List<String> listenEvents, String userId) {
        for (String eventId : listenEvents) {
            Event eventToEdit = getEventById(eventId);
            if(!userExistInEvent(userId, eventToEdit)){
                List<String> userList = eventToEdit.getNotificationUser();
                userList.add(userId);
                eventRepo.save(eventToEdit);
            }
        }
    }

    public List<Event> setNotificationUserToEvents(String userId, List<String> events){
        clearAllEventLinks(userId);
        List<Event> newUserEvents = new ArrayList<>();
        for (String eventId : events) {
            newUserEvents.add(addUserToEvent(eventId, userId));
        }
        return newUserEvents;
    }

    public Event addUserToEvent(String eventId, String userId){
        Event eventToEdit = eventRepo.findById(eventId).orElseThrow(() -> new NoSuchElementException("No event found with ID: "+eventId));
        eventToEdit.getNotificationUser().add(userId);
        return eventRepo.save(eventToEdit);
    }

    private void clearAllEventLinks(String userId){
        List<Event> userEvents = eventRepo.findAllByNotificationUserContains(userId);
        for (Event userEvent : userEvents) {
            userEvent.getNotificationUser().remove(userId);
        }
        eventRepo.saveAll(userEvents);
    }

    private boolean userExistInEvent(String id, Event toCheck){
        return toCheck.getNotificationUser().contains(id);
    }

    public Event getEventById(String eventId){
        return eventRepo.findById(eventId).orElseThrow(() -> new NoSuchElementException("No event found with ID: " + eventId));
    }
}
