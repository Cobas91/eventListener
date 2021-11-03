package eventlistener.exception;


public class EventNotFoundException extends RuntimeException{

    public EventNotFoundException(String message, Exception e) {
        super(message, e);
    }
    public EventNotFoundException(String message) {
        super(message);
    }
}
