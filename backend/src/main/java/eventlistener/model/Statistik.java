package eventlistener.model;

import eventlistener.model.event.Event;
import eventlistener.model.event.EventContentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Statistik {
    private Event triggeredEvent;
    private Action action;
    private EventContentDTO content;
    private LocalDateTime triggeredAt;
    private LocalDateTime finishedAt;
}
