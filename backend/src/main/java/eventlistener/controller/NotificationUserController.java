package eventlistener.controller;

import eventlistener.model.NotificationUser;
import eventlistener.service.NotificationUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class NotificationUserController {

    private final NotificationUserService notificationUserService;

    public NotificationUserController(NotificationUserService notificationUserService) {
        this.notificationUserService = notificationUserService;
    }

    @GetMapping()
    public List<NotificationUser> getAllUser(){
        return notificationUserService.getAllUser();
    }

    @PostMapping
    public NotificationUser addUser(@RequestBody NotificationUser userToAdd){
        return notificationUserService.addUser(userToAdd);
    }

}
