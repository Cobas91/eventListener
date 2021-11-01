package eventlistener.controller;

import eventlistener.security.model.AppUserDTO;
import eventlistener.service.AppUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appuser")
public class AppUserController {
    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }


    @PostMapping()
    public AppUserDTO addUserToDB(@RequestBody AppUserDTO userToAdd){
        return appUserService.addUser(userToAdd);
    }
}
