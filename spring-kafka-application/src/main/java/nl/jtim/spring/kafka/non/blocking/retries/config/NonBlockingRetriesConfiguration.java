package nl.jtim.spring.kafka.non.blocking.retries.config;

import nl.jtim.spring.kafka.non.blocking.retries.SomeEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaRetryTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.retrytopic.RetryTopicConfiguration;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static nl.jtim.spring.kafka.non.blocking.retries.consumer.EventConsumer.TOPIC_NAME;

// @EnableKafka is not needed because @EnableKafkaRetryTopic is meta-annotated including @EnableKafka
@EnableKafkaRetryTopic
@Configuration
public class NonBlockingRetriesConfiguration {

    public static final long INITIAL_INTERVAL = SECONDS.toMillis(1);
    public static final long MAX_INTERVAL = SECONDS.toMillis(5);
    public static final int NUMBER_OF_PARTITIONS = 3;
    public static final short REPLICATION_FACTOR = (short) 1;

    /**
     * This will create retry topics and a dlt, as well as the corresponding consumers,
     * for all topics in methods annotated with '@KafkaListener' using the default configurations.
     * <p>
     * The KafkaTemplate instance is required for message forwarding.
     * <p>
     * Want to set up non-blocking retries for topic: "events"
     * exponential backoff: 1000ms
     * multiplier: of 2
     * max attempts: 4
     * <p>
     * Topics created:
     * <p>
     * events
     * events-retry-1000
     * events-retry-2000
     * events-retry-4000
     * events-dlt
     */
    @Bean
    public RetryTopicConfiguration retryTopicConfiguration(KafkaTemplate<String, SomeEvent> template) {
        return RetryTopicConfigurationBuilder
                .newInstance()
                // In case you don't want to retry on certain exceptions
//                .notRetryOn(List.of(NullPointerException.class))
                .exponentialBackoff(INITIAL_INTERVAL, 2, MAX_INTERVAL)
                .maxAttempts(4)
                .includeTopic(TOPIC_NAME)
                .autoCreateTopics(true, NUMBER_OF_PARTITIONS, REPLICATION_FACTOR)
                .create(template);
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ThreadPoolTaskScheduler();
    }
}
