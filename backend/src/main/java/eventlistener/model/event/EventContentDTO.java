package eventlistener.model.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventContentDTO {
    @Id
    @JsonProperty("datapackageid")
    private String id;

    @JsonProperty("entitytoken")
    private String customerRef;

    @JsonProperty("environmentname")
    private String environmentName;

    private String project;
    @JsonProperty("timestamp_long")
    private long timestampLong;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("timestamp_ts")
    private LocalDateTime timestampTs;

    @JsonProperty("comment_user")
    private String commentUser;

    @JsonProperty("comment_extract")
    private String commentExtract;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("execution_started")
    private LocalDateTime executionStarted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("execution_finished")
    private LocalDateTime executionFinished;

    private String content;

}
