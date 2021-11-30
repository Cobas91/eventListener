/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

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
