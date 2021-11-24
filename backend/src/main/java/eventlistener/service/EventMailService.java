package eventlistener.service;


import eventlistener.model.event.Event;
import eventlistener.model.event.EventContentDTO;
import eventlistener.service.event.EventService;
import eventlistener.service.mail.MailService;
import org.springframework.stereotype.Service;



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
        mailService.handleMailAction(eventDetails, event.getNotificationUser());
    }
}
