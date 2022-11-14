package nl.jtim.spring.kafka.non.blocking.retries.rest;

import nl.jtim.spring.kafka.non.blocking.retries.SomeEvent;
import nl.jtim.spring.kafka.non.blocking.retries.producer.EventProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class RestResource {

    private final EventProducer eventProducer;

    public RestResource(EventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    @PostMapping
    public void produce(@RequestBody Request request) {
        SomeEvent event = new SomeEvent(request.getCustomerId(), request.getAction(), request.getDescription());
        eventProducer.produce(event);
    }
}
