package eventlistener.controller;

import eventlistener.TestHelper;
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
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class NotificationUserControllerTest {
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
    TestHelper testHelper;

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
        testHelper.clearTable("event_notification_user");
        appUserRepo.deleteAll();
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
        if(response.getBody() == null) throw new BadCredentialsException("Can´t get LoginHeader, Bad Credentials");
        headers.setBearerAuth(response.getBody());
        return headers;
    }

    @Test
    @DisplayName("Should return a List of Users")
    void testGetAllUser() {
        //GIVEN
        List<NotificationUser> users = List.of(
                NotificationUser.builder()
                        .email("1@1.de")
                        .name("1")
                        .build(),
                NotificationUser.builder()
                        .email("2@2.de")
                        .name("2")
                        .build()
        );
        List<NotificationUser> expected = notificationUserRepo.saveAll(users);
        //WHEN
        ResponseEntity<NotificationUser[]> responseEntity = restTemplate.exchange("/api/user", HttpMethod.GET , new HttpEntity<>(getLoginHeader()), NotificationUser[].class);

        //THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), arrayContainingInAnyOrder(expected));
    }

    @Test
    @DisplayName("Should add a new User and set the User ID to the given event.")
    void addUser() {
        //GIVEN
        Event eventToAdd = Event.builder()
                .name("IntegrationTest Event")
                .notificationUser(List.of())
                .build();
        Event eventWithId = eventRepo.save(eventToAdd);
        NotificationUserDTO userDTO = NotificationUserDTO.builder()
                .name("TestUser")
                .email("test@test.de")
                .listenEvents(List.of(eventWithId.getId()))
                .build();
        //WHEN
        ResponseEntity<NotificationUser> responseEntity = restTemplate.exchange("/api/user", HttpMethod.POST , new HttpEntity<>(userDTO, getLoginHeader()), NotificationUser.class);
        //THEN
        NotificationUser newAddedUser = responseEntity.getBody();
        assert newAddedUser != null;
        Optional<NotificationUser> repoUser = notificationUserRepo.findById(newAddedUser.getId());
        Optional<Event> optionalEvent = eventRepo.findById(eventToAdd.getId());
        assert optionalEvent.isPresent();
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(repoUser, is(Optional.of(newAddedUser)));
        assertThat(optionalEvent.get().getNotificationUser(), contains(newAddedUser));
    }

    @Test
    @DisplayName("Should Throw a EventNotFoundException, and Statuscode 400")
    void addUserGetNoEvent() {
        long eventId = 31;
        //GIVEN
        NotificationUserDTO userDTO = NotificationUserDTO.builder()
                .name("TestUser")
                .email("test@test.de")
                .listenEvents(List.of(22L))
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
                .id(1)
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
                .name("TestUser")
                .email("test@test.de")
                .build();
        NotificationUser userWithId = notificationUserRepo.save(userToEdit);
        Event userEvent = eventRepo.save(Event.builder().name("TestEvent").description("Beschreibung").actions(List.of(Action.MAIL)).notificationUser(List.of(userWithId)).build());
        NotificationUserEditDTO newUser = NotificationUserEditDTO.builder()
                .id(userToEdit.getId())
                .name("TestUserNeu")
                .email("newMail@test.de")
                .listenEvents(List.of(userEvent))
                .build();
        NotificationUser expected = NotificationUser.builder()
                .id(userToEdit.getId())
                .name("TestUserNeu")
                .email("newMail@test.de")
                .build();
        //WHEN
        ResponseEntity<NotificationUser> responseEntity = restTemplate.exchange("/api/user/"+userWithId.getId(), HttpMethod.PUT , new HttpEntity<>(newUser,getLoginHeader()), NotificationUser.class);
        //THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(expected));
    }
}