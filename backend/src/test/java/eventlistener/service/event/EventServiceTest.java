package eventlistener.service.event;

import eventlistener.model.Action;
import eventlistener.model.event.Event;
import eventlistener.model.event.ResponseEvent;
import eventlistener.repo.EventRepo;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceTest {

    EventRepo eventRepo = mock(EventRepo.class);


    EventService eventService = new EventService(eventRepo);


    @Test
    @DisplayName("Return all available events in a list")
    void testGetAllEvents() {
        //GIVEN
        List<Event> expected = List.of(
                Event.builder()
                        .id("1")
                        .name("TestEvent")
                        .actions(List.of(Action.MAIL))
                        .notificationUser(List.of("UserId123", "UserId456"))
                        .description("Test Event 1")
                        .build(),
                Event.builder()
                        .id("2")
                        .name("TestEvent")
                        .actions(List.of(Action.MAIL))
                        .notificationUser(List.of("UserId123", "UserId456"))
                        .description("Test Event 2")
                        .build()
        );
        //WHEN
        when(eventRepo.findAll()).thenReturn(expected);
        List<Event> actual = eventService.getAllEvents();
        //THEN
        assertThat(actual, is(expected));
        verify(eventRepo).findAll();

    }

    @Test
    @DisplayName("Return the new Added Event with ID")
    void testAddEvent() {
        //GIVEN
        Event eventToAdd = Event.builder()
                .name("TestEvent")
                .description("UnitTest")
                .notificationUser(List.of("ID1", "ID2"))
                .actions(List.of(Action.MAIL))
                .build();
        Event expected = Event.builder()
                .id("IdFromDatabase")
                .name("TestEvent")
                .description("UnitTest")
                .notificationUser(List.of("ID1", "ID2"))
                .actions(List.of(Action.MAIL))
                .build();
        //WHEN
        when(eventRepo.save(eventToAdd)).thenReturn(expected);
        Event actual = eventService.addEvent(eventToAdd);
        //THEN
        assertThat(actual, is(expected));
        verify(eventRepo).save(eventToAdd);
    }

    @Test
    @DisplayName("Return a List of Events where the given User is part of notification users")
    void testGetAllEventsFromUser() {
        //GIVEN
        String idToFound = "UserId123";
        List<Event> events = List.of(
                Event.builder()
                        .id("1")
                        .name("TestEvent")
                        .actions(List.of(Action.MAIL))
                        .notificationUser(List.of("UserId123", "UserId456"))
                        .description("Test Event 1")
                        .build(),
                Event.builder()
                        .id("2")
                        .name("TestEvent2")
                        .actions(List.of(Action.MAIL))
                        .notificationUser(List.of("UserId456"))
                        .description("Test Event 2")
                        .build(),
                Event.builder()
                        .id("3")
                        .name("TestEvent3")
                        .actions(List.of(Action.MAIL))
                        .notificationUser(List.of("UserId123", "UserId456"))
                        .description("Test Event 2")
                        .build()
        );
        List<Event> expected = List.of(
                Event.builder()
                        .id("1")
                        .name("TestEvent")
                        .actions(List.of(Action.MAIL))
                        .notificationUser(List.of("UserId123", "UserId456"))
                        .description("Test Event 1")
                        .build(),
                Event.builder()
                        .id("3")
                        .name("TestEvent3")
                        .actions(List.of(Action.MAIL))
                        .notificationUser(List.of("UserId123", "UserId456"))
                        .description("Test Event 2")
                        .build()
        );
        //WHEN
        when(eventRepo.findAllByNotificationUserContains(idToFound)).thenReturn(expected);
        //THEN
        List<Event> actual = eventService.getAllEventsFromUser(idToFound);
        assertThat(actual, is(expected));
        verify(eventRepo).findAllByNotificationUserContains(idToFound);
    }
}