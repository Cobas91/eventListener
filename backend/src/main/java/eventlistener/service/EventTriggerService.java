package eventlistener.service;

import eventlistener.exception.ActionNotConfiguredException;
import eventlistener.exception.EventNotFoundException;
import eventlistener.model.Action;
import eventlistener.model.event.EventContentDTO;
import eventlistener.service.action.ActionMapper;
import eventlistener.service.event.EventService;
import org.springframework.stereotype.Service;


@Service
public class EventTriggerService {
    EventMailService eventMailService;
    StatistikService statistikService;
    EventService eventService;
    ActionMapper actionMapper;

    public EventTriggerService(EventMailService eventMailService, StatistikService statistikService, EventService eventService, ActionMapper actionMapper) {
        this.eventMailService = eventMailService;
        this.statistikService = statistikService;
        this.eventService = eventService;
        this.actionMapper = actionMapper;
    }

    public void triggerEvent(Long eventId, String actionString, EventContentDTO eventDetails){
        Action action = actionMapper.mapAction(actionString);
        if(eventExists(eventId)){
            triggerAction(eventId, action, eventDetails);
            statistikService.recordEvent(action, eventId, eventDetails);
        }else{
            throw new EventNotFoundException("Event with ID: "+eventId+" does not exist.");
        }

    }

    private void triggerAction(Long eventId, Action action, EventContentDTO eventDetails){
        switch (action){
            case MAIL:
                eventMailService.triggerEvent(eventId, eventDetails);
                break;
            case SLACK:
                throw new ActionNotConfiguredException("This Action is not configured yet.");
            default:
                throw new ActionNotConfiguredException("No valid Action for this event.");
        }
    }

    private boolean eventExists(Long eventId){
        return eventService.eventExist(eventId);
    }
}
