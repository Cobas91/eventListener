/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

package eventlistener.service.notificationuser;

import eventlistener.model.event.Event;
import eventlistener.model.notificationuser.NotificationUser;
import eventlistener.model.notificationuser.NotificationUserDTO;
import eventlistener.model.notificationuser.NotificationUserEditDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NotificationUserMapper {

    public NotificationUser mapNotificationUser(NotificationUserDTO dtoToMap){
        return NotificationUser.builder()
                .name(dtoToMap.getName())
                .email(dtoToMap.getEmail())
                .build();
    }

    public NotificationUserEditDTO MapNotificationUser(NotificationUser userToMap, List<Event> events){
        return NotificationUserEditDTO.builder()
                .name(userToMap.getName())
                .id(userToMap.getId())
                .email(userToMap.getEmail())
                .listenEvents(events)
                .build();
    }

    public NotificationUser mapNotificationUser(NotificationUserEditDTO userToMap){
        return NotificationUser.builder()
                .id(userToMap.getId())
                .email(userToMap.getEmail())
                .name(userToMap.getName())
                .build();
    }

}
