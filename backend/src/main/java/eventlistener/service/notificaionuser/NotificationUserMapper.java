package eventlistener.service.notificaionuser;

import eventlistener.model.event.Event;
import eventlistener.model.notificationuser.NotificationUser;
import eventlistener.model.notificationuser.NotificationUserDTO;
import eventlistener.model.notificationuser.NotificationUserEditDTO;
import eventlistener.service.event.EventService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NotificationUserMapper {

    public NotificationUser mapDTO(NotificationUserDTO dtoToMap){
        return NotificationUser.builder()
                .id(dtoToMap.getId())
                .name(dtoToMap.getName())
                .email(dtoToMap.getEmail())
                .build();
    }

    public NotificationUserEditDTO mapUserToEditUser(NotificationUser userToMap, List<Event> events){
        return NotificationUserEditDTO.builder()
                .name(userToMap.getName())
                .id(userToMap.getId())
                .email(userToMap.getEmail())
                .listenEvents(events)
                .build();
    }

}
