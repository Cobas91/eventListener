/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

package eventlistener.service;

import eventlistener.model.Action;
import eventlistener.model.statistic.RechartDataDTO;
import eventlistener.model.statistic.Statistic;
import eventlistener.model.event.Event;
import eventlistener.model.event.EventContentDTO;
import eventlistener.repo.StatistikRepo;
import eventlistener.service.action.ActionMapper;
import eventlistener.service.event.EventService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatistikService {
    StatistikRepo statistikRepo;
    EventService eventService;
    ActionMapper actionMapper;

    public StatistikService(StatistikRepo statistikRepo, EventService eventService, ActionMapper actionMapper) {
        this.statistikRepo = statistikRepo;
        this.eventService = eventService;
        this.actionMapper = actionMapper;
    }

    public void recordEvent(Action action, Long eventId, EventContentDTO eventDetails) {
        Event event = eventService.getSingleEvent(eventId);
        Statistic report = Statistic.builder()
                .triggeredEvent(event)
                .action(action)
                .content(eventDetails)
                .triggeredAt(LocalDateTime.now())
                .build();
        statistikRepo.save(report);
    }

    public List<Statistic> getAllStatistikObjects() {
        return statistikRepo.findAll();
    }

    public List<Statistic> getAllStatisticObjectsByAction(String action) {
        return statistikRepo.getStatisticByAction(actionMapper.mapAction(action));
    }

    public List<RechartDataDTO> getRechartData() {
        return buildRechartData();
    }

    private List<RechartDataDTO> buildRechartData(){
        List<RechartDataDTO> data = new java.util.ArrayList<>(List.of());
        for (Action action : Action.values()) {
            List<Statistic> statisticAction = getAllStatisticObjectsByAction(action.toString());
            data.add(RechartDataDTO.builder().name(action).amount(statisticAction.size()).build());
        }
        return data;
    }
}
