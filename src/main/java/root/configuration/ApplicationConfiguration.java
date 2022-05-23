package root.configuration;

import root.repository.UserRepository;
import root.service.IdGenerator;
import root.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public IdGenerator idGenerator() {
        return new IdGenerator();
    }

    @Bean
    public UserService userService(IdGenerator idGenerator, UserRepository userRepository) {
        return new UserService(idGenerator, userRepository);
    }
}
