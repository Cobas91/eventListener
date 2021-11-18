package eventlistener.exception;

public class OfficeAccessTokenException extends RuntimeException{
    public OfficeAccessTokenException(String message, Exception e){
        super(message, e);
    }
    public OfficeAccessTokenException(String message){
        super(message);
    }
}
