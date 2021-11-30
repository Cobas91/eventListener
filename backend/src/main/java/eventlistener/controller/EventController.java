/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

package eventlistener.controller;


import eventlistener.model.event.Event;
import eventlistener.model.event.EventContentDTO;
import eventlistener.model.event.EventToModifyDTO;
import eventlistener.service.EventTriggerService;
import eventlistener.service.UserEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
@Slf4j
public class EventController {

    private final UserEventService userEventService;

    private final EventTriggerService eventTriggerService;

    public EventController(UserEventService userEventService, EventTriggerService eventTriggerService) {
        this.userEventService = userEventService;
        this.eventTriggerService = eventTriggerService;
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
    public Event addEvent(@RequestBody EventToModifyDTO eventToAdd){
        return userEventService.addEvent(eventToAdd);
    }

    @PostMapping("/trigger/{eventId}/{actionString}")
    public void triggerEventAction(@PathVariable Long eventId, @PathVariable String actionString, @RequestBody EventContentDTO eventDetails){
        eventTriggerService.triggerEvent(eventId,actionString,eventDetails);
    }

    @PutMapping("/{eventId}")
    public Event editEvent(@PathVariable Long eventId, @RequestBody EventToModifyDTO event){
        return userEventService.editEvent(eventId, event);
    }

    @DeleteMapping("/{eventId}")
    public String deleteEvent(@PathVariable Long eventId){
        return userEventService.deleteEvent(eventId);
    }
}
