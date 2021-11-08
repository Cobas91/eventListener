package eventlistener.repo;

import eventlistener.model.event.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventRepo extends MongoRepository<Event, String> {
    List<Event> findAllByNotificationUserContains(String userId);

    List<Event> findAllByNotificationUserNotContains(String userId);
}
