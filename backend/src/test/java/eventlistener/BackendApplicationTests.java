package eventlistener;

import eventlistener.security.repo.AppUserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AppUserRepo.class)
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
class BackendApplicationTests {

    @Test
    void contextLoads() {
    }

}
