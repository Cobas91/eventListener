package eventlistener.service;

import eventlistener.model.event.Event;
import eventlistener.model.notificationuser.NotificationUser;
import eventlistener.service.event.EventService;
import eventlistener.service.notificaionuser.NotificationUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserEventService {

    private final EventService eventService;
    private final NotificationUserService notificationUserService;

    public UserEventService(EventService eventService, NotificationUserService notificationUserService) {
        this.eventService = eventService;
        this.notificationUserService = notificationUserService;
    }

    public boolean eventsExist(List<String> listenEvents) {
        return eventService.eventsExist(listenEvents);
    }

    public void addUserToEvents(List<String> listenEvents, String userId) {
        eventService.addUserToEvents(listenEvents, userId);
    }
    public List<Event> getAllEventsFromUser(String userId) {
        return eventService.getAllEventsFromUser(userId);
    }

    public List<Event> setNotificationUserToEvents(String userId, List<String> events){
        return eventService.setNotificationUserToEvents(userId, events);
    }

    public List<NotificationUser> getUsersById(List<String> userIds){
        return notificationUserService.getUsersById(userIds);
    }
}
