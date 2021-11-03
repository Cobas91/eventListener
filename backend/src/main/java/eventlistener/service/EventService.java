package eventlistener.service;

import eventlistener.model.NotificationUser;
import eventlistener.model.event.Event;
import eventlistener.repo.EventRepo;
import eventlistener.repo.NotificationUserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepo eventRepo;

    private final NotificationUserRepo notificationUserRepo;

    public EventService(EventRepo eventRepo, NotificationUserRepo notificationUserRepo) {
        this.eventRepo = eventRepo;
        this.notificationUserRepo = notificationUserRepo;
    }

    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    public Event addEvent(Event eventToAdd) {
        return eventRepo.save(validateEventDataSet(eventToAdd));
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
