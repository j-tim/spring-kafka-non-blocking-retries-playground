package nl.jtim.spring.kafka.non.blocking.retries.consumer;

public class CanNotHandleEventRightNowException extends RuntimeException {

    public CanNotHandleEventRightNowException(String message) {
        super(message);
    }
}
