package eventlistener.controller;

import eventlistener.model.NotificationUser;
import eventlistener.service.NotificationUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/user")
public class NotificationUserController {

    private NotificationUserService notificationUserService;

    public NotificationUserController(NotificationUserService notificationUserService) {
        this.notificationUserService = notificationUserService;
    }

    @GetMapping
    public List<NotificationUser> getAllUser(){
        return notificationUserService.getAllUser();
    }

    @PostMapping
    public NotificationUser addUser(@RequestBody NotificationUser userToAdd){
        return notificationUserService.addUser(userToAdd);
    }

}
