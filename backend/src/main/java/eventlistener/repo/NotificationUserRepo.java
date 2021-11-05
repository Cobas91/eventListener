package eventlistener.repo;

import eventlistener.model.notificationuser.NotificationUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationUserRepo extends MongoRepository<NotificationUser, String> {
}
