package eventlistener.repo;

import eventlistener.model.event.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepo extends MongoRepository<Event, String> {
}
