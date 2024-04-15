package nl.jtim.spring.kafka.non.blocking.retries.consumer;

import lombok.extern.slf4j.Slf4j;
import nl.jtim.spring.kafka.non.blocking.retries.SomeEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventConsumer {

    public static final String TOPIC_NAME = "events";
    private final EventHandler service;

    public EventConsumer(EventHandler service) {
        this.service = service;
    }

    @KafkaListener(topics = TOPIC_NAME)
    @RetryableTopic(attempts = "4", numPartitions = "3", replicationFactor = "1",
            backoff = @Backoff(delay = 1000, multiplier = 2, maxDelay = 5000))
    public void on(ConsumerRecord<String, SomeEvent> event,
                   @Header(KafkaHeaders.RECEIVED_PARTITION) String partition,
                   @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Consumed from topic: {}, partition: {} value: {}", topic, partition, event);

        if (topic.equals(TOPIC_NAME)) {
            log.warn("We are not able to handle incoming data!");
            throw new CanNotHandleEventRightNowException("We have to wait a bit...");
        }

        service.handle(event.value());
    }

    /**
     * Handle from death letter topic
     */
    @DltHandler
    public void processMessageFromDeadLetterTopic(SomeEvent stockQuote) {
        log.info("Handling StockQuote from dead letter topic: {}", stockQuote);
    }
}
