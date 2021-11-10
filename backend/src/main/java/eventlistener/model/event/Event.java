package eventlistener.model.event;

import eventlistener.model.Action;
import eventlistener.model.notificationuser.NotificationUser;
import lombok.*;


import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @ElementCollection
    private List<Action> actions;
    private String description;
    @ManyToMany
    private List<NotificationUser> notificationUser;
}
