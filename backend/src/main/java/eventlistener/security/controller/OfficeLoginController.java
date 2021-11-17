package eventlistener.security.controller;

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
        return oAuthService.getJwtTokenWithOfficeCode(code);
    }
}
