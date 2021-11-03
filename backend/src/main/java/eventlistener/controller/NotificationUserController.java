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

    @GetMapping
    public List<NotificationUser> getAllUser(){
        return notificationUserService.getAllUser();
    }

    @PostMapping
    public NotificationUser addUser(@RequestBody NotificationUserDTO userToAdd){
        return notificationUserService.addUser(userToAdd);
    }

    @GetMapping("/{id}")
    public NotificationUser getSingleUser(@PathVariable String id){
        return notificationUserService.getSingleUser(id);
    }

}
