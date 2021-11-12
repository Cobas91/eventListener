package eventlistener.service;

import eventlistener.exception.EventNotFoundException;
import eventlistener.model.event.Event;
import eventlistener.model.event.EventToModifyDTO;
import eventlistener.model.notificationuser.NotificationUser;
import eventlistener.model.notificationuser.NotificationUserDTO;
import eventlistener.model.notificationuser.NotificationUserEditDTO;
import eventlistener.service.event.EventService;
import eventlistener.service.notificaionuser.NotificationUserMapper;
import eventlistener.service.notificaionuser.NotificationUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserEventService {

    private final EventService eventService;
    private final NotificationUserService notificationUserService;

    private final NotificationUserMapper notificationUserMapper;

    public UserEventService(EventService eventService, NotificationUserService notificationUserService, NotificationUserMapper notificationUserMapper) {
        this.eventService = eventService;
        this.notificationUserService = notificationUserService;
        this.notificationUserMapper = notificationUserMapper;
    }

    public List<NotificationUser> getAllUser() {
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
        return notificationUserMapper.mapUserToEditUser(userToMap, eventsToMap);

    }

    public NotificationUser editUser(Long id, NotificationUserEditDTO userToEdit) {
        //TODO erfolg testen -> events wurden aktualisiert?
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

    public Event addEvent(Event eventToAdd) {
        return eventService.addEvent(eventToAdd);
    }

    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }
}
