package eventlistener.service.notificaionuser;

import eventlistener.model.Action;
import eventlistener.model.event.Event;
import eventlistener.model.notificationuser.NotificationUser;
import eventlistener.model.notificationuser.NotificationUserDTO;
import eventlistener.model.notificationuser.NotificationUserEditDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class NotificationUserMapperTest {

    private final NotificationUserMapper mapper = new NotificationUserMapper();

    @Test
    void testMapDTO() {
        //GIVEN
        NotificationUserDTO userDTO = NotificationUserDTO.builder()
                .name("Test1")
                .email("test@test.de")
                .listenEvents(List.of(123L, 456L))
                .build();
        NotificationUser expected = NotificationUser.builder()
                .email("test@test.de")
                .name("Test1")
                .build();
        //WHEN
        NotificationUser actual = mapper.mapDTO(userDTO);
        //THEN
        assertThat(actual, is(expected));
    }

    @Test
    void mapUserToEditUser() {
        //GIVEN
        NotificationUser user = NotificationUser.builder()
                .id(1)
                .name("Test")
                .email("test@test.de")
                .build();
        List<NotificationUser> users = List.of(
                NotificationUser.builder()
                        .id(1)
                        .email("test@test.de")
                        .name("User1")
                        .build(),
                NotificationUser.builder()
                        .id(2)
                        .email("test@test.de")
                        .name("User2")
                        .build()
        );
        List<Event> events = List.of(
                Event.builder()
                        .id(1)
                        .name("Erstes Event")
                        .description("Lala")
                        .actions(List.of(Action.MAIL))
                        .notificationUser(users)
                        .build(),
                Event.builder()
                        .id(2)
                        .name("Zweites Event")
                        .description("Lala")
                        .actions(List.of(Action.MAIL))
                        .notificationUser(users)
                        .build());
        NotificationUserEditDTO expected = NotificationUserEditDTO.builder()
                .name("Test")
                .id(1)
                .listenEvents(events)
                .email("test@test.de")
                .build();
        //WHEN
        NotificationUserEditDTO actual = mapper.mapUserToEditUser(user, events);
        //THEN
        assertThat(actual, is(expected));
    }

    @Test
    void mapToUser() {
        //GIVEN
        NotificationUser expected = NotificationUser.builder()
                .id(1)
                .name("Test")
                .email("test@test.de")
                .build();
        List<NotificationUser> users = List.of(
                NotificationUser.builder()
                        .id(1)
                        .email("test@test.de")
                        .name("User1")
                        .build(),
                NotificationUser.builder()
                        .id(2)
                        .email("test@test.de")
                        .name("User2")
                        .build()
        );
        List<Event> events = List.of(
                Event.builder()
                        .id(1)
                        .name("Erstes Event")
                        .description("Lala")
                        .actions(List.of(Action.MAIL))
                        .notificationUser(users)
                        .build(),
                Event.builder()
                        .id(2)
                        .name("Zweites Event")
                        .description("Lala")
                        .actions(List.of(Action.MAIL))
                        .notificationUser(users)
                        .build());
        NotificationUserEditDTO user = NotificationUserEditDTO.builder()
                .name("Test")
                .id(1)
                .listenEvents(events)
                .email("test@test.de")
                .build();
        //WHEN
        NotificationUser actual = mapper.mapToUser(user);
        //THEN
        assertThat(actual, is(expected));
    }
}