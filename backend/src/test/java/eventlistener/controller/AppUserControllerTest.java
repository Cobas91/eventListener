package eventlistener.controller;

import eventlistener.TestHelper;
import eventlistener.TestPostgresqlContainer;
import eventlistener.security.model.AppUserDTO;
import eventlistener.security.repo.AppUserRepo;
import eventlistener.service.AppUserService;

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

    @Autowired
    private TestHelper testHelper;

    @BeforeEach
    public void clearDB(){
        testHelper.clearDatabase();
    }


    @Test
    @DisplayName("Should Return generated User with hashed Password")
    void addUserToDB() {
        //GIVEN
        AppUserDTO userToAdd = AppUserDTO.builder().username("NewAddedUser").password("Unhashed Password").build();
        //WHEN

        ResponseEntity<AppUserDTO> addUserResponse = restTemplate.exchange("/api/appuser", HttpMethod.POST, new HttpEntity<>(userToAdd, testHelper.getLoginHeader()), AppUserDTO.class);
        //THEN
        assertThat(addUserResponse.getStatusCode(), is(HttpStatus.OK));
        userToAdd.setId(Objects.requireNonNull(addUserResponse.getBody()).getId());
        userToAdd.setPassword(Objects.requireNonNull(addUserResponse.getBody()).getPassword());
        assertThat(addUserResponse.getBody(), is(userToAdd));

    }
}