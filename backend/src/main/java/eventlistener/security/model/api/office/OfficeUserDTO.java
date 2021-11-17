package eventlistener.security.model.api.office;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfficeUserDTO {
    String displayName;
    String givenName;
    String jobTitle;
}
