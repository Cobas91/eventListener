package eventlistener.controller;

import eventlistener.model.Action;
import eventlistener.model.event.Event;
import eventlistener.model.notificationuser.NotificationUser;
import eventlistener.model.notificationuser.NotificationUserDTO;
import eventlistener.model.notificationuser.NotificationUserEditDTO;
import eventlistener.repo.EventRepo;
import eventlistener.repo.NotificationUserRepo;
import eventlistener.security.model.AppUserDTO;
import eventlistener.security.repo.AppUserRepo;
import eventlistener.service.notificaionuser.NotificationUserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotificationUserControllerTest {

    @Autowired
    private NotificationUserService notificationUserService;

    @Autowired
    private NotificationUserRepo notificationUserRepo;

    @Autowired
    AppUserRepo appUserRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    EventRepo eventRepo;

    @BeforeEach
    public void clearDB(){
        notificationUserRepo.deleteAll();
    }

    public HttpHeaders getLoginHeader(){
        appUserRepo.save(AppUserDTO.builder()
                .username("test")
                .password(passwordEncoder.encode("some-password"))
                .build());
        AppUserDTO loginData = new AppUserDTO("test", "some-password");
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/login", loginData, String.class);
        HttpHeaders headers = new HttpHeaders();
        if(response.getBody() == null) throw new BadCredentialsException("Cant get LoginHeader, Bad Credentials");
        headers.setBearerAuth(response.getBody());
        return headers;
    }

    @Test
    @DisplayName("Should return a List of Users")
    void testGetAllUser() {
        //GIVEN
        List<NotificationUser> users = List.of(
                NotificationUser.builder()
                        .id("ID1")
                        .email("1@1.de")
                        .name("1")
                        .build(),
                NotificationUser.builder()
                        .id("ID2")
                        .email("2@2.de")
                        .name("2")
                        .build()
        );
        notificationUserRepo.saveAll(users);
        //WHEN
        ResponseEntity<NotificationUser[]> responseEntity = restTemplate.exchange("/api/user", HttpMethod.GET , new HttpEntity<>(getLoginHeader()), NotificationUser[].class);

        //THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), arrayContainingInAnyOrder(
                NotificationUser.builder()
                        .id("ID1")
                        .email("1@1.de")
                        .name("1")
                        .build(),
                NotificationUser.builder()
                        .id("ID2")
                        .email("2@2.de")
                        .name("2")
                        .build()
        ));
    }

    @Test
    @DisplayName("Should add a new User and set the User ID to the given event.")
    void addUser() {
        String eventId = "31";
        //GIVEN
        NotificationUserDTO userDTO = NotificationUserDTO.builder()
                .name("TestUser")
                .email("test@test.de")
                .listenEvents(List.of(eventId))
                .build();
        Event eventToAdd = Event.builder()
                .name("IntegrationTest Event")
                .id(eventId)
                .notificationUser(List.of())
                .build();
        eventRepo.save(eventToAdd);
        //WHEN
        ResponseEntity<NotificationUser> responseEntity = restTemplate.exchange("/api/user", HttpMethod.POST , new HttpEntity<>(userDTO, getLoginHeader()), NotificationUser.class);
        //THEN
        NotificationUser newAddedUser = responseEntity.getBody();
        assert newAddedUser != null;
        Optional<NotificationUser> repoUser = notificationUserRepo.findById(newAddedUser.getId());
        Optional<Event> repoEvent = eventRepo.findById(eventId);
        assert repoEvent.isPresent();
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(repoUser, is(Optional.of(newAddedUser)));
        assertThat(repoEvent.get().getNotificationUser(), contains(newAddedUser));
    }

    @Test
    @DisplayName("Should Throw a EventNotFoundException, and Statuscode 400")
    void addUserGetNoEvent() {
        String eventId = "31";
        //GIVEN
        NotificationUserDTO userDTO = NotificationUserDTO.builder()
                .name("TestUser")
                .email("test@test.de")
                .listenEvents(List.of("WrongEventID"))
                .build();
        Event eventToAdd = Event.builder()
                .name("IntegrationTest Event")
                .id(eventId)
                .notificationUser(List.of())
                .build();
        eventRepo.save(eventToAdd);
        //WHEN
        ResponseEntity<NotificationUser> responseEntity = restTemplate.exchange("/api/user", HttpMethod.POST , new HttpEntity<>(userDTO, getLoginHeader()), NotificationUser.class);
        //THEN

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    void getSingleUser() {
        //GIVEN
        NotificationUser userToFind = NotificationUser.builder()
                .id("1")
                .name("TestUser")
                .email("test@test.de")
                .build();
        notificationUserRepo.save(userToFind);
        //WHEN
        ResponseEntity<NotificationUser> responseEntity = restTemplate.exchange("/api/user/"+userToFind.getId(), HttpMethod.GET , new HttpEntity<>(getLoginHeader()), NotificationUser.class);
        //THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(userToFind));
    }

    @Test
    @DisplayName("Edit an existing User")
    void testEditUser() {
        //GIVEN
        NotificationUser userToEdit = NotificationUser.builder()
                .id("1")
                .name("TestUser")
                .email("test@test.de")
                .build();
        notificationUserRepo.save(userToEdit);
        List<Event> events = List.of(Event.builder().id("1").name("TestEvent").description("Beschreibung").actions(List.of(Action.MAIL)).notificationUser(List.of(userToEdit)).build());
        NotificationUserEditDTO newUser = NotificationUserEditDTO.builder()
                .id("1")
                .name("TestUserNeu")
                .email("newMail@test.de")
                .listenEvents(events)
                .build();
        NotificationUser expected = NotificationUser.builder()
                .id("1")
                .name("TestUserNeu")
                .email("newMail@test.de")
                .build();
        //WHEN
        ResponseEntity<NotificationUser> responseEntity = restTemplate.exchange("/api/user/"+userToEdit.getId(), HttpMethod.PUT , new HttpEntity<>(newUser,getLoginHeader()), NotificationUser.class);
        //THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(expected));
    }
}