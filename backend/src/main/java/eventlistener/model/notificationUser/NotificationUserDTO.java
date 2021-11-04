package eventlistener.model.notificationUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationUserDTO {
    private String id;
    private String email;
    private String name;
    private List<String> listenEvents;
}
