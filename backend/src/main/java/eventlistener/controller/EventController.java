package eventlistener.controller;

import eventlistener.model.event.Event;
import eventlistener.service.EventService;
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

    @PostMapping
    public Event addEvent(@RequestBody Event eventToAdd){
        return eventService.addEvent(eventToAdd);
    }
}
