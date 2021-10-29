package eventlistener.service;

import eventlistener.model.NotificationUser;
import eventlistener.repo.NotificationUserRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationUserServiceTest {

    NotificationUserRepo notificationUserRepo = mock(NotificationUserRepo.class);

    NotificationUserService notificationUserService = new NotificationUserService(notificationUserRepo);

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
        NotificationUser userToAdd = NotificationUser.builder()
                .email("test@test.de")
                .name("Herr.Test")
                .build();
        when(notificationUserRepo.save(userToAdd)).thenReturn(userToAdd);

        //WHEN
        NotificationUser actual = notificationUserService.addUser(userToAdd);
        //THEN
        assertThat(actual, is(userToAdd));
        verify(notificationUserRepo).save(userToAdd);
    }

    @Test
    @DisplayName("Should Return added User.")
    void testAddUserIllegalArgumentException() {
        //GIVEN
        NotificationUser userToAdd = NotificationUser.builder()
                .email("test@test.de")
                .name("Herr.Test")
                .build();
        when(notificationUserRepo.save(userToAdd)).thenThrow(new IllegalArgumentException("Test Exception where thrown"));

        //THEN //WHEN
        Exception exception = assertThrows(IllegalArgumentException.class, ()-> notificationUserService.addUser(userToAdd));
        assertThat(exception.getMessage(), is("Test Exception where thrown"));
        verify(notificationUserRepo).save(userToAdd);
    }

    @Test
    @DisplayName("Should return a single user by id.")
    void testGetSingleUser() {
        //GIVEN
        NotificationUser userToFind = NotificationUser.builder()
                .email("test@test.de")
                .name("Herr.Test")
                .build();
        when(notificationUserRepo.findById("test@test.de")).thenReturn(Optional.of(userToFind));
        //WHEN
        NotificationUser actual = notificationUserService.getSingleUser("test@test.de");
        //THEN

        assertThat(actual, is(userToFind));
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