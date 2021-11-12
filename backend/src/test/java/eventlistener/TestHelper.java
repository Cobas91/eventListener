package eventlistener;

import eventlistener.model.Action;
import eventlistener.model.event.Event;
import eventlistener.model.notificationuser.NotificationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.List;

@Service
public class TestHelper {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void clearTable(String table){
        JdbcTestUtils.deleteFromTables(jdbcTemplate, table);
    }

    public List<NotificationUser> getListOfNotificationUser(){
        return List.of(
                NotificationUser.builder()
                        .name("User1")
                        .email("user1@test.de")
                        .build(),
                NotificationUser.builder()
                        .name("User2")
                        .email("user2@test.de")
                        .build()
        );
    }

    public NotificationUser getNotificationUser(){
        return NotificationUser.builder().email("user1@test.de").name("User1").build();
    }

    public Event getEvent(){
        return Event.builder()
                .name("Event1")
                .description("Erstes Event")
                .actions(List.of(Action.MAIL))
                .notificationUser(getListOfNotificationUser())
                .build();
    }

    public List<Event> getListOfEvents(){
        return List.of(
                Event.builder()
                        .name("Event1")
                        .description("Erstes Event")
                        .actions(List.of(Action.MAIL))
                        .notificationUser(getListOfNotificationUser())
                        .build(),
                Event.builder()
                        .name("Event2")
                        .description("Zweites Event")
                        .actions(List.of(Action.MAIL))
                        .notificationUser(getListOfNotificationUser())
                        .build()
        );
    }
}
