package eventlistener.controller;

import eventlistener.model.event.Event;
import eventlistener.model.event.EventToModifyDTO;
import eventlistener.service.event.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getAllEvents(){
        return eventService.getAllEvents();
    }

    @GetMapping("/not/{excludedUserId}")
    public List<Event> getAllEvents(@PathVariable String excludedUserId){
        return eventService.getAllEventsExcludeUser(excludedUserId);
    }

    @GetMapping("/{eventId}")
    public EventToModifyDTO getSingleEvent(@PathVariable String eventId){
        return eventService.getSingleEvent(eventId);
    }

    @PostMapping
    public Event addEvent(@RequestBody Event eventToAdd){
        return eventService.addEvent(eventToAdd);
    }
}
