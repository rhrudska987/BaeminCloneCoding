package baemin.baeminjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BaeminJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaeminJpaApplication.class, args);
    }
}
