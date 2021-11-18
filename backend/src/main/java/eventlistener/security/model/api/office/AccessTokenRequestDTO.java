package eventlistener.security.model.api.office;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccessTokenRequestDTO {
    String client_id;
    String scope;
    String code;
    String redirect_uri;
    String client_secret;
}
