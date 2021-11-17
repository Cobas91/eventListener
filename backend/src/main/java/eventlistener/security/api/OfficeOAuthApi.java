package eventlistener.security.api;

import eventlistener.security.model.api.office.AccessTokenResponseDTO;
import eventlistener.security.model.api.office.OfficeUserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class OfficeOAuthApi {

    @Value("${office.client.id}")
    String clientId;

    @Value("${office.redirect.uri}")
    String redirectUri;

    @Value("${office.client.secret}")
    String clientSecret;

    @Value("${office.token.url}")
    String tokenUrl;

    RestTemplate restTemplate = new RestTemplate();

    private String getAccessToken(String code){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("grant_type", "authorization_code");
        map.add("scope", "user.read");
        map.add("code", code);
        map.add("redirect_uri", redirectUri);
        map.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<AccessTokenResponseDTO> response = restTemplate.postForEntity(tokenUrl, request, AccessTokenResponseDTO.class);

        return response.getBody().getAccessToken();
    }

    public OfficeUserDTO getOfficeUserInformation(String code){
        String token = getAccessToken(code);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        ResponseEntity<OfficeUserDTO> response = restTemplate.exchange("https://graph.microsoft.com/v1.0/me", HttpMethod.GET , new HttpEntity<>(headers), OfficeUserDTO.class);
        return response.getBody();
    }
}
