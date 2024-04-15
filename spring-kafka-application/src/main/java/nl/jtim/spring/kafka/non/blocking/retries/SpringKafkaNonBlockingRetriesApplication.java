package nl.jtim.spring.kafka.non.blocking.retries;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SpringKafkaNonBlockingRetriesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaNonBlockingRetriesApplication.class, args);
    }

}
