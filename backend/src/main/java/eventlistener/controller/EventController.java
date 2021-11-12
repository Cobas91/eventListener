package eventlistener.controller;

import eventlistener.model.event.Event;
import eventlistener.model.event.EventContentDTO;
import eventlistener.service.EventMailService;
import eventlistener.service.UserEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
@Slf4j
public class EventController {

    private final UserEventService userEventService;

    private final EventMailService eventMailService;

    public EventController(UserEventService userEventService, EventMailService eventMailService) {
        this.userEventService = userEventService;
        this.eventMailService = eventMailService;
    }

    @GetMapping
    public List<Event> getAllEvents(){
        return userEventService.getAllEvents();
    }

    @GetMapping("/not/{excludedUserId}")
    public List<Event> getAllEventsExcludeUser(@PathVariable Long excludedUserId){
        return userEventService.getAllEventsExcludeUser(excludedUserId);
    }

    @GetMapping("/{eventId}")
    public Event getSingleEvent(@PathVariable Long eventId){
        return userEventService.getSingleEvent(eventId);
    }

    @PostMapping
    public Event addEvent(@RequestBody Event eventToAdd){
        return userEventService.addEvent(eventToAdd);
    }

    @PostMapping("/trigger/{eventId}")
    public void triggerEventAction(@PathVariable Long eventId, @RequestBody EventContentDTO eventDetails){
        eventMailService.triggerEvent(eventId, eventDetails);
    }
}
