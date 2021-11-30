/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

package eventlistener.repo;

import eventlistener.model.event.Event;
import eventlistener.model.notificationuser.NotificationUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepo extends JpaRepository<Event, Long> {

    List<Event> findAllByNotificationUserContains(NotificationUser notificationUser);

    List<Event> findAllByNotificationUserNotContaining(NotificationUser notificationUser);

}
