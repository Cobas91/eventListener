package eventlistener.controller;

import eventlistener.model.Action;
import eventlistener.model.event.Event;
import eventlistener.model.event.ResponseEvent;
import eventlistener.repo.EventRepo;
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
import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EventService eventService;

    @Autowired
    private AppUserRepo appUserRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EventRepo eventRepo;

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
        List<Event> events = List.of(
                Event.builder()
                        .id("1")
                        .name("TestEvent")
                        .actions(List.of(Action.MAIL))
                        .notificationUser(List.of("UserId123", "UserId456"))
                        .description("Test Event 1")
                        .build(),
                Event.builder()
                        .id("2")
                        .name("TestEvent2")
                        .actions(List.of(Action.MAIL))
                        .notificationUser(List.of("UserId123", "UserId456"))
                        .description("Test Event 2")
                        .build()
        );
        eventRepo.saveAll(events);
        //WHEN
        ResponseEntity<ResponseEvent[]> responseEntity = restTemplate.exchange("/api/event", HttpMethod.GET , new HttpEntity<>(getLoginHeader()), ResponseEvent[].class);
        //THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), arrayContaining(
                ResponseEvent.builder()
                        .id("1")
                        .name("TestEvent")
                        .actions(List.of(Action.MAIL))
                        .description("Test Event 1")
                        .build(),
                ResponseEvent.builder()
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
        Event eventToAdd = Event.builder()
                .id("1")
                .name("TestEvent")
                .description("Integrationstest")
                .actions(List.of(Action.MAIL))
                .notificationUser(List.of("User1", "User2"))
                .build();
        //WHEN
        ResponseEntity<Event> responseEntity = restTemplate.exchange("/api/event", HttpMethod.POST , new HttpEntity<>(eventToAdd, getLoginHeader()), Event.class);
        Optional<Event> actual = eventRepo.findById("1");
        //THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(actual, is(Optional.of(eventToAdd)));


    }
}