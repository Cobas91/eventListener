package eventlistener.service.notificaionuser;

import eventlistener.model.event.Event;
import eventlistener.model.notificationUser.NotificationUser;
import eventlistener.model.notificationUser.NotificationUserDTO;
import eventlistener.service.event.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationUserMapper {

    EventService eventService;

    public NotificationUserMapper(EventService eventService) {
        this.eventService = eventService;
    }

    public NotificationUser mapDTO(NotificationUserDTO dtoToMap){
        return NotificationUser.builder()
                .name(dtoToMap.getName())
                .email(dtoToMap.getEmail())
                .build();
    }

    public NotificationUserDTO mapUser(NotificationUser userToMap){
        List<String> eventIds = eventService.getAllEventsFromUser(userToMap.getId()).stream().map(Event::getId).collect(Collectors.toList());
        return NotificationUserDTO.builder()
                .name(userToMap.getName())
                .id(userToMap.getId())
                .email(userToMap.getEmail())
                .listenEvents(eventIds)
                .build();
    }
}
