package nl.jtim.spring.kafka.non.blocking.retries.rest;

import lombok.Getter;

@Getter
public class Request {

    private String customerId;
    private String action;
    private String description;

    public Request(String customerId, String action, String description) {
        this.customerId = customerId;
        this.action = action;
        this.description = description;
    }
}
