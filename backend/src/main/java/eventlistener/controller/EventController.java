package eventlistener.controller;

import eventlistener.model.event.Event;
import eventlistener.model.event.EventToModifyDTO;
import eventlistener.service.UserEventService;
import eventlistener.service.event.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {

    private final UserEventService userEventService;

    public EventController(UserEventService userEventService) {
        this.userEventService = userEventService;
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
}
