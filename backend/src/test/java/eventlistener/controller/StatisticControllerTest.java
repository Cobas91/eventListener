package eventlistener.controller;

import eventlistener.TestHelper;
import eventlistener.TestPostgresqlContainer;
import eventlistener.exception.ActionNotConfiguredException;
import eventlistener.model.Action;
import eventlistener.model.statistic.Statistic;
import eventlistener.repo.StatistikRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class StatisticControllerTest {
    @Container
    public static PostgreSQLContainer<TestPostgresqlContainer> postgreSQLContainer = TestPostgresqlContainer.getInstance();

    @Autowired
    TestHelper testHelper;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StatistikRepo statistikRepo;

    @BeforeEach
    void clearDatabase(){
        testHelper.clearDatabase();
    }

    @Test
    void getAllStatistikObjects() {
        //GIVEN
        List<Statistic> statistics = testHelper.getStatistikObjects();
        statistikRepo.saveAll(statistics);
        //WHEN
        ResponseEntity<Statistic[]> responseEntity = restTemplate.exchange("/api/statistik", HttpMethod.GET, new HttpEntity<>(testHelper.getLoginHeader()), Statistic[].class);
        //THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(Objects.requireNonNull(responseEntity.getBody()).length, is(2));
    }

    @Test
    void getAllStatistikObjectsByAction() {
        //GIVEN
        List<Statistic> statistics = testHelper.getStatistikObjects();
        Statistic slackStatistic = testHelper.getStatistikObject();
        slackStatistic.setAction(Action.SLACK);
        statistikRepo.save(slackStatistic);
        statistikRepo.saveAll(statistics);
        //WHEN
        ResponseEntity<Statistic[]> responseEntity = restTemplate.exchange("/api/statistik/mail", HttpMethod.GET, new HttpEntity<>(testHelper.getLoginHeader()), Statistic[].class);
        //THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(Objects.requireNonNull(responseEntity.getBody()).length, is(2));

    }

    @Test
    void getAllStatistikObjectsByActionException() {
        //GIVEN

        //WHEN
        ResponseEntity<ActionNotConfiguredException> responseEntity = restTemplate.exchange("/api/statistik/unsupported", HttpMethod.GET, new HttpEntity<>(testHelper.getLoginHeader()), ActionNotConfiguredException.class);
        //THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));
    }
}