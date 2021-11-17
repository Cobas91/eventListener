package eventlistener.security.service;

import eventlistener.exception.OfficeAccessTokenException;
import eventlistener.exception.OfficeOAuthException;
import eventlistener.security.api.OfficeOAuthApi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OAuthServiceTest {

    OfficeOAuthApi officeOAuthApi = mock(OfficeOAuthApi.class);
    JWTUtilService jwtUtilService = mock(JWTUtilService.class);

    OAuthService oAuthService = new OAuthService(officeOAuthApi, jwtUtilService);

    @Test
    @DisplayName("Should Return a OfficeAccessTokenException, no valid Code input")
    void testGetJwtTokenWithOfficeCode() {
        //GIVEN
        String code = "TestCode";
        String exceptionMessage = "Cant handle Code";

        //WHEN
        when(officeOAuthApi.getOfficeUserInformation(code)).thenThrow(new OfficeAccessTokenException(exceptionMessage));
        Exception exception = assertThrows(OfficeAccessTokenException.class, ()-> oAuthService.getJwtTokenWithOfficeCode(code));
        //THEN

    }

    @Test
    @DisplayName("Should Return a OfficeOAuthException, no valid User Information")
    void testGetJwtTokenWithOfficeCodeNoValidUserInformation() {
        //GIVEN
        String code = "TestCode";
        String exceptionMessage = "No valid User Information";

        //WHEN
        when(officeOAuthApi.getOfficeUserInformation(code)).thenThrow(new OfficeOAuthException(exceptionMessage));
        Exception exception = assertThrows(OfficeOAuthException.class, ()-> oAuthService.getJwtTokenWithOfficeCode(code));
        //THEN

    }
}