package eventlistener.service.notificaionuser;

import eventlistener.model.notificationuser.NotificationUser;
import eventlistener.model.notificationuser.NotificationUserDTO;
import eventlistener.model.notificationuser.NotificationUserEditDTO;
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
