package eventlistener.model.notificationuser;

import eventlistener.model.event.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationUserEditDTO {
    private String id;
    private String email;
    private String name;
    private List<Event> listenEvents;
}
