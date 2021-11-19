package eventlistener.service;

import eventlistener.model.Action;
import eventlistener.model.Statistik;
import eventlistener.model.event.Event;
import eventlistener.model.event.EventContentDTO;
import eventlistener.repo.StatistikRepo;
import eventlistener.service.event.EventService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StatistikService {
    StatistikRepo statistikRepo;
    EventService eventService;

    public StatistikService(StatistikRepo statistikRepo, EventService eventService) {
        this.statistikRepo = statistikRepo;
        this.eventService = eventService;
    }

    public void recordEvent(Action action, Long eventId, EventContentDTO eventDetails) {
        Event event = eventService.getSingleEvent(eventId);
        Statistik report = Statistik.builder()
                .triggeredEvent(event)
                .action(action)
                .content(eventDetails)
                .triggeredAt(LocalDateTime.now())
                .build();
        statistikRepo.save(report);
    }
}
