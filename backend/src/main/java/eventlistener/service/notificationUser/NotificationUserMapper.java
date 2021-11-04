package eventlistener.service.notificationUser;

import eventlistener.model.notificationUser.NotificationUser;
import eventlistener.model.notificationUser.NotificationUserDTO;
import org.springframework.stereotype.Service;

@Service
public class NotificationUserMapper {


    public NotificationUser mapDTO(NotificationUserDTO dtoToMap){
        return NotificationUser.builder()
                .name(dtoToMap.getName())
                .email(dtoToMap.getEmail())
                .build();
    }
}
