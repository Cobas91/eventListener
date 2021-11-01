package eventlistener.security.repo;

import eventlistener.security.model.AppUserDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepo extends MongoRepository<AppUserDTO, String> {
}
