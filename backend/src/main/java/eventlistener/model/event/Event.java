package eventlistener.model.event;

import eventlistener.model.Action;
import eventlistener.model.notificationuser.NotificationUser;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {
    @Id
    private String id;
    private String name;
    private List<Action> actions;
    private String description;
    @DBRef
    private List<NotificationUser> notificationUser;
}
