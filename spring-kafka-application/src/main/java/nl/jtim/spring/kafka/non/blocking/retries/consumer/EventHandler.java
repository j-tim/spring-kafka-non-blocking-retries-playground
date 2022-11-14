package nl.jtim.spring.kafka.non.blocking.retries.consumer;

import nl.jtim.spring.kafka.non.blocking.retries.SomeEvent;

public interface EventHandler {


    void handle(SomeEvent event);
}
