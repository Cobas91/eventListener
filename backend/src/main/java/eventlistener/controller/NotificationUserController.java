package eventlistener.controller;

import eventlistener.model.notificationuser.NotificationUser;
import eventlistener.model.notificationuser.NotificationUserDTO;
import eventlistener.model.event.Event;
import eventlistener.model.notificationuser.NotificationUserEditDTO;
import eventlistener.service.UserEventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class NotificationUserController {



    private final UserEventService userEventService;

    public NotificationUserController(UserEventService userEventService) {
        this.userEventService = userEventService;
    }

    @GetMapping
    public List<NotificationUser> getAllUser(){
        return userEventService.getAllUsers();
    }

    @PostMapping
    public NotificationUser addUser(@RequestBody NotificationUserDTO userToAdd){
        return userEventService.addUser(userToAdd);
    }

    @GetMapping("/{id}")
    public NotificationUserEditDTO getSingleUser(@PathVariable Long id){
        return userEventService.getSingleUser(id);
    }

    @PutMapping("/{id}")
    public NotificationUser editUser(@PathVariable Long id, @RequestBody NotificationUserEditDTO userToEdit){
        return userEventService.editUser(id,userToEdit);
    }

    @GetMapping("/{userId}/event")
    public List<Event> getAllEventsFromUser(@PathVariable Long userId){
        return userEventService.getAllEventsFromUser(userId);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId){
        return userEventService.deleteUser(userId);
    }

}
