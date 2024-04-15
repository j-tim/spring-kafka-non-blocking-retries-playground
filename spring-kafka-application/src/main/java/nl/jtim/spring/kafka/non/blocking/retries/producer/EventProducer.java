package nl.jtim.spring.kafka.non.blocking.retries.producer;

import lombok.extern.slf4j.Slf4j;
import nl.jtim.spring.kafka.non.blocking.retries.SomeEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public EventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produce(SomeEvent event) {
        kafkaTemplate.send("events", event.getCustomerId(), event);
        log.info("Produced event: {}", event);
    }
}
