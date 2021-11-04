package eventlistener.service;

import eventlistener.model.notificationUser.NotificationUser;
import eventlistener.model.event.Event;
import eventlistener.repo.EventRepo;
import eventlistener.repo.NotificationUserRepo;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class EventService {

    private final EventRepo eventRepo;


    public EventService(EventRepo eventRepo) {
        this.eventRepo = eventRepo;

    }

    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    public Event addEvent(Event eventToAdd) {
        return eventRepo.save(eventToAdd);
    }

    public List<Event> getAllEventsFromUser(String userId) {
        List<Event> userEvents = new ArrayList<>();
        List<Event> allEvents = eventRepo.findAll();
        for (Event event : allEvents) {
            List<String> users = event.getNotificationUser();
            for (String user : users) {
                if(Objects.equals(user, userId)){
                    userEvents.add(event);
                }
            }
        }
        return userEvents;
    }


    public boolean eventsExist(List<String> listenEvents) {
        List<Event> listOfEvents = (List<Event>) eventRepo.findAllById(listenEvents);
        return listOfEvents.size() >= listenEvents.size();
    }

    public void addUserToEvents(List<String> listenEvents, NotificationUser userWithID) {
        for (String eventId : listenEvents) {
            Event eventToEdit = getEventById(eventId);
            List<String> userList = eventToEdit.getNotificationUser();
            userList.add(userWithID.getId());
            eventRepo.save(eventToEdit);

        }
    }

    public Event getEventById(String eventId){
        Optional<Event> optEvent = eventRepo.findById(eventId);
        if(optEvent.isPresent()) return optEvent.get();
        throw new NoSuchElementException("No event found with ID: "+eventId);
    }
}
