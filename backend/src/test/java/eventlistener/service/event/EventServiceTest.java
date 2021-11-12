package eventlistener.service.event;

import eventlistener.model.Action;
import eventlistener.model.event.Event;
import eventlistener.model.notificationuser.NotificationUser;
import eventlistener.repo.EventRepo;
import eventlistener.service.UserEventService;
import eventlistener.service.notificaionuser.NotificationUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

class EventServiceTest {

    EventRepo eventRepo = mock(EventRepo.class);

    EventMapper eventMapper = mock(EventMapper.class);

    EventService eventService = new EventService(eventRepo, eventMapper);


    @Test
    @DisplayName("Return all available events in a list")
    void testGetAllEvents() {
        //GIVEN
        List<Event> expected = List.of(
                Event.builder()
                        .id(1)
                        .name("TestEvent")
                        .actions(List.of(Action.MAIL))
                        .notificationUser(List.of(NotificationUser.builder().build(), NotificationUser.builder().build()))
                        .description("Test Event 1")
                        .build(),
                Event.builder()
                        .id(1)
                        .name("TestEvent")
                        .actions(List.of(Action.MAIL))
                        .notificationUser(List.of(NotificationUser.builder().build(), NotificationUser.builder().build()))
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
                .notificationUser(List.of(NotificationUser.builder().build(), NotificationUser.builder().build()))
                .actions(List.of(Action.MAIL))
                .build();
        Event expected = Event.builder()
                .id(1)
                .name("TestEvent")
                .description("UnitTest")
                .notificationUser(List.of(NotificationUser.builder().build(), NotificationUser.builder().build()))
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
        long idToFind = 123;
        NotificationUser userToFind = NotificationUser.builder().id(idToFind).build();
        List<Event> events = List.of(
                Event.builder()
                        .id(1)
                        .name("TestEvent")
                        .actions(List.of(Action.MAIL))
                        .notificationUser(List.of(NotificationUser.builder().build(), NotificationUser.builder().build()))
                        .description("Test Event 1")
                        .build(),
                Event.builder()
                        .id(2)
                        .name("TestEvent2")
                        .actions(List.of(Action.MAIL))
                        .notificationUser(List.of(NotificationUser.builder().build(), NotificationUser.builder().build()))
                        .description("Test Event 2")
                        .build(),
                Event.builder()
                        .id(3)
                        .name("TestEvent3")
                        .actions(List.of(Action.MAIL))
                        .notificationUser(List.of(NotificationUser.builder().build(), NotificationUser.builder().build()))
                        .description("Test Event 2")
                        .build()
        );
        List<Event> expected = List.of(
                Event.builder()
                        .id(1)
                        .name("TestEvent")
                        .actions(List.of(Action.MAIL))
                        .notificationUser(List.of(userToFind, NotificationUser.builder().build()))
                        .description("Test Event 1")
                        .build(),
                Event.builder()
                        .id(3)
                        .name("TestEvent3")
                        .actions(List.of(Action.MAIL))
                        .notificationUser(List.of(userToFind, NotificationUser.builder().build()))
                        .description("Test Event 2")
                        .build()
        );
        //WHEN
        when(eventRepo.findAllByNotificationUserContains(userToFind)).thenReturn(expected);
        //THEN
        List<Event> actual = eventService.getAllEventsFromUser(userToFind);
        assertThat(actual, is(expected));
        verify(eventRepo).findAllByNotificationUserContains(userToFind);
    }

}