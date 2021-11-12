package eventlistener.controller;

import eventlistener.TestHelper;
import eventlistener.model.Action;
import eventlistener.model.event.Event;
import eventlistener.model.event.EventToModifyDTO;
import eventlistener.model.event.ResponseEventDTO;
import eventlistener.model.notificationuser.NotificationUser;
import eventlistener.repo.EventRepo;
import eventlistener.repo.NotificationUserRepo;
import eventlistener.security.model.AppUserDTO;
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
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
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

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer()
            .withDatabaseName("eventListener_test")
            .withUsername("eventListener")
            .withPassword("eventListener");

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
        testHelper.clearTable("event_notification_user");
        notificationUserRepo.deleteAll();
        appUserRepo.deleteAll();
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
        ResponseEntity<ResponseEventDTO[]> responseEntity = restTemplate.exchange("/api/event", HttpMethod.GET , new HttpEntity<>(getLoginHeader()), ResponseEventDTO[].class);
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
        List<NotificationUser> users = List.of(user1, user2);
        Event eventToAdd = Event.builder()
                .name("TestEvent")
                .description("Integrationstest")
                .actions(List.of(Action.MAIL))
                .notificationUser(users)
                .build();
        //WHEN
        ResponseEntity<Event> responseEntity = restTemplate.exchange("/api/event", HttpMethod.POST , new HttpEntity<>(eventToAdd, getLoginHeader()), Event.class);
        Optional<Event> actual = eventRepo.findById(responseEntity.getBody().getId());
        //THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(actual.isPresent(), is(true));
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getNotificationUser(), is(users));
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
        ResponseEntity<Event> responseEntity = restTemplate.exchange("/api/event", HttpMethod.POST , new HttpEntity<>(eventToAdd, getLoginHeader()), Event.class);
        Optional<Event> actual = eventRepo.findById(1L);
        //THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    @DisplayName("Get all Events where the given UserId is not included")
    void testGetAllEventsExcludeUser(){
        //GIVEN
        long userId = 1;
        NotificationUser user1 = NotificationUser.builder()
                .id(userId)
                .name("Hans")
                .email("test@test.de")
                .build();
        NotificationUser user2 = NotificationUser.builder()
                .id(2)
                .name("Peter")
                .email("test@test.de")
                .build();
        List<NotificationUser> includeUsers = List.of(user1,user2);
        List<NotificationUser> excludeUsers = List.of(user2);
        Event include = Event.builder().id(1).name("TestEvent").actions(List.of(Action.MAIL)).description("TestEvent Integrationstest").notificationUser(includeUsers).build();
        Event include2 = Event.builder().id(2).name("TestEvent2").actions(List.of(Action.MAIL)).description("TestEvent Integrationstest 2").notificationUser(includeUsers).build();
        Event exclude = Event.builder().id(3).name("TestEvent3").actions(List.of(Action.MAIL)).description("TestEvent Integrationstest 3").notificationUser(excludeUsers).build();

        notificationUserRepo.save(user1);
        notificationUserRepo.save(user2);

        eventRepo.save(include);
        eventRepo.save(include2);
        eventRepo.save(exclude);
        //WHEN
        ResponseEntity<Event[]> responseEntity = restTemplate.exchange("/api/event/not/"+userId, HttpMethod.GET , new HttpEntity<>(getLoginHeader()), Event[].class);
        //THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), arrayContaining(exclude));
    }

    @Test
    @DisplayName("Should Edit a given Event and return the new Event")
    void testEditEvent(){
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
                .name("Integrationstest Event geändert")
                .description("Integrationstest sind toll und funktionieren")
                .actions(List.of(Action.MAIL))
                .notificationUser(List.of(user1WithId.getId(), user2WithId.getId(), newUserWithId.getId()))
                .build();
        Event expected = Event.builder()
                .id(eventIdToEdit)
                .name("Integrationstest Event geändert")
                .description("Integrationstest sind toll und funktionieren")
                .actions(List.of(Action.MAIL))
                .notificationUser(List.of(user1WithId, user2WithId, newUserWithId))
                .build();
        //WHEN
        ResponseEntity<Event> responseEntity = restTemplate.exchange("/api/event/"+eventIdToEdit, HttpMethod.PUT , new HttpEntity<>(eventToEdit,getLoginHeader()), Event.class);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(expected));

    }
}