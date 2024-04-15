package nl.jtim.spring.kafka.non.blocking.retries.consumer;

import lombok.extern.slf4j.Slf4j;
import nl.jtim.spring.kafka.non.blocking.retries.SomeEvent;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class DefaultEventHandler implements EventHandler {

    private final Random random = new Random();

    @Override
    public void handle(SomeEvent event) {

        // Non-recoverable exception
        if ("NON_RECOVERABLE_EXCEPTION".equalsIgnoreCase(event.getAction())) {
            throw new NullPointerException("Oh no.........");
        }

        // Exception you might recover from
        if ("RANDOM_FAILURE".equalsIgnoreCase(event.getAction())) {
            boolean throwException = random.nextBoolean();

            if (throwException) {
                log.info("You are not lucky today. Exception for you!");
                throw new RuntimeException("Whoops.....");
            } else {
                log.info("You are lucky, no exception for you!");
            }
        }

        log.info("Event successfully handled");
    }
}

