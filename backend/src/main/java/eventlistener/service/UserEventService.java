package eventlistener.service;

import eventlistener.exception.EventNotFoundException;
import eventlistener.model.event.Event;
import eventlistener.model.event.EventToModifyDTO;
import eventlistener.model.notificationuser.NotificationUser;
import eventlistener.model.notificationuser.NotificationUserDTO;
import eventlistener.model.notificationuser.NotificationUserEditDTO;
import eventlistener.service.event.EventMapper;
import eventlistener.service.event.EventService;
import eventlistener.service.notificationuser.NotificationUserMapper;
import eventlistener.service.notificationuser.NotificationUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserEventService {

    private final EventService eventService;
    private final NotificationUserService notificationUserService;

    private final NotificationUserMapper notificationUserMapper;

    private final EventMapper eventMapper;

    public UserEventService(EventService eventService, NotificationUserService notificationUserService, NotificationUserMapper notificationUserMapper, EventMapper eventMapper) {
        this.eventService = eventService;
        this.notificationUserService = notificationUserService;
        this.notificationUserMapper = notificationUserMapper;
        this.eventMapper = eventMapper;
    }

    public List<NotificationUser> getAllUsers() {
        return notificationUserService.getAllUser();
    }

    public NotificationUser addUser(NotificationUserDTO userToAdd) {
        if(eventService.eventsExist(userToAdd.getListenEvents())){
            NotificationUser user = notificationUserService.addUser(userToAdd);
            eventService.addUserToEvents(userToAdd.getListenEvents(), user);
            return user;
        }else{
            throw new EventNotFoundException("Can not add User: "+userToAdd.getName()+" events are not existing.");
        }

    }

    public NotificationUserEditDTO getSingleUser(Long id) {
        NotificationUser userToMap = notificationUserService.getSingleUser(id);
        List<Event> eventsToMap = eventService.getAllEventsFromUser(userToMap);
        return notificationUserMapper.MapNotificationUser(userToMap, eventsToMap);

    }

    public NotificationUser editUser(Long id, NotificationUserEditDTO userToEdit) {
        NotificationUser user = notificationUserService.getSingleUser(id);
        eventService.setNotificationUserToEvents(user, userToEdit.getListenEvents());
        return notificationUserService.editUser(userToEdit);
    }

    public List<Event> getAllEventsFromUser(Long userId) {
        NotificationUser user = notificationUserService.getSingleUser(userId);
        return eventService.getAllEventsFromUser(user);
    }

    public List<Event> getAllEventsExcludeUser(Long excludedUserId) {
        NotificationUser userToExclude = notificationUserService.getSingleUser(excludedUserId);
        return eventService.getAllEventsExcludeUser(userToExclude);
    }

    public Event getSingleEvent(Long eventId) {
        return eventService.getSingleEvent(eventId);
    }

    public Event addEvent(EventToModifyDTO eventToAdd) {
        List<NotificationUser> users = notificationUserService.getUsersById(eventToAdd.getNotificationUser());
        return eventService.addEvent(eventMapper.mapEvent(eventToAdd, users));
    }

    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    public Event editEvent(Long eventId, EventToModifyDTO event) {
        if(eventId != event.getId()) throw new IllegalArgumentException("The Event you want to Edit and the given content is not processable");
        List<NotificationUser> users = notificationUserService.getUsersById(event.getNotificationUser());
        Event eventToAdd = eventMapper.mapEvent(event, users);
        return eventService.addEvent(eventToAdd);
    }

    public String deleteEvent(Long eventId) {
        return eventService.deleteEvent(eventId);
    }

    public String deleteUser(Long userId) {
        return notificationUserService.deleteUser(userId);
    }
}
