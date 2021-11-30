/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

package eventlistener.security.service;

import eventlistener.security.api.OfficeOAuthApi;
import eventlistener.security.model.api.office.OfficeUserDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class OAuthService {

    OfficeOAuthApi officeOAuthApi;
    JWTUtilService jwtUtilService;

    public OAuthService(OfficeOAuthApi officeOAuthApi, JWTUtilService jwtUtilService) {
        this.officeOAuthApi = officeOAuthApi;
        this.jwtUtilService = jwtUtilService;
    }

    public String getJwtTokenWithOfficeCode(String code){
        OfficeUserDTO user = officeOAuthApi.getOfficeUserInformation(code);
        return jwtUtilService.createToken(new HashMap<>(), user.getDisplayName());
    }
}
