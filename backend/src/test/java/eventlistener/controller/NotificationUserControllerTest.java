package eventlistener.controller;

import eventlistener.model.NotificationUser;
import eventlistener.repo.NotificationUserRepo;
import eventlistener.security.model.AppUserDTO;
import eventlistener.security.repo.AppUserRepo;
import eventlistener.service.NotificationUserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotificationUserControllerTest {

    @Autowired
    NotificationUserService notificationUserService;

    @Autowired
    NotificationUserRepo notificationUserRepo;

    @Autowired
    AppUserRepo appUserRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    TestRestTemplate restTemplate;

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
    void addUser() {
        //GIVEN
        NotificationUser userToAdd = NotificationUser.builder()
                .id("1")
                .name("TestUser")
                .email("test@test.de")
                .build();
        //WHEN
        ResponseEntity<NotificationUser> responseEntity = restTemplate.exchange("/api/user", HttpMethod.POST , new HttpEntity<>(userToAdd, getLoginHeader()), NotificationUser.class);
        //THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(userToAdd));
        assertThat(notificationUserRepo.findById(userToAdd.getId()), is(Optional.of(userToAdd)));
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
}