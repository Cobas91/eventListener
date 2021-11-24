package eventlistener.repo;

import eventlistener.model.Action;
import eventlistener.model.statistic.Statistic;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StatistikRepo extends MongoRepository<Statistic, Long> {
    List<Statistic> getStatisticByAction(Action action);
}
