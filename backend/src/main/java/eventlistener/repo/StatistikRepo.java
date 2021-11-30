/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

package eventlistener.repo;

import eventlistener.model.Action;
import eventlistener.model.statistic.Statistic;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StatistikRepo extends MongoRepository<Statistic, Long> {
    List<Statistic> getStatisticByAction(Action action);
}
