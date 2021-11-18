package eventlistener.model.notificationuser;

import eventlistener.model.event.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class NotificationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String email;
    private String name;
    @ManyToMany
    @JoinTable(
            name = "followed_events",
            joinColumns = @JoinColumn(name = "notificationUser_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> followedEvents;

    public void followEvent(Event event){
        followedEvents.add(event);
    }
}
