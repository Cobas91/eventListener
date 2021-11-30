/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

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
