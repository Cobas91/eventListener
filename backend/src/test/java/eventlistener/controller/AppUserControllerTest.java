package eventlistener.controller;

import eventlistener.TestPostgresqlContainer;
import eventlistener.security.model.AppUserDTO;
import eventlistener.security.repo.AppUserRepo;
import eventlistener.service.AppUserService;

import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;




@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class AppUserControllerTest {

    @Container
    public static PostgreSQLContainer<TestPostgresqlContainer> postgreSQLContainer = TestPostgresqlContainer.getInstance();

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AppUserRepo appUserRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void clearDB(){
        appUserRepo.deleteAll();
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
    @DisplayName("Should Return generated User with hashed Password")
    void addUserToDB() {
        //GIVEN
        AppUserDTO userToAdd = AppUserDTO.builder().username("NewAddedUser").password("Unhashed Password").build();
        //WHEN

        ResponseEntity<AppUserDTO> addUserResponse = restTemplate.exchange("/api/appuser", HttpMethod.POST, new HttpEntity<>(userToAdd, getLoginHeader()), AppUserDTO.class);
        //THEN
        assertThat(addUserResponse.getStatusCode(), is(HttpStatus.OK));
        userToAdd.setId(Objects.requireNonNull(addUserResponse.getBody()).getId());
        userToAdd.setPassword(Objects.requireNonNull(addUserResponse.getBody()).getPassword());
        assertThat(addUserResponse.getBody(), is(userToAdd));

    }
}