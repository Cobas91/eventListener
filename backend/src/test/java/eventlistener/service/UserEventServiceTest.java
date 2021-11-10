package eventlistener.service;

import eventlistener.model.Action;
import eventlistener.model.event.Event;
import eventlistener.model.notificationuser.NotificationUser;
import eventlistener.model.notificationuser.NotificationUserDTO;
import eventlistener.model.notificationuser.NotificationUserEditDTO;
import eventlistener.repo.EventRepo;
import eventlistener.repo.NotificationUserRepo;
import eventlistener.service.event.EventService;
import eventlistener.service.notificaionuser.NotificationUserMapper;
import eventlistener.service.notificaionuser.NotificationUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

class UserEventServiceTest {



    EventService eventService = mock(EventService.class);
    EventRepo eventRepo = mock(EventRepo.class);

    NotificationUserService notificationUserService = mock(NotificationUserService.class);
    NotificationUserRepo notificationUserRepo = mock(NotificationUserRepo.class);

    NotificationUserMapper notificationUserMapper = new NotificationUserMapper();
    UserEventService userEventService = new UserEventService(eventService, notificationUserService, notificationUserMapper);

    @Test
    @DisplayName("Return a List of Notification User")
    void testGetAllUser() {
        //GIVEN
        List<NotificationUser> expected = List.of(
                NotificationUser.builder()
                        .name("TestUser1")
                        .email("test1@test.de")
                        .id(1)
                        .build(),
                NotificationUser.builder()
                        .name("TestUser2")
                        .email("test2@test.de")
                        .id(2)
                        .build()
        );
        //WHEN
        when(notificationUserService.getAllUser()).thenReturn(expected);
        List<NotificationUser> actual = userEventService.getAllUser();
        //THEN
        assertThat(actual, is(expected));
    }

    @Test
    @DisplayName("Return the new User with User ID.")
    void testAddUser() {
        //GIVEN
        List<Long> eventIds = List.of(1L, 2L);
        NotificationUserDTO userToAdd = NotificationUserDTO.builder()
                .name("TestUser")
                .email("tes@test.de")
                .listenEvents(eventIds)
                .build();
        NotificationUser addedUser = NotificationUser.builder()
                .id(1)
                .email("test@test.de")
                .name("TestUser")
                .build();
        //WHEN
        when(eventService.eventsExist(eventIds)).thenReturn(true);
        when(notificationUserService.addUser(userToAdd)).thenReturn(addedUser);

        NotificationUser actual = userEventService.addUser(userToAdd);
        //THEN
        assertThat(actual, is(addedUser));
        verify(eventService).eventsExist(eventIds);
        verify(notificationUserService).addUser(userToAdd);
    }

    @Test
    @DisplayName("Return a NotificationUserDTO including List of Event Ids")
    void testGetSingleUser() {
        //GIVEN
        long userIdToSearch = 1;
        List<Event> events = List.of(
                Event.builder()
                        .id(1)
                        .actions(List.of(Action.MAIL))
                        .name("Test Event 1")
                        .description("Beschreibung 1")
                        .build(),
                Event.builder()
                        .id(2)
                        .actions(List.of(Action.MAIL))
                        .name("Test Event 2")
                        .description("Beschreibung 2")
                        .build()
                );
        NotificationUserEditDTO expected = NotificationUserEditDTO.builder()
                .id(1)
                .listenEvents(events)
                .email("test@test.de")
                .name("TestUser")
                .build();
        NotificationUser user = NotificationUser.builder()
                .id(userIdToSearch)
                .name("TestUser")
                .email("test@test.de")
                .build();
        //WHEN
        when(notificationUserService.getSingleUser(userIdToSearch)).thenReturn(user);
        when(eventService.getAllEventsFromUser(user)).thenReturn(events);

        NotificationUserEditDTO actual = userEventService.getSingleUser(userIdToSearch);
        //THEN
        assertThat(actual, is(expected));
        verify(notificationUserService).getSingleUser(userIdToSearch);
        verify(eventService).getAllEventsFromUser(user);
    }

    @Test
    @DisplayName("Return a NotificationUser Object after setting all Events and save in DB")
    void testEditUser() {
        //GIVEN
        List<Event> events = List.of(
                Event.builder()
                        .id(1)
                        .actions(List.of(Action.MAIL))
                        .name("Test Event 1")
                        .description("Beschreibung 1")
                        .build(),
                Event.builder()
                        .id(2)
                        .actions(List.of(Action.MAIL))
                        .name("Test Event 2")
                        .description("Beschreibung 2")
                        .build()
        );
        NotificationUserEditDTO userToEdit = NotificationUserEditDTO.builder()
                .id(1)
                .email("test@test.de")
                .name("Test")
                .listenEvents(events)
                .build();
        NotificationUser expected = NotificationUser.builder()
                .id(1)
                .email("test@test.de")
                .name("Test")
                .build();
        long idToEdit = 1;
        //WHEN
        when(notificationUserService.getSingleUser(idToEdit)).thenReturn(expected);
        when(eventService.getAllEventsFromUser(expected)).thenReturn(events);
        when(notificationUserService.editUser(userToEdit)).thenReturn(expected);
        NotificationUser actual = userEventService.editUser(idToEdit, userToEdit);
        //THEN
        assertThat(actual, is(expected));

    }

    @Test
    @DisplayName("Return a List of Events from a given userId")
    void testGetAllEventsFromUser() {
        //GIVEN
        long userId = 1;
        NotificationUser user = NotificationUser.builder().id(userId).build();
        List<NotificationUser> users = List.of(user);
        List<Event> expected = List.of(
                Event.builder()
                        .id(1)
                        .actions(List.of(Action.MAIL))
                        .name("Test Event 1")
                        .description("Beschreibung 1")
                        .notificationUser(users)
                        .build(),
                Event.builder()
                        .id(2)
                        .actions(List.of(Action.MAIL))
                        .name("Test Event 2")
                        .description("Beschreibung 2")
                        .notificationUser(users)
                        .build()
        );
        //WHEN
        when(eventService.getAllEventsFromUser(user)).thenReturn(expected);
        List<Event> actual = userEventService.getAllEventsFromUser(userId);
        //THEN
        assertThat(actual, is(expected));
        verify(eventService).getAllEventsFromUser(user);
    }

}