package eventlistener.repo;

import eventlistener.model.event.Event;
import eventlistener.model.notificationuser.NotificationUser;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EventRepo extends PagingAndSortingRepository<Event, Long> {
    @Override
    List<Event> findAll();
    List<Event> findAllByNotificationUserContains(NotificationUser notificationUser);

    List<Event> findAllByNotificationUserNotContaining(NotificationUser notificationUser);
}
