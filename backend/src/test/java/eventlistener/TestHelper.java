package eventlistener;

import eventlistener.model.Action;
import eventlistener.model.statistic.Statistic;
import eventlistener.model.event.Event;
import eventlistener.model.event.EventContentDTO;
import eventlistener.model.notificationuser.NotificationUser;
import eventlistener.repo.EventRepo;
import eventlistener.repo.NotificationUserRepo;
import eventlistener.repo.StatistikRepo;
import eventlistener.security.model.AppUserDTO;
import eventlistener.security.repo.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.List;
import java.util.Objects;

@Service
public class TestHelper {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    EventRepo eventRepo;
    @Autowired
    StatistikRepo statistikRepo;
    @Autowired
    NotificationUserRepo notificationUserRepo;
    @Autowired
    AppUserRepo appUserRepo;
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;


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

    public EventContentDTO getEventContentDTO(){
        return EventContentDTO.builder()
                .content("Content in Event")
                .commentExtract("No Comment in here")
                .commentUser("No User Comment in here")
                .customerRef("Kunde12345")
                .environmentName("Zap Audit 2.6")
                .project("Project 0815")
                .timestampLong(1235233214234L)
                .build();
    }

    public List<Statistic> getStatistikObjects(){
        return List.of(Statistic.
                        builder()
                        .action(Action.MAIL)
                        .content(getEventContentDTO())
                        .triggeredEvent(getEvent())
                        .build(),
                Statistic.
                        builder()
                        .action(Action.MAIL)
                        .content(getEventContentDTO())
                        .triggeredEvent(getEvent())
                        .build()
        );
    }
    public Statistic getStatistikObject(){
        return Statistic.builder()
                .action(Action.MAIL)
                .content(getEventContentDTO())
                .triggeredEvent(getEvent())
                .build();
    }

    public void clearDatabase(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "event_notification_user");
        notificationUserRepo.deleteAll();
        appUserRepo.deleteAll();
        eventRepo.deleteAll();
        statistikRepo.deleteAll();
    }

    public HttpHeaders getLoginHeader(){
        appUserRepo.save(AppUserDTO.builder()
                .username("test")
                .password(passwordEncoder.encode("some-password"))
                .build());
        AppUserDTO loginData = new AppUserDTO("test", "some-password");
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/login", loginData, String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(Objects.requireNonNull(response.getBody()));
        return headers;
    }
}
