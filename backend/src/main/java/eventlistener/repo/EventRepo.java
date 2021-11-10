package eventlistener.repo;

import eventlistener.model.event.Event;
import eventlistener.model.notificationuser.NotificationUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepo extends JpaRepository<Event, Long> {

    List<Event> findAllByNotificationUserContains(NotificationUser notificationUser);

    List<Event> findAllByNotificationUserNotContaining(NotificationUser notificationUser);

}
