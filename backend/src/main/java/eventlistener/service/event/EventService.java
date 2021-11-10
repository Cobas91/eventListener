package eventlistener.service.event;


import eventlistener.model.event.Event;
import eventlistener.model.notificationuser.NotificationUser;
import eventlistener.repo.EventRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;



@Service
@Slf4j
public class EventService {

    private final EventRepo eventRepo;

    private final EventMapper eventMapper;


    public EventService(EventRepo eventRepo, EventMapper eventMapper) {
        this.eventRepo = eventRepo;
        this.eventMapper = eventMapper;

    }

    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    public Event addEvent(Event eventToAdd) {
        return eventRepo.save(eventToAdd);
    }

    public List<Event> getAllEventsFromUser(NotificationUser user) {
        return eventRepo.findAllByNotificationUserContains(user);
    }

    public List<Event> getAllEventsExcludeUser(NotificationUser users) {
        return eventRepo.findAllByNotificationUserNotContaining(users);
    }


    public boolean eventsExist(List<Long> listenEvents) {
        List<Event> listOfEvents = (List<Event>) eventRepo.findAllById(listenEvents);
        return listOfEvents.size() >= listenEvents.size();
    }

    public void addUserToEvents(List<Long> listenEvents, NotificationUser user) {
        for (Long eventId : listenEvents) {
            Event eventToEdit = getEventById(eventId);
            if(!userExistInEvent(user.getId(), eventToEdit)){
                List<NotificationUser> userList = eventToEdit.getNotificationUser();
                userList.add(user);
                eventRepo.save(eventToEdit);
            }
        }
    }

    public List<Event> setNotificationUserToEvents(NotificationUser user, List<Event> eventsToSet){
        clearAllEventLinks(user);
        List<Event> newUserEvents = new ArrayList<>();
        for (Event event : eventsToSet) {
            newUserEvents.add(addUserToEvent(event.getId(), user));
        }
        return newUserEvents;
    }

    public Event addUserToEvent(long eventId, NotificationUser userId){
        Event eventToEdit = eventRepo.findById(eventId).orElseThrow(() -> new NoSuchElementException("No event found with ID: "+eventId));
        eventToEdit.getNotificationUser().add(userId);
        return eventRepo.save(eventToEdit);
    }

    private void clearAllEventLinks(NotificationUser user){
        List<Event> userEvents = eventRepo.findAllByNotificationUserContains(user);
        for (Event userEvent : userEvents) {
            userEvent.getNotificationUser().remove(user);
        }
        eventRepo.saveAll(userEvents);
    }

    private boolean userExistInEvent(long id, Event toCheck){
        return toCheck.getNotificationUser().contains(id);
    }

    public Event getEventById(Long eventId){
        return eventRepo.findById(eventId).orElseThrow(() -> new NoSuchElementException("No event found with ID: " + eventId));
    }

    public Event getSingleEvent(Long eventId) {
        Optional<Event> optionalSimpleEvent = eventRepo.findById(eventId);
        if(optionalSimpleEvent.isPresent()){
            return optionalSimpleEvent.get();
        }
        log.error("Can´t find Event with ID "+eventId);
        throw new NoSuchElementException("Can´t find Event with ID "+eventId);
    }
}
