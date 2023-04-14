package ru.make.alex.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.make.alex.game.config.security.RsaProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaProperties.class)
public class RacingApplication {
    public static void main(String[] args) {
        SpringApplication.run(RacingApplication.class, args);
    }

}
