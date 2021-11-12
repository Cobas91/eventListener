package eventlistener.service.event;

import eventlistener.model.event.Event;
import eventlistener.model.event.EventToModifyDTO;
import eventlistener.model.event.ResponseEventDTO;
import eventlistener.model.notificationuser.NotificationUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventMapper {

    public List<ResponseEventDTO> mapDtoToEvent(List<Event> events){
        List<ResponseEventDTO> mappedEvents = new ArrayList<>();
        for (Event event : events) {
            mappedEvents.add(
                    ResponseEventDTO.builder()
                            .name(event.getName())
                            .id(event.getId())
                            .actions(event.getActions())
                            .description(event.getDescription())
                            .build());
        }
        return mappedEvents;
    }

    public EventToModifyDTO mapToModify(Event event, List<NotificationUser> userList) {
        return EventToModifyDTO.builder()
                .id(event.getId())
                .actions(event.getActions())
                .description(event.getDescription())
                .notificationUser(userList)
                .build();
    }

}
