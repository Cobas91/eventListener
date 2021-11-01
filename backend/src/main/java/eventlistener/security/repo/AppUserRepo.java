package eventlistener.security.repo;

import eventlistener.security.model.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepo extends MongoRepository<AppUser, String> {
}
