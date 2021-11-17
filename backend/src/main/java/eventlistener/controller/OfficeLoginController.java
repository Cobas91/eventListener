package eventlistener.controller;

import eventlistener.security.model.api.office.OfficeUserDTO;
import eventlistener.security.service.OAuthService;
import org.springframework.web.bind.annotation.*;

@RestController
public class OfficeLoginController {

    OAuthService oAuthService;

    public OfficeLoginController(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    @GetMapping("/oauth/office/{code}")
    public String login(@PathVariable String code){
        return oAuthService.getOfficeAccessToken(code);
    }
}
