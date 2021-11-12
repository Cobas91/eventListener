package eventlistener.service.event;

import eventlistener.model.event.Event;
import eventlistener.model.event.EventToModifyDTO;
import eventlistener.model.event.ResponseEventDTO;
import eventlistener.model.notificationuser.NotificationUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventMapper {

    public List<ResponseEventDTO> mapEvent(List<Event> events){
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

    public EventToModifyDTO mapEvent(Event event, List<NotificationUser> userList) {
        return EventToModifyDTO.builder()
                .id(event.getId())
                .actions(event.getActions())
                .description(event.getDescription())
                .notificationUserIds(userList.stream().map(NotificationUser::getId).collect(Collectors.toList()))
                .build();
    }

    public Event mapEvent(EventToModifyDTO event, List<NotificationUser> users){
        return Event.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .actions(event.getActions())
                .notificationUser(users)
                .build();
    }

}
