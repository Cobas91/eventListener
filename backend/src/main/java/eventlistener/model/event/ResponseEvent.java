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
public class ResponseEvent {
    private String id;
    private String name;
    private List<Action> actions;
}
