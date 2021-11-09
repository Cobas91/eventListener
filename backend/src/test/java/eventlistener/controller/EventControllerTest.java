package eventlistener.controller;

import eventlistener.model.Action;
import eventlistener.model.event.Event;
import eventlistener.model.event.ResponseEventDTO;
import eventlistener.model.notificationuser.NotificationUser;
import eventlistener.repo.EventRepo;
import eventlistener.repo.NotificationUserRepo;
import eventlistener.security.model.AppUserDTO;
import eventlistener.security.repo.AppUserRepo;
import eventlistener.service.event.EventService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AppUserRepo appUserRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private NotificationUserRepo notificationUserRepo;

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

    @BeforeEach
    public void clearDB(){
        eventRepo.deleteAll();
    }
    @Test
    @DisplayName("Returns a List of ResponseEvents")
    void testGetAllEvents() {
        //GIVEN
        NotificationUser user1 = notificationUserRepo.save(NotificationUser.builder().name("TestUser1").email("test@test.de").build());
        NotificationUser user2 = notificationUserRepo.save(NotificationUser.builder().name("TestUser2").email("test@test.de").build());
        List<NotificationUser> users = List.of(user1, user2);
        List<Event> events = List.of(
                Event.builder()
                        .id("1")
                        .name("TestEvent")
                        .actions(List.of(Action.MAIL))
                        .notificationUser(users)
                        .description("Test Event 1")
                        .build(),
                Event.builder()
                        .id("2")
                        .name("TestEvent2")
                        .actions(List.of(Action.MAIL))
                        .notificationUser(users)
                        .description("Test Event 2")
                        .build()
        );
        eventRepo.saveAll(events);
        //WHEN
        ResponseEntity<ResponseEventDTO[]> responseEntity = restTemplate.exchange("/api/event", HttpMethod.GET , new HttpEntity<>(getLoginHeader()), ResponseEventDTO[].class);
        //THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), arrayContaining(
                ResponseEventDTO.builder()
                        .id("1")
                        .name("TestEvent")
                        .actions(List.of(Action.MAIL))
                        .description("Test Event 1")
                        .build(),
                ResponseEventDTO.builder()
                        .id("2")
                        .name("TestEvent2")
                        .actions(List.of(Action.MAIL))
                        .description("Test Event 2")
                        .build()));
    }

    @Test
    @DisplayName("Return the new Added Event as Event")
    void testAddEvent() {
        //GIVEN
        NotificationUser user1 = notificationUserRepo.save(NotificationUser.builder().name("TestUser1").email("test@test.de").build());
        NotificationUser user2 = notificationUserRepo.save(NotificationUser.builder().name("TestUser2").email("test@test.de").build());
        List<NotificationUser> users = List.of(user1, user2);
        Event eventToAdd = Event.builder()
                .id("1")
                .name("TestEvent")
                .description("Integrationstest")
                .actions(List.of(Action.MAIL))
                .notificationUser(users)
                .build();
        //WHEN
        ResponseEntity<Event> responseEntity = restTemplate.exchange("/api/event", HttpMethod.POST , new HttpEntity<>(eventToAdd, getLoginHeader()), Event.class);
        Optional<Event> actual = eventRepo.findById("1");
        //THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(actual, is(Optional.of(eventToAdd)));
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getNotificationUser(), is(users));
    }

    @Test
    @DisplayName("Return a Bad Request HTTP Status and throw MappingException")
    void testAddEventException() {
        //GIVEN
        Event eventToAdd = Event.builder()
                .id("1")
                .name("TestEvent")
                .description("Integrationstest")
                .actions(List.of(Action.MAIL))
                .notificationUser(List.of(NotificationUser.builder().build()))
                .build();
        //WHEN
        ResponseEntity<Event> responseEntity = restTemplate.exchange("/api/event", HttpMethod.POST , new HttpEntity<>(eventToAdd, getLoginHeader()), Event.class);
        Optional<Event> actual = eventRepo.findById("1");
        //THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    @DisplayName("Get all Events where the given UserId is not included")
    public void testGetAllEventsExcludeUser(){
        //GIVEN
        String userId = "1";
        List<NotificationUser> includeUsers = List.of(
                NotificationUser.builder()
                        .id("1")
                        .name("Hans")
                        .email("test@test.de")
                        .build(),
                NotificationUser.builder()
                        .id("2")
                        .name("Peter")
                        .email("test@test.de")
                        .build()
        );
        List<NotificationUser> excludeUsers = List.of(
                NotificationUser.builder()
                        .id("3")
                        .name("Hans")
                        .email("test@test.de")
                        .build(),
                NotificationUser.builder()
                        .id("4")
                        .name("Peter")
                        .email("test@test.de")
                        .build()
        );
        Event include = Event.builder().id("1").name("TestEvent").actions(List.of(Action.MAIL)).description("TestEvent Integrationstest").notificationUser(includeUsers).build();
        Event include2 = Event.builder().id("2").name("TestEvent2").actions(List.of(Action.MAIL)).description("TestEvent Integrationstest 2").notificationUser(includeUsers).build();
        Event exclude = Event.builder().id("3").name("TestEvent3").actions(List.of(Action.MAIL)).description("TestEvent Integrationstest 3").notificationUser(excludeUsers).build();
        eventRepo.save(include);
        eventRepo.save(include2);
        eventRepo.save(exclude);
        //WHEN
        ResponseEntity<Event[]> responseEntity = restTemplate.exchange("/api/event/not/"+userId, HttpMethod.GET , new HttpEntity<>(getLoginHeader()), Event[].class);
        //THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), arrayContaining(exclude));
    }
}