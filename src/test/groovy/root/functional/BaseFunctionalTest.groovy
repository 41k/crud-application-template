package root.functional

import io.restassured.RestAssured
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import root.repository.UserRepository
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@ActiveProfiles(['test'])
@SpringBootTest(webEnvironment = RANDOM_PORT)
abstract class BaseFunctionalTest extends Specification {

    @LocalServerPort
    private int port

    @Autowired
    protected UserRepository userRepository

    def setup() {
        RestAssured.port = port
        userRepository.deleteAll()
        userRepository.flush()
    }
}
