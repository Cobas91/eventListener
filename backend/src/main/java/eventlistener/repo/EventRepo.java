package eventlistener.repo;

import eventlistener.model.event.Event;
import eventlistener.model.notificationuser.NotificationUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventRepo extends MongoRepository<Event, String> {
    List<Event> findAllByNotificationUserContains(NotificationUser notificationUser);

    List<Event> findAllByNotificationUserNotContains(NotificationUser notificationUser);
}
