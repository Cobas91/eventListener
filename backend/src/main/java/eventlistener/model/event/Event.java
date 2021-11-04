package eventlistener.model.event;

import eventlistener.model.Action;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

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
    private List<String> notificationUser;
}
