package eventlistener.service;

import eventlistener.model.Action;
import eventlistener.model.event.Event;
import eventlistener.model.event.EventContentDTO;
import eventlistener.service.event.EventService;
import eventlistener.service.mail.MailService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EventMailService {
    private final EventService eventService;
    private final MailService mailService;


    public EventMailService(EventService eventService, MailService mailService) {
        this.eventService = eventService;
        this.mailService = mailService;

    }

    public void triggerEvent(Long eventId, EventContentDTO eventDetails){
        Event event = eventService.getSingleEvent(eventId);
        List<Action> actions = event.getActions();

        for (Action action : actions) {
            if(action == Action.MAIL){
               mailService.handleMailAction(eventDetails, event.getNotificationUser());
            }
        }

        //Get Event -> Event Exists? -> Not Exist -> Throw NoSuchElementException
        //Get Event -> Event Exists? -> Exists -> getNotificationUsers -> Send Mail to all User based on Mail Template in Event Action
    }
}
