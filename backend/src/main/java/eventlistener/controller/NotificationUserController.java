package eventlistener.controller;

import eventlistener.model.notificationuser.NotificationUser;
import eventlistener.model.notificationuser.NotificationUserDTO;
import eventlistener.model.event.Event;
import eventlistener.model.notificationuser.NotificationUserEditDTO;
import eventlistener.service.event.EventService;
import eventlistener.service.notificaionuser.NotificationUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class NotificationUserController {

    private final NotificationUserService notificationUserService;

    private final EventService eventService;

    public NotificationUserController(NotificationUserService notificationUserService, EventService eventService) {
        this.notificationUserService = notificationUserService;
        this.eventService = eventService;
    }

    @GetMapping
    public List<NotificationUser> getAllUser(){
        return notificationUserService.getAllUser();
    }

    @PostMapping
    public NotificationUser addUser(@RequestBody NotificationUserDTO userToAdd){
        return notificationUserService.addUser(userToAdd);
    }

    @GetMapping("/{id}")
    public NotificationUserDTO getSingleUser(@PathVariable String id){
        return notificationUserService.getSingleUser(id);
    }

    @GetMapping("/event/{userId}")
    public List<Event> getAllEventsFromUser(@PathVariable String userId){
        return eventService.getAllEventsFromUser(userId);
    }

}
