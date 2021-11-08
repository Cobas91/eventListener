package eventlistener.service;

import eventlistener.exception.EventNotFoundException;
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
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationUserServiceTest {

    NotificationUserRepo notificationUserRepo = mock(NotificationUserRepo.class);

    EventRepo eventRepo = mock(EventRepo.class);

    UserEventService userEventService = mock(UserEventService.class);
    NotificationUserMapper notificationUserMapper = new NotificationUserMapper();

    NotificationUserService notificationUserService = new NotificationUserService(notificationUserRepo, eventRepo, notificationUserMapper, userEventService);

    @Test
    @DisplayName("Should a return a List of Notification Users")
    void testGetAllUser() {
        //GIVEN
        List<NotificationUser> users = List.of(
                NotificationUser.builder()
                        .email("test@test.de")
                        .name("Herr.Test")
                        .build(),
                NotificationUser.builder()
                        .email("test@test.de")
                        .name("Frau.Test")
                        .build()
        );
        when(notificationUserRepo.findAll()).thenReturn(users);

        //WHEN
        List<NotificationUser> actual = notificationUserService.getAllUser();
        //THEN
        assertThat(actual, is(users));
        verify(notificationUserRepo).findAll();
    }
    @Test
    @DisplayName("Should a return empty list")
    void testGetAllUserEmptyDB() {
        //GIVEN
        when(notificationUserRepo.findAll()).thenReturn(List.of());

        //WHEN
        List<NotificationUser> actual = notificationUserService.getAllUser();
        //THEN
        assertThat(actual, is(List.of()));
        verify(notificationUserRepo).findAll();
    }

    @Test
    @DisplayName("Should Return added User.")
    void testAddUser() {
        //GIVEN
        NotificationUserDTO userToAddDTO = NotificationUserDTO.builder()
                .email("test@test.de")
                .name("Herr.Test")
                .build();

        NotificationUser userToAdd = NotificationUser.builder()
                .email("test@test.de")
                .name("Herr.Test")
                .build();
        when(userEventService.eventsExist(userToAddDTO.getListenEvents())).thenReturn(true);
        when(notificationUserRepo.save(userToAdd)).thenReturn(userToAdd);

        //WHEN
        NotificationUser actual = notificationUserService.addUser(userToAddDTO);
        //THEN
        assertThat(actual, is(userToAdd));
        verify(notificationUserRepo).save(userToAdd);
    }

    @Test
    @DisplayName("Should Return a EventNotFoundException")
    void testAddUserNoEventsFound() {
        //GIVEN
        NotificationUserDTO userToAddDTO = NotificationUserDTO.builder()
                .email("test@test.de")
                .name("Herr.Test")
                .build();

        when(userEventService.eventsExist(userToAddDTO.getListenEvents())).thenReturn(false);

        //WHEN
        //THEN
        assertThrows(EventNotFoundException.class, ()-> notificationUserService.addUser(userToAddDTO));
        verify(userEventService).eventsExist(userToAddDTO.getListenEvents());
    }


    @Test
    @DisplayName("Should return a single user by id.")
    void testGetSingleUser() {
        //GIVEN
        NotificationUser userToFind = NotificationUser.builder()
                .email("test@test.de")
                .name("Herr.Test")
                .build();
        NotificationUserEditDTO userToFindDTO = NotificationUserEditDTO.builder()
                .email("test@test.de")
                .name("Herr.Test")
                .listenEvents(List.of())
                .build();
        when(notificationUserRepo.findById("test@test.de")).thenReturn(Optional.of(userToFind));
        //WHEN
        NotificationUserEditDTO actual = notificationUserService.getSingleUser("test@test.de");
        //THEN

        assertThat(actual, is(userToFindDTO));
        verify(notificationUserRepo).findById("test@test.de");
    }

    @Test
    @DisplayName("Should return a NoSuchElementException")
    void testGetSingleUserNotFound() {
        //GIVEN
        NotificationUser userToFind = NotificationUser.builder()
                .email("test@test.de")
                .name("Herr.Test")
                .build();
        when(notificationUserRepo.findById("test@test.de")).thenReturn(Optional.empty());
        //WHEN
        Exception exception = assertThrows(NoSuchElementException.class, ()-> notificationUserService.getSingleUser("test@test.de"));
        //THEN

        assertThat(exception.getMessage(), is("Cant find User with id "+userToFind.getEmail()));
        verify(notificationUserRepo).findById("test@test.de");
    }
}