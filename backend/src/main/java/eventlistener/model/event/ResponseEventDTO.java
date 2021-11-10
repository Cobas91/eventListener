package eventlistener.model.event;

import eventlistener.model.Action;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseEventDTO {
    private long id;
    private String name;
    private String description;
    private List<Action> actions;
}
