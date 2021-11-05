package eventlistener.service.notificaionuser;

import eventlistener.model.notificationuser.NotificationUser;
import eventlistener.model.notificationuser.NotificationUserDTO;
import eventlistener.model.notificationuser.NotificationUserEditDTO;
import eventlistener.service.event.EventService;
import org.springframework.stereotype.Service;


@Service
public class NotificationUserMapper {

    EventService eventService;

    public NotificationUserMapper(EventService eventService) {
        this.eventService = eventService;
    }

    public NotificationUser mapDTO(NotificationUserDTO dtoToMap){
        return NotificationUser.builder()
                .id(dtoToMap.getId())
                .name(dtoToMap.getName())
                .email(dtoToMap.getEmail())
                .build();
    }

    public NotificationUserEditDTO mapUserToEditUser(NotificationUser userToMap){
        return NotificationUserEditDTO.builder()
                .name(userToMap.getName())
                .id(userToMap.getId())
                .email(userToMap.getEmail())
                .listenEvents(eventService.getAllEventsFromUser(userToMap.getId()))
                .build();
    }
}
