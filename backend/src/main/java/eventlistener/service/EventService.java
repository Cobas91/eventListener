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

    private Event validateEventDataSet(Event eventToValidate){
        List<NotificationUser> uncheckedUser = eventToValidate.getNotificationUser();
        eventToValidate.setNotificationUser(filterExisting(uncheckedUser));
        return eventToValidate;
    }

    private List<NotificationUser> filterExisting(List<NotificationUser> listToFilter){
        return listToFilter.stream().
                filter(user -> notificationUserRepo.existsById(user.getId()))
                .map(user -> notificationUserRepo.findById(user.getId()).get())
                .collect(Collectors.toList());
    }
}
