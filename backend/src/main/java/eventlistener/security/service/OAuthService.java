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

    public String getOfficeAccessToken(String code){
        OfficeUserDTO user = officeOAuthApi.getOfficeUserInformation(code);
        return jwtUtilService.createToken(new HashMap<>(), user.getDisplayName());
    }
}
