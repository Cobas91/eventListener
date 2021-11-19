package eventlistener.repo;

import eventlistener.model.Statistik;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatistikRepo extends MongoRepository<Statistik, Long> {
}
