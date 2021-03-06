package eventlistener.controller;

import eventlistener.TestHelper;
import eventlistener.TestPostgresqlContainer;
import eventlistener.model.Action;
import eventlistener.model.event.Event;
import eventlistener.model.event.EventToModifyDTO;
import eventlistener.model.event.ResponseEventDTO;
import eventlistener.model.notificationuser.NotificationUser;
import eventlistener.repo.EventRepo;
import eventlistener.repo.NotificationUserRepo;

import eventlistener.security.repo.AppUserRepo;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class EventControllerTest {
    @Container
    public static PostgreSQLContainer<TestPostgresqlContainer> postgreSQLContainer = TestPostgresqlContainer.getInstance();

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

    @Autowired
    private TestHelper testHelper;


    @BeforeEach
    public void clearDB() {
        testHelper.clearDatabase();
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
                        .id(1)
                        .name("TestEvent")
                        .actions(List.of(Action.MAIL))
                        .notificationUser(users)
                        .description("Test Event 1")
                        .build(),
                Event.builder()
                        .id(2)
                        .name("TestEvent2")
                        .actions(List.of(Action.MAIL))
                        .notificationUser(users)
                        .description("Test Event 2")
                        .build()
        );
        eventRepo.saveAll(events);
        //WHEN
        ResponseEntity<ResponseEventDTO[]> responseEntity = restTemplate.exchange("/api/event", HttpMethod.GET, new HttpEntity<>(testHelper.getLoginHeader()), ResponseEventDTO[].class);
        //THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(Objects.requireNonNull(responseEntity.getBody()).length, is(2));
    }

    @Test
    @DisplayName("Return the new Added Event as Event")
    void testAddEvent() {
        //GIVEN
        NotificationUser user1 = notificationUserRepo.save(NotificationUser.builder().name("TestUser1").email("test@test.de").build());
        NotificationUser user2 = notificationUserRepo.save(NotificationUser.builder().name("TestUser2").email("test@test.de").build());
        List<Long> users = List.of(user1.getId(), user2.getId());
        EventToModifyDTO eventToAdd = EventToModifyDTO.builder()
                .name("TestEvent")
                .description("Integrationstest")
                .actions(List.of(Action.MAIL))
                .notificationUser(users)
                .build();
        //WHEN
        ResponseEntity<Event> responseEntity = restTemplate.exchange("/api/event", HttpMethod.POST, new HttpEntity<>(eventToAdd, testHelper.getLoginHeader()), Event.class);
        Optional<Event> actual = eventRepo.findById(Objects.requireNonNull(responseEntity.getBody()).getId());
        //THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(actual.isPresent(), is(true));
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getNotificationUser(), is(List.of(user1, user2)));
    }

    @Test
    @DisplayName("Return a Bad Request HTTP Status and throw MappingException")
    void testAddEventException() {
        //GIVEN
        Event eventToAdd = Event.builder()
                .id(1)
                .name("TestEvent")
                .description("Integrationstest")
                .actions(List.of(Action.MAIL))
                .notificationUser(List.of(NotificationUser.builder().build()))
                .build();
        //WHEN
        ResponseEntity<Event> responseEntity = restTemplate.exchange("/api/event", HttpMethod.POST, new HttpEntity<>(eventToAdd, testHelper.getLoginHeader()), Event.class);
        Optional<Event> actual = eventRepo.findById(1L);
        //THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    @DisplayName("Get all Events where the given UserId is not included")
    void testGetAllEventsExcludeUser() {
        //GIVEN
        NotificationUser user1 = NotificationUser.builder()
                .name("Hans")
                .email("test@test.de")
                .build();
        NotificationUser user2 = NotificationUser.builder()
                .name("Peter")
                .email("test@test.de")
                .build();
        NotificationUser user1WithId = notificationUserRepo.save(user1);
        NotificationUser user2WithId = notificationUserRepo.save(user2);
        List<NotificationUser> includeUsers = List.of(user1WithId, user2WithId);
        List<NotificationUser> excludeUsers = List.of(user2WithId);
        Event include = Event.builder().name("TestEvent").actions(List.of(Action.MAIL)).description("TestEvent Integrationstest").notificationUser(includeUsers).build();
        Event include2 = Event.builder().name("TestEvent2").actions(List.of(Action.MAIL)).description("TestEvent Integrationstest 2").notificationUser(includeUsers).build();
        Event expected = Event.builder().name("TestEvent3").actions(List.of(Action.MAIL)).description("TestEvent Integrationstest 3").notificationUser(excludeUsers).build();


        eventRepo.save(include);
        eventRepo.save(include2);
        Event expectedWithId = eventRepo.save(expected);
        //WHEN
        ResponseEntity<Event[]> responseEntity = restTemplate.exchange("/api/event/not/" + user1WithId.getId(), HttpMethod.GET, new HttpEntity<>(testHelper.getLoginHeader()), Event[].class);
        //THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), arrayContaining(expectedWithId));
    }

    @Test
    @DisplayName("Should Edit a given Event and return the new Event")
    void testEditEvent() {
        //GIVEN
        NotificationUser user1 = NotificationUser.builder()
                .name("Test User 1")
                .email("test@test.de")
                .build();
        NotificationUser user2 = NotificationUser.builder()
                .name("Test User 2")
                .email("test@test.de")
                .build();
        NotificationUser newUser = NotificationUser.builder()
                .name("Test User 2")
                .email("test@test.de")
                .build();
        NotificationUser user1WithId = notificationUserRepo.save(user1);
        NotificationUser user2WithId = notificationUserRepo.save(user2);
        NotificationUser newUserWithId = notificationUserRepo.save(newUser);

        Event dbEvent = Event.builder()
                .name("Integrationstest Event")
                .description("Integrationstest sind toll")
                .actions(List.of(Action.MAIL))
                .notificationUser(List.of(user1WithId, user2WithId))
                .build();
        Event eventWithId = eventRepo.save(dbEvent);

        long eventIdToEdit = eventWithId.getId();
        EventToModifyDTO eventToEdit = EventToModifyDTO.builder()
                .id(eventIdToEdit)
                .name("Integrationstest Event ge??ndert")
                .description("Integrationstest sind toll und funktionieren")
                .actions(List.of(Action.MAIL))
                .notificationUser(List.of(user1WithId.getId(), user2WithId.getId(), newUserWithId.getId()))
                .build();
        Event expected = Event.builder()
                .id(eventIdToEdit)
                .name("Integrationstest Event ge??ndert")
                .description("Integrationstest sind toll und funktionieren")
                .actions(List.of(Action.MAIL))
                .notificationUser(List.of(user1WithId, user2WithId, newUserWithId))
                .build();
        //WHEN
        ResponseEntity<Event> responseEntity = restTemplate.exchange("/api/event/" + eventIdToEdit, HttpMethod.PUT, new HttpEntity<>(eventToEdit, testHelper.getLoginHeader()), Event.class);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(expected));
    }

    @Test
    @DisplayName("Returns a List of ResponseEvents")
    void testGetSingleEvent() {
        //TODO
        //GIVEN
        List<NotificationUser> users = testHelper.getListOfNotificationUser();
        notificationUserRepo.saveAll(users);
        Event event = testHelper.getEvent();
        event.setNotificationUser(users);
        Event eventWithId = eventRepo.save(event);
        ResponseEventDTO expected = ResponseEventDTO.builder()
                .id(eventWithId.getId())
                .name(eventWithId.getName())
                .description(eventWithId.getDescription())
                .actions(eventWithId.getActions())
                .build();
        //WHEN
        ResponseEntity<ResponseEventDTO> responseEntity = restTemplate.exchange("/api/event/"+eventWithId.getId(), HttpMethod.GET, new HttpEntity<>(testHelper.getLoginHeader()), ResponseEventDTO.class);
        //THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(Objects.requireNonNull(responseEntity.getBody()), is(expected));
    }
}