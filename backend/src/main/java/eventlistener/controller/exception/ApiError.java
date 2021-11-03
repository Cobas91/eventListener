package eventlistener.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class ApiError {
    private String text;
    private String exMessage;
    private LocalDateTime timestamp;

    public ApiError(String text, String exMessage) {
        this.text = text;
        this.exMessage = exMessage;
        this.timestamp = LocalDateTime.now();
    }
}
