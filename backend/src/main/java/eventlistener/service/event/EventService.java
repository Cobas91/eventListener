package eventlistener.service.event;


import eventlistener.model.event.Event;
import eventlistener.model.event.EventToModifyDTO;
import eventlistener.model.notificationuser.NotificationUser;
import eventlistener.repo.EventRepo;
import eventlistener.service.UserEventService;
import eventlistener.service.notificaionuser.NotificationUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;



@Service
@Slf4j
public class EventService {

    private final EventRepo eventRepo;

    private final EventMapper eventMapper;

    private final UserEventService userEventService;

    public EventService(EventRepo eventRepo, EventMapper eventMapper, UserEventService userEventService) {
        this.eventRepo = eventRepo;
        this.eventMapper = eventMapper;
        this.userEventService = userEventService;
    }

    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    public Event addEvent(Event eventToAdd) {
        return eventRepo.save(eventToAdd);
    }

    public List<Event> getAllEventsFromUser(String userId) {
        return eventRepo.findAllByNotificationUserContains(userId);
    }

    public List<Event> getAllEventsExcludeUser(String userId) {
        return eventRepo.findAllByNotificationUserNotContains(userId);
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

    public EventToModifyDTO getSingleEvent(String eventId) {
        Optional<Event> optionalSimpleEvent = eventRepo.findById(eventId);
        if(optionalSimpleEvent.isPresent()){
            List<NotificationUser> userList = userEventService.getUsersById(optionalSimpleEvent.get().getNotificationUser());
            return eventMapper.mapToModify(optionalSimpleEvent.get(), userList);
        }
        log.error("Can´t find Event with ID "+eventId);
        throw new NoSuchElementException("Can´t find Event with ID "+eventId);
    }
}
